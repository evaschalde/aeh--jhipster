package com.caramelpoint.aeh.web.rest;

import com.caramelpoint.aeh.AehApp;

import com.caramelpoint.aeh.domain.Template;
import com.caramelpoint.aeh.repository.TemplateRepository;
import com.caramelpoint.aeh.service.TemplateService;
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
 * Test class for the TemplateResource REST controller.
 *
 * @see TemplateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AehApp.class)
public class TemplateResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FORM = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FORM = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FORM_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FORM_CONTENT_TYPE = "image/png";

    @Autowired
    private TemplateRepository templateRepository;

    

    @Autowired
    private TemplateService templateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTemplateMockMvc;

    private Template template;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TemplateResource templateResource = new TemplateResource(templateService);
        this.restTemplateMockMvc = MockMvcBuilders.standaloneSetup(templateResource)
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
    public static Template createEntity(EntityManager em) {
        Template template = new Template()
            .name(DEFAULT_NAME)
            .form(DEFAULT_FORM)
            .formContentType(DEFAULT_FORM_CONTENT_TYPE);
        return template;
    }

    @Before
    public void initTest() {
        template = createEntity(em);
    }

    @Test
    @Transactional
    public void createTemplate() throws Exception {
        int databaseSizeBeforeCreate = templateRepository.findAll().size();

        // Create the Template
        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(template)))
            .andExpect(status().isCreated());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeCreate + 1);
        Template testTemplate = templateList.get(templateList.size() - 1);
        assertThat(testTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTemplate.getForm()).isEqualTo(DEFAULT_FORM);
        assertThat(testTemplate.getFormContentType()).isEqualTo(DEFAULT_FORM_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createTemplateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = templateRepository.findAll().size();

        // Create the Template with an existing ID
        template.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateMockMvc.perform(post("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(template)))
            .andExpect(status().isBadRequest());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTemplates() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get all the templateList
        restTemplateMockMvc.perform(get("/api/templates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(template.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].formContentType").value(hasItem(DEFAULT_FORM_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].form").value(hasItem(Base64Utils.encodeToString(DEFAULT_FORM))));
    }
    

    @Test
    @Transactional
    public void getTemplate() throws Exception {
        // Initialize the database
        templateRepository.saveAndFlush(template);

        // Get the template
        restTemplateMockMvc.perform(get("/api/templates/{id}", template.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(template.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.formContentType").value(DEFAULT_FORM_CONTENT_TYPE))
            .andExpect(jsonPath("$.form").value(Base64Utils.encodeToString(DEFAULT_FORM)));
    }
    @Test
    @Transactional
    public void getNonExistingTemplate() throws Exception {
        // Get the template
        restTemplateMockMvc.perform(get("/api/templates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTemplate() throws Exception {
        // Initialize the database
        templateService.save(template);

        int databaseSizeBeforeUpdate = templateRepository.findAll().size();

        // Update the template
        Template updatedTemplate = templateRepository.findById(template.getId()).get();
        // Disconnect from session so that the updates on updatedTemplate are not directly saved in db
        em.detach(updatedTemplate);
        updatedTemplate
            .name(UPDATED_NAME)
            .form(UPDATED_FORM)
            .formContentType(UPDATED_FORM_CONTENT_TYPE);

        restTemplateMockMvc.perform(put("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTemplate)))
            .andExpect(status().isOk());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);
        Template testTemplate = templateList.get(templateList.size() - 1);
        assertThat(testTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTemplate.getForm()).isEqualTo(UPDATED_FORM);
        assertThat(testTemplate.getFormContentType()).isEqualTo(UPDATED_FORM_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingTemplate() throws Exception {
        int databaseSizeBeforeUpdate = templateRepository.findAll().size();

        // Create the Template

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTemplateMockMvc.perform(put("/api/templates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(template)))
            .andExpect(status().isBadRequest());

        // Validate the Template in the database
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTemplate() throws Exception {
        // Initialize the database
        templateService.save(template);

        int databaseSizeBeforeDelete = templateRepository.findAll().size();

        // Get the template
        restTemplateMockMvc.perform(delete("/api/templates/{id}", template.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Template> templateList = templateRepository.findAll();
        assertThat(templateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Template.class);
        Template template1 = new Template();
        template1.setId(1L);
        Template template2 = new Template();
        template2.setId(template1.getId());
        assertThat(template1).isEqualTo(template2);
        template2.setId(2L);
        assertThat(template1).isNotEqualTo(template2);
        template1.setId(null);
        assertThat(template1).isNotEqualTo(template2);
    }
}
