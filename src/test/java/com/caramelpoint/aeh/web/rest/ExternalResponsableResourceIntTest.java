package com.caramelpoint.aeh.web.rest;

import com.caramelpoint.aeh.AehApp;

import com.caramelpoint.aeh.domain.ExternalResponsable;
import com.caramelpoint.aeh.repository.ExternalResponsableRepository;
import com.caramelpoint.aeh.service.ExternalResponsableService;
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
 * Test class for the ExternalResponsableResource REST controller.
 *
 * @see ExternalResponsableResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AehApp.class)
public class ExternalResponsableResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private ExternalResponsableRepository externalResponsableRepository;

    

    @Autowired
    private ExternalResponsableService externalResponsableService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restExternalResponsableMockMvc;

    private ExternalResponsable externalResponsable;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExternalResponsableResource externalResponsableResource = new ExternalResponsableResource(externalResponsableService);
        this.restExternalResponsableMockMvc = MockMvcBuilders.standaloneSetup(externalResponsableResource)
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
    public static ExternalResponsable createEntity(EntityManager em) {
        ExternalResponsable externalResponsable = new ExternalResponsable()
            .name(DEFAULT_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .phone(DEFAULT_PHONE);
        return externalResponsable;
    }

    @Before
    public void initTest() {
        externalResponsable = createEntity(em);
    }

    @Test
    @Transactional
    public void createExternalResponsable() throws Exception {
        int databaseSizeBeforeCreate = externalResponsableRepository.findAll().size();

        // Create the ExternalResponsable
        restExternalResponsableMockMvc.perform(post("/api/external-responsables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalResponsable)))
            .andExpect(status().isCreated());

        // Validate the ExternalResponsable in the database
        List<ExternalResponsable> externalResponsableList = externalResponsableRepository.findAll();
        assertThat(externalResponsableList).hasSize(databaseSizeBeforeCreate + 1);
        ExternalResponsable testExternalResponsable = externalResponsableList.get(externalResponsableList.size() - 1);
        assertThat(testExternalResponsable.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testExternalResponsable.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testExternalResponsable.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createExternalResponsableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = externalResponsableRepository.findAll().size();

        // Create the ExternalResponsable with an existing ID
        externalResponsable.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExternalResponsableMockMvc.perform(post("/api/external-responsables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalResponsable)))
            .andExpect(status().isBadRequest());

        // Validate the ExternalResponsable in the database
        List<ExternalResponsable> externalResponsableList = externalResponsableRepository.findAll();
        assertThat(externalResponsableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExternalResponsables() throws Exception {
        // Initialize the database
        externalResponsableRepository.saveAndFlush(externalResponsable);

        // Get all the externalResponsableList
        restExternalResponsableMockMvc.perform(get("/api/external-responsables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(externalResponsable.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }
    

    @Test
    @Transactional
    public void getExternalResponsable() throws Exception {
        // Initialize the database
        externalResponsableRepository.saveAndFlush(externalResponsable);

        // Get the externalResponsable
        restExternalResponsableMockMvc.perform(get("/api/external-responsables/{id}", externalResponsable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(externalResponsable.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingExternalResponsable() throws Exception {
        // Get the externalResponsable
        restExternalResponsableMockMvc.perform(get("/api/external-responsables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExternalResponsable() throws Exception {
        // Initialize the database
        externalResponsableService.save(externalResponsable);

        int databaseSizeBeforeUpdate = externalResponsableRepository.findAll().size();

        // Update the externalResponsable
        ExternalResponsable updatedExternalResponsable = externalResponsableRepository.findById(externalResponsable.getId()).get();
        // Disconnect from session so that the updates on updatedExternalResponsable are not directly saved in db
        em.detach(updatedExternalResponsable);
        updatedExternalResponsable
            .name(UPDATED_NAME)
            .lastName(UPDATED_LAST_NAME)
            .phone(UPDATED_PHONE);

        restExternalResponsableMockMvc.perform(put("/api/external-responsables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExternalResponsable)))
            .andExpect(status().isOk());

        // Validate the ExternalResponsable in the database
        List<ExternalResponsable> externalResponsableList = externalResponsableRepository.findAll();
        assertThat(externalResponsableList).hasSize(databaseSizeBeforeUpdate);
        ExternalResponsable testExternalResponsable = externalResponsableList.get(externalResponsableList.size() - 1);
        assertThat(testExternalResponsable.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testExternalResponsable.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testExternalResponsable.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingExternalResponsable() throws Exception {
        int databaseSizeBeforeUpdate = externalResponsableRepository.findAll().size();

        // Create the ExternalResponsable

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restExternalResponsableMockMvc.perform(put("/api/external-responsables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalResponsable)))
            .andExpect(status().isBadRequest());

        // Validate the ExternalResponsable in the database
        List<ExternalResponsable> externalResponsableList = externalResponsableRepository.findAll();
        assertThat(externalResponsableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExternalResponsable() throws Exception {
        // Initialize the database
        externalResponsableService.save(externalResponsable);

        int databaseSizeBeforeDelete = externalResponsableRepository.findAll().size();

        // Get the externalResponsable
        restExternalResponsableMockMvc.perform(delete("/api/external-responsables/{id}", externalResponsable.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExternalResponsable> externalResponsableList = externalResponsableRepository.findAll();
        assertThat(externalResponsableList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExternalResponsable.class);
        ExternalResponsable externalResponsable1 = new ExternalResponsable();
        externalResponsable1.setId(1L);
        ExternalResponsable externalResponsable2 = new ExternalResponsable();
        externalResponsable2.setId(externalResponsable1.getId());
        assertThat(externalResponsable1).isEqualTo(externalResponsable2);
        externalResponsable2.setId(2L);
        assertThat(externalResponsable1).isNotEqualTo(externalResponsable2);
        externalResponsable1.setId(null);
        assertThat(externalResponsable1).isNotEqualTo(externalResponsable2);
    }
}
