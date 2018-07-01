package com.caramelpoint.aeh.web.rest;

import com.caramelpoint.aeh.AehApp;

import com.caramelpoint.aeh.domain.Form;
import com.caramelpoint.aeh.repository.FormRepository;
import com.caramelpoint.aeh.service.FormService;
import com.caramelpoint.aeh.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static com.caramelpoint.aeh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FormResource REST controller.
 *
 * @see FormResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AehApp.class)
public class FormResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FORM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FORM = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FORM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FORM_CONTENT_TYPE = "image/png";

    @Autowired
    private FormRepository formRepository;

    

    @Autowired
    private FormService formService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormMockMvc;

    private Form form;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormResource formResource = new FormResource(formService);
        this.restFormMockMvc = MockMvcBuilders.standaloneSetup(formResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Form createEntity(EntityManager em) {
        Form form = new Form()
            .name(DEFAULT_NAME)
            .form(DEFAULT_FORM)
            .formContentType(DEFAULT_FORM_CONTENT_TYPE);
        return form;
    }

    @Before
    public void initTest() {
        form = createEntity(em);
    }

    @Test
    @Transactional
    public void createForm() throws Exception {
        int databaseSizeBeforeCreate = formRepository.findAll().size();

        // Create the Form
        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(form)))
            .andExpect(status().isCreated());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeCreate + 1);
        Form testForm = formList.get(formList.size() - 1);
        assertThat(testForm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testForm.getForm()).isEqualTo(DEFAULT_FORM);
        assertThat(testForm.getFormContentType()).isEqualTo(DEFAULT_FORM_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createFormWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formRepository.findAll().size();

        // Create the Form with an existing ID
        form.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormMockMvc.perform(post("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(form)))
            .andExpect(status().isBadRequest());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllForms() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get all the formList
        restFormMockMvc.perform(get("/api/forms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(form.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].formContentType").value(hasItem(DEFAULT_FORM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].form").value(hasItem(Base64Utils.encodeToString(DEFAULT_FORM))));
    }
    

    @Test
    @Transactional
    public void getForm() throws Exception {
        // Initialize the database
        formRepository.saveAndFlush(form);

        // Get the form
        restFormMockMvc.perform(get("/api/forms/{id}", form.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(form.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.formContentType").value(DEFAULT_FORM_CONTENT_TYPE))
            .andExpect(jsonPath("$.form").value(Base64Utils.encodeToString(DEFAULT_FORM)));
    }
    @Test
    @Transactional
    public void getNonExistingForm() throws Exception {
        // Get the form
        restFormMockMvc.perform(get("/api/forms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateForm() throws Exception {
        // Initialize the database
        formService.save(form);

        int databaseSizeBeforeUpdate = formRepository.findAll().size();

        // Update the form
        Form updatedForm = formRepository.findById(form.getId()).get();
        // Disconnect from session so that the updates on updatedForm are not directly saved in db
        em.detach(updatedForm);
        updatedForm
            .name(UPDATED_NAME)
            .form(UPDATED_FORM)
            .formContentType(UPDATED_FORM_CONTENT_TYPE);

        restFormMockMvc.perform(put("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedForm)))
            .andExpect(status().isOk());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate);
        Form testForm = formList.get(formList.size() - 1);
        assertThat(testForm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testForm.getForm()).isEqualTo(UPDATED_FORM);
        assertThat(testForm.getFormContentType()).isEqualTo(UPDATED_FORM_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingForm() throws Exception {
        int databaseSizeBeforeUpdate = formRepository.findAll().size();

        // Create the Form

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFormMockMvc.perform(put("/api/forms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(form)))
            .andExpect(status().isBadRequest());

        // Validate the Form in the database
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteForm() throws Exception {
        // Initialize the database
        formService.save(form);

        int databaseSizeBeforeDelete = formRepository.findAll().size();

        // Get the form
        restFormMockMvc.perform(delete("/api/forms/{id}", form.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Form> formList = formRepository.findAll();
        assertThat(formList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Form.class);
        Form form1 = new Form();
        form1.setId(1L);
        Form form2 = new Form();
        form2.setId(form1.getId());
        assertThat(form1).isEqualTo(form2);
        form2.setId(2L);
        assertThat(form1).isNotEqualTo(form2);
        form1.setId(null);
        assertThat(form1).isNotEqualTo(form2);
    }
}
