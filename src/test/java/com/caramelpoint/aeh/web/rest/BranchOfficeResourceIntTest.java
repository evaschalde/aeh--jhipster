package com.caramelpoint.aeh.web.rest;

import com.caramelpoint.aeh.AehApp;

import com.caramelpoint.aeh.domain.BranchOffice;
import com.caramelpoint.aeh.repository.BranchOfficeRepository;
import com.caramelpoint.aeh.service.BranchOfficeService;
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
 * Test class for the BranchOfficeResource REST controller.
 *
 * @see BranchOfficeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AehApp.class)
public class BranchOfficeResourceIntTest {

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private BranchOfficeRepository branchOfficeRepository;

    

    @Autowired
    private BranchOfficeService branchOfficeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBranchOfficeMockMvc;

    private BranchOffice branchOffice;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BranchOfficeResource branchOfficeResource = new BranchOfficeResource(branchOfficeService);
        this.restBranchOfficeMockMvc = MockMvcBuilders.standaloneSetup(branchOfficeResource)
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
    public static BranchOffice createEntity(EntityManager em) {
        BranchOffice branchOffice = new BranchOffice()
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .stateProvince(DEFAULT_STATE_PROVINCE)
            .phone(DEFAULT_PHONE);
        return branchOffice;
    }

    @Before
    public void initTest() {
        branchOffice = createEntity(em);
    }

    @Test
    @Transactional
    public void createBranchOffice() throws Exception {
        int databaseSizeBeforeCreate = branchOfficeRepository.findAll().size();

        // Create the BranchOffice
        restBranchOfficeMockMvc.perform(post("/api/branch-offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchOffice)))
            .andExpect(status().isCreated());

        // Validate the BranchOffice in the database
        List<BranchOffice> branchOfficeList = branchOfficeRepository.findAll();
        assertThat(branchOfficeList).hasSize(databaseSizeBeforeCreate + 1);
        BranchOffice testBranchOffice = branchOfficeList.get(branchOfficeList.size() - 1);
        assertThat(testBranchOffice.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testBranchOffice.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testBranchOffice.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testBranchOffice.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
        assertThat(testBranchOffice.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createBranchOfficeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = branchOfficeRepository.findAll().size();

        // Create the BranchOffice with an existing ID
        branchOffice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBranchOfficeMockMvc.perform(post("/api/branch-offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchOffice)))
            .andExpect(status().isBadRequest());

        // Validate the BranchOffice in the database
        List<BranchOffice> branchOfficeList = branchOfficeRepository.findAll();
        assertThat(branchOfficeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBranchOffices() throws Exception {
        // Initialize the database
        branchOfficeRepository.saveAndFlush(branchOffice);

        // Get all the branchOfficeList
        restBranchOfficeMockMvc.perform(get("/api/branch-offices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(branchOffice.getId().intValue())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())));
    }
    

    @Test
    @Transactional
    public void getBranchOffice() throws Exception {
        // Initialize the database
        branchOfficeRepository.saveAndFlush(branchOffice);

        // Get the branchOffice
        restBranchOfficeMockMvc.perform(get("/api/branch-offices/{id}", branchOffice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(branchOffice.getId().intValue()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingBranchOffice() throws Exception {
        // Get the branchOffice
        restBranchOfficeMockMvc.perform(get("/api/branch-offices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBranchOffice() throws Exception {
        // Initialize the database
        branchOfficeService.save(branchOffice);

        int databaseSizeBeforeUpdate = branchOfficeRepository.findAll().size();

        // Update the branchOffice
        BranchOffice updatedBranchOffice = branchOfficeRepository.findById(branchOffice.getId()).get();
        // Disconnect from session so that the updates on updatedBranchOffice are not directly saved in db
        em.detach(updatedBranchOffice);
        updatedBranchOffice
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .phone(UPDATED_PHONE);

        restBranchOfficeMockMvc.perform(put("/api/branch-offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBranchOffice)))
            .andExpect(status().isOk());

        // Validate the BranchOffice in the database
        List<BranchOffice> branchOfficeList = branchOfficeRepository.findAll();
        assertThat(branchOfficeList).hasSize(databaseSizeBeforeUpdate);
        BranchOffice testBranchOffice = branchOfficeList.get(branchOfficeList.size() - 1);
        assertThat(testBranchOffice.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testBranchOffice.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testBranchOffice.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testBranchOffice.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testBranchOffice.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingBranchOffice() throws Exception {
        int databaseSizeBeforeUpdate = branchOfficeRepository.findAll().size();

        // Create the BranchOffice

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBranchOfficeMockMvc.perform(put("/api/branch-offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchOffice)))
            .andExpect(status().isBadRequest());

        // Validate the BranchOffice in the database
        List<BranchOffice> branchOfficeList = branchOfficeRepository.findAll();
        assertThat(branchOfficeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBranchOffice() throws Exception {
        // Initialize the database
        branchOfficeService.save(branchOffice);

        int databaseSizeBeforeDelete = branchOfficeRepository.findAll().size();

        // Get the branchOffice
        restBranchOfficeMockMvc.perform(delete("/api/branch-offices/{id}", branchOffice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BranchOffice> branchOfficeList = branchOfficeRepository.findAll();
        assertThat(branchOfficeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BranchOffice.class);
        BranchOffice branchOffice1 = new BranchOffice();
        branchOffice1.setId(1L);
        BranchOffice branchOffice2 = new BranchOffice();
        branchOffice2.setId(branchOffice1.getId());
        assertThat(branchOffice1).isEqualTo(branchOffice2);
        branchOffice2.setId(2L);
        assertThat(branchOffice1).isNotEqualTo(branchOffice2);
        branchOffice1.setId(null);
        assertThat(branchOffice1).isNotEqualTo(branchOffice2);
    }
}
