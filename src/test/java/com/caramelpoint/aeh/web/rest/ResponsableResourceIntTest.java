package com.caramelpoint.aeh.web.rest;

import com.caramelpoint.aeh.AehApp;

import com.caramelpoint.aeh.domain.Responsable;
import com.caramelpoint.aeh.repository.ResponsableRepository;
import com.caramelpoint.aeh.service.ResponsableService;
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

import javax.persistence.EntityManager;
import java.util.List;


import static com.caramelpoint.aeh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ResponsableResource REST controller.
 *
 * @see ResponsableResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AehApp.class)
public class ResponsableResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    @Autowired
    private ResponsableRepository responsableRepository;

    

    @Autowired
    private ResponsableService responsableService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restResponsableMockMvc;

    private Responsable responsable;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResponsableResource responsableResource = new ResponsableResource(responsableService);
        this.restResponsableMockMvc = MockMvcBuilders.standaloneSetup(responsableResource)
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
    public static Responsable createEntity(EntityManager em) {
        Responsable responsable = new Responsable()
            .name(DEFAULT_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .phone(DEFAULT_PHONE)
            .mail(DEFAULT_MAIL);
        return responsable;
    }

    @Before
    public void initTest() {
        responsable = createEntity(em);
    }

    @Test
    @Transactional
    public void createResponsable() throws Exception {
        int databaseSizeBeforeCreate = responsableRepository.findAll().size();

        // Create the Responsable
        restResponsableMockMvc.perform(post("/api/responsables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsable)))
            .andExpect(status().isCreated());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeCreate + 1);
        Responsable testResponsable = responsableList.get(responsableList.size() - 1);
        assertThat(testResponsable.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testResponsable.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testResponsable.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testResponsable.getMail()).isEqualTo(DEFAULT_MAIL);
    }

    @Test
    @Transactional
    public void createResponsableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = responsableRepository.findAll().size();

        // Create the Responsable with an existing ID
        responsable.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResponsableMockMvc.perform(post("/api/responsables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsable)))
            .andExpect(status().isBadRequest());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllResponsables() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get all the responsableList
        restResponsableMockMvc.perform(get("/api/responsables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(responsable.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL.toString())));
    }
    

    @Test
    @Transactional
    public void getResponsable() throws Exception {
        // Initialize the database
        responsableRepository.saveAndFlush(responsable);

        // Get the responsable
        restResponsableMockMvc.perform(get("/api/responsables/{id}", responsable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(responsable.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingResponsable() throws Exception {
        // Get the responsable
        restResponsableMockMvc.perform(get("/api/responsables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResponsable() throws Exception {
        // Initialize the database
        responsableService.save(responsable);

        int databaseSizeBeforeUpdate = responsableRepository.findAll().size();

        // Update the responsable
        Responsable updatedResponsable = responsableRepository.findById(responsable.getId()).get();
        // Disconnect from session so that the updates on updatedResponsable are not directly saved in db
        em.detach(updatedResponsable);
        updatedResponsable
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE)
            .mail(UPDATED_MAIL);

        restResponsableMockMvc.perform(put("/api/responsables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResponsable)))
            .andExpect(status().isOk());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeUpdate);
        Responsable testResponsable = responsableList.get(responsableList.size() - 1);
        assertThat(testResponsable.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testResponsable.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testResponsable.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testResponsable.getMail()).isEqualTo(UPDATED_MAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingResponsable() throws Exception {
        int databaseSizeBeforeUpdate = responsableRepository.findAll().size();

        // Create the Responsable

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restResponsableMockMvc.perform(put("/api/responsables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(responsable)))
            .andExpect(status().isBadRequest());

        // Validate the Responsable in the database
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResponsable() throws Exception {
        // Initialize the database
        responsableService.save(responsable);

        int databaseSizeBeforeDelete = responsableRepository.findAll().size();

        // Get the responsable
        restResponsableMockMvc.perform(delete("/api/responsables/{id}", responsable.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Responsable> responsableList = responsableRepository.findAll();
        assertThat(responsableList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Responsable.class);
        Responsable responsable1 = new Responsable();
        responsable1.setId(1L);
        Responsable responsable2 = new Responsable();
        responsable2.setId(responsable1.getId());
        assertThat(responsable1).isEqualTo(responsable2);
        responsable2.setId(2L);
        assertThat(responsable1).isNotEqualTo(responsable2);
        responsable1.setId(null);
        assertThat(responsable1).isNotEqualTo(responsable2);
    }
}
