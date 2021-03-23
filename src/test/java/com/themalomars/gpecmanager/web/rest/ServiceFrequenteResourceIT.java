package com.themalomars.gpecmanager.web.rest;

import com.themalomars.gpecmanager.GpecmanagerApp;
import com.themalomars.gpecmanager.domain.ServiceFrequente;
import com.themalomars.gpecmanager.domain.Employe;
import com.themalomars.gpecmanager.repository.ServiceFrequenteRepository;
import com.themalomars.gpecmanager.service.ServiceFrequenteService;
import com.themalomars.gpecmanager.service.dto.ServiceFrequenteCriteria;
import com.themalomars.gpecmanager.service.ServiceFrequenteQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ServiceFrequenteResource} REST controller.
 */
@SpringBootTest(classes = GpecmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServiceFrequenteResourceIT {

    private static final Long DEFAULT_CODE_SERVICE = 1L;
    private static final Long UPDATED_CODE_SERVICE = 2L;
    private static final Long SMALLER_CODE_SERVICE = 1L - 1L;

    private static final String DEFAULT_LIBELLE_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_SERVICE = "BBBBBBBBBB";

    @Autowired
    private ServiceFrequenteRepository serviceFrequenteRepository;

    @Autowired
    private ServiceFrequenteService serviceFrequenteService;

    @Autowired
    private ServiceFrequenteQueryService serviceFrequenteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceFrequenteMockMvc;

    private ServiceFrequente serviceFrequente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceFrequente createEntity(EntityManager em) {
        ServiceFrequente serviceFrequente = new ServiceFrequente()
            .codeService(DEFAULT_CODE_SERVICE)
            .libelleService(DEFAULT_LIBELLE_SERVICE);
        return serviceFrequente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceFrequente createUpdatedEntity(EntityManager em) {
        ServiceFrequente serviceFrequente = new ServiceFrequente()
            .codeService(UPDATED_CODE_SERVICE)
            .libelleService(UPDATED_LIBELLE_SERVICE);
        return serviceFrequente;
    }

    @BeforeEach
    public void initTest() {
        serviceFrequente = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceFrequente() throws Exception {
        int databaseSizeBeforeCreate = serviceFrequenteRepository.findAll().size();
        // Create the ServiceFrequente
        restServiceFrequenteMockMvc.perform(post("/api/service-frequentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceFrequente)))
            .andExpect(status().isCreated());

        // Validate the ServiceFrequente in the database
        List<ServiceFrequente> serviceFrequenteList = serviceFrequenteRepository.findAll();
        assertThat(serviceFrequenteList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceFrequente testServiceFrequente = serviceFrequenteList.get(serviceFrequenteList.size() - 1);
        assertThat(testServiceFrequente.getCodeService()).isEqualTo(DEFAULT_CODE_SERVICE);
        assertThat(testServiceFrequente.getLibelleService()).isEqualTo(DEFAULT_LIBELLE_SERVICE);
    }

    @Test
    @Transactional
    public void createServiceFrequenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceFrequenteRepository.findAll().size();

        // Create the ServiceFrequente with an existing ID
        serviceFrequente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceFrequenteMockMvc.perform(post("/api/service-frequentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceFrequente)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceFrequente in the database
        List<ServiceFrequente> serviceFrequenteList = serviceFrequenteRepository.findAll();
        assertThat(serviceFrequenteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeServiceIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceFrequenteRepository.findAll().size();
        // set the field null
        serviceFrequente.setCodeService(null);

        // Create the ServiceFrequente, which fails.


        restServiceFrequenteMockMvc.perform(post("/api/service-frequentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceFrequente)))
            .andExpect(status().isBadRequest());

        List<ServiceFrequente> serviceFrequenteList = serviceFrequenteRepository.findAll();
        assertThat(serviceFrequenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleServiceIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceFrequenteRepository.findAll().size();
        // set the field null
        serviceFrequente.setLibelleService(null);

        // Create the ServiceFrequente, which fails.


        restServiceFrequenteMockMvc.perform(post("/api/service-frequentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceFrequente)))
            .andExpect(status().isBadRequest());

        List<ServiceFrequente> serviceFrequenteList = serviceFrequenteRepository.findAll();
        assertThat(serviceFrequenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceFrequentes() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);

        // Get all the serviceFrequenteList
        restServiceFrequenteMockMvc.perform(get("/api/service-frequentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceFrequente.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeService").value(hasItem(DEFAULT_CODE_SERVICE.intValue())))
            .andExpect(jsonPath("$.[*].libelleService").value(hasItem(DEFAULT_LIBELLE_SERVICE)));
    }
    
    @Test
    @Transactional
    public void getServiceFrequente() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);

        // Get the serviceFrequente
        restServiceFrequenteMockMvc.perform(get("/api/service-frequentes/{id}", serviceFrequente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceFrequente.getId().intValue()))
            .andExpect(jsonPath("$.codeService").value(DEFAULT_CODE_SERVICE.intValue()))
            .andExpect(jsonPath("$.libelleService").value(DEFAULT_LIBELLE_SERVICE));
    }


    @Test
    @Transactional
    public void getServiceFrequentesByIdFiltering() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);

        Long id = serviceFrequente.getId();

        defaultServiceFrequenteShouldBeFound("id.equals=" + id);
        defaultServiceFrequenteShouldNotBeFound("id.notEquals=" + id);

        defaultServiceFrequenteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServiceFrequenteShouldNotBeFound("id.greaterThan=" + id);

        defaultServiceFrequenteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServiceFrequenteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllServiceFrequentesByCodeServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);

        // Get all the serviceFrequenteList where codeService equals to DEFAULT_CODE_SERVICE
        defaultServiceFrequenteShouldBeFound("codeService.equals=" + DEFAULT_CODE_SERVICE);

        // Get all the serviceFrequenteList where codeService equals to UPDATED_CODE_SERVICE
        defaultServiceFrequenteShouldNotBeFound("codeService.equals=" + UPDATED_CODE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceFrequentesByCodeServiceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);

        // Get all the serviceFrequenteList where codeService not equals to DEFAULT_CODE_SERVICE
        defaultServiceFrequenteShouldNotBeFound("codeService.notEquals=" + DEFAULT_CODE_SERVICE);

        // Get all the serviceFrequenteList where codeService not equals to UPDATED_CODE_SERVICE
        defaultServiceFrequenteShouldBeFound("codeService.notEquals=" + UPDATED_CODE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceFrequentesByCodeServiceIsInShouldWork() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);

        // Get all the serviceFrequenteList where codeService in DEFAULT_CODE_SERVICE or UPDATED_CODE_SERVICE
        defaultServiceFrequenteShouldBeFound("codeService.in=" + DEFAULT_CODE_SERVICE + "," + UPDATED_CODE_SERVICE);

        // Get all the serviceFrequenteList where codeService equals to UPDATED_CODE_SERVICE
        defaultServiceFrequenteShouldNotBeFound("codeService.in=" + UPDATED_CODE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceFrequentesByCodeServiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);

        // Get all the serviceFrequenteList where codeService is not null
        defaultServiceFrequenteShouldBeFound("codeService.specified=true");

        // Get all the serviceFrequenteList where codeService is null
        defaultServiceFrequenteShouldNotBeFound("codeService.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceFrequentesByCodeServiceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);

        // Get all the serviceFrequenteList where codeService is greater than or equal to DEFAULT_CODE_SERVICE
        defaultServiceFrequenteShouldBeFound("codeService.greaterThanOrEqual=" + DEFAULT_CODE_SERVICE);

        // Get all the serviceFrequenteList where codeService is greater than or equal to UPDATED_CODE_SERVICE
        defaultServiceFrequenteShouldNotBeFound("codeService.greaterThanOrEqual=" + UPDATED_CODE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceFrequentesByCodeServiceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);

        // Get all the serviceFrequenteList where codeService is less than or equal to DEFAULT_CODE_SERVICE
        defaultServiceFrequenteShouldBeFound("codeService.lessThanOrEqual=" + DEFAULT_CODE_SERVICE);

        // Get all the serviceFrequenteList where codeService is less than or equal to SMALLER_CODE_SERVICE
        defaultServiceFrequenteShouldNotBeFound("codeService.lessThanOrEqual=" + SMALLER_CODE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceFrequentesByCodeServiceIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);

        // Get all the serviceFrequenteList where codeService is less than DEFAULT_CODE_SERVICE
        defaultServiceFrequenteShouldNotBeFound("codeService.lessThan=" + DEFAULT_CODE_SERVICE);

        // Get all the serviceFrequenteList where codeService is less than UPDATED_CODE_SERVICE
        defaultServiceFrequenteShouldBeFound("codeService.lessThan=" + UPDATED_CODE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceFrequentesByCodeServiceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);

        // Get all the serviceFrequenteList where codeService is greater than DEFAULT_CODE_SERVICE
        defaultServiceFrequenteShouldNotBeFound("codeService.greaterThan=" + DEFAULT_CODE_SERVICE);

        // Get all the serviceFrequenteList where codeService is greater than SMALLER_CODE_SERVICE
        defaultServiceFrequenteShouldBeFound("codeService.greaterThan=" + SMALLER_CODE_SERVICE);
    }


    @Test
    @Transactional
    public void getAllServiceFrequentesByLibelleServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);

        // Get all the serviceFrequenteList where libelleService equals to DEFAULT_LIBELLE_SERVICE
        defaultServiceFrequenteShouldBeFound("libelleService.equals=" + DEFAULT_LIBELLE_SERVICE);

        // Get all the serviceFrequenteList where libelleService equals to UPDATED_LIBELLE_SERVICE
        defaultServiceFrequenteShouldNotBeFound("libelleService.equals=" + UPDATED_LIBELLE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceFrequentesByLibelleServiceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);

        // Get all the serviceFrequenteList where libelleService not equals to DEFAULT_LIBELLE_SERVICE
        defaultServiceFrequenteShouldNotBeFound("libelleService.notEquals=" + DEFAULT_LIBELLE_SERVICE);

        // Get all the serviceFrequenteList where libelleService not equals to UPDATED_LIBELLE_SERVICE
        defaultServiceFrequenteShouldBeFound("libelleService.notEquals=" + UPDATED_LIBELLE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceFrequentesByLibelleServiceIsInShouldWork() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);

        // Get all the serviceFrequenteList where libelleService in DEFAULT_LIBELLE_SERVICE or UPDATED_LIBELLE_SERVICE
        defaultServiceFrequenteShouldBeFound("libelleService.in=" + DEFAULT_LIBELLE_SERVICE + "," + UPDATED_LIBELLE_SERVICE);

        // Get all the serviceFrequenteList where libelleService equals to UPDATED_LIBELLE_SERVICE
        defaultServiceFrequenteShouldNotBeFound("libelleService.in=" + UPDATED_LIBELLE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceFrequentesByLibelleServiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);

        // Get all the serviceFrequenteList where libelleService is not null
        defaultServiceFrequenteShouldBeFound("libelleService.specified=true");

        // Get all the serviceFrequenteList where libelleService is null
        defaultServiceFrequenteShouldNotBeFound("libelleService.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceFrequentesByLibelleServiceContainsSomething() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);

        // Get all the serviceFrequenteList where libelleService contains DEFAULT_LIBELLE_SERVICE
        defaultServiceFrequenteShouldBeFound("libelleService.contains=" + DEFAULT_LIBELLE_SERVICE);

        // Get all the serviceFrequenteList where libelleService contains UPDATED_LIBELLE_SERVICE
        defaultServiceFrequenteShouldNotBeFound("libelleService.contains=" + UPDATED_LIBELLE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceFrequentesByLibelleServiceNotContainsSomething() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);

        // Get all the serviceFrequenteList where libelleService does not contain DEFAULT_LIBELLE_SERVICE
        defaultServiceFrequenteShouldNotBeFound("libelleService.doesNotContain=" + DEFAULT_LIBELLE_SERVICE);

        // Get all the serviceFrequenteList where libelleService does not contain UPDATED_LIBELLE_SERVICE
        defaultServiceFrequenteShouldBeFound("libelleService.doesNotContain=" + UPDATED_LIBELLE_SERVICE);
    }


    @Test
    @Transactional
    public void getAllServiceFrequentesByEmployeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);
        Employe employe = EmployeResourceIT.createEntity(em);
        em.persist(employe);
        em.flush();
        serviceFrequente.addEmploye(employe);
        serviceFrequenteRepository.saveAndFlush(serviceFrequente);
        Long employeId = employe.getId();

        // Get all the serviceFrequenteList where employe equals to employeId
        defaultServiceFrequenteShouldBeFound("employeId.equals=" + employeId);

        // Get all the serviceFrequenteList where employe equals to employeId + 1
        defaultServiceFrequenteShouldNotBeFound("employeId.equals=" + (employeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceFrequenteShouldBeFound(String filter) throws Exception {
        restServiceFrequenteMockMvc.perform(get("/api/service-frequentes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceFrequente.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeService").value(hasItem(DEFAULT_CODE_SERVICE.intValue())))
            .andExpect(jsonPath("$.[*].libelleService").value(hasItem(DEFAULT_LIBELLE_SERVICE)));

        // Check, that the count call also returns 1
        restServiceFrequenteMockMvc.perform(get("/api/service-frequentes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceFrequenteShouldNotBeFound(String filter) throws Exception {
        restServiceFrequenteMockMvc.perform(get("/api/service-frequentes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceFrequenteMockMvc.perform(get("/api/service-frequentes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingServiceFrequente() throws Exception {
        // Get the serviceFrequente
        restServiceFrequenteMockMvc.perform(get("/api/service-frequentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceFrequente() throws Exception {
        // Initialize the database
        serviceFrequenteService.save(serviceFrequente);

        int databaseSizeBeforeUpdate = serviceFrequenteRepository.findAll().size();

        // Update the serviceFrequente
        ServiceFrequente updatedServiceFrequente = serviceFrequenteRepository.findById(serviceFrequente.getId()).get();
        // Disconnect from session so that the updates on updatedServiceFrequente are not directly saved in db
        em.detach(updatedServiceFrequente);
        updatedServiceFrequente
            .codeService(UPDATED_CODE_SERVICE)
            .libelleService(UPDATED_LIBELLE_SERVICE);

        restServiceFrequenteMockMvc.perform(put("/api/service-frequentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceFrequente)))
            .andExpect(status().isOk());

        // Validate the ServiceFrequente in the database
        List<ServiceFrequente> serviceFrequenteList = serviceFrequenteRepository.findAll();
        assertThat(serviceFrequenteList).hasSize(databaseSizeBeforeUpdate);
        ServiceFrequente testServiceFrequente = serviceFrequenteList.get(serviceFrequenteList.size() - 1);
        assertThat(testServiceFrequente.getCodeService()).isEqualTo(UPDATED_CODE_SERVICE);
        assertThat(testServiceFrequente.getLibelleService()).isEqualTo(UPDATED_LIBELLE_SERVICE);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceFrequente() throws Exception {
        int databaseSizeBeforeUpdate = serviceFrequenteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceFrequenteMockMvc.perform(put("/api/service-frequentes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceFrequente)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceFrequente in the database
        List<ServiceFrequente> serviceFrequenteList = serviceFrequenteRepository.findAll();
        assertThat(serviceFrequenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceFrequente() throws Exception {
        // Initialize the database
        serviceFrequenteService.save(serviceFrequente);

        int databaseSizeBeforeDelete = serviceFrequenteRepository.findAll().size();

        // Delete the serviceFrequente
        restServiceFrequenteMockMvc.perform(delete("/api/service-frequentes/{id}", serviceFrequente.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceFrequente> serviceFrequenteList = serviceFrequenteRepository.findAll();
        assertThat(serviceFrequenteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
