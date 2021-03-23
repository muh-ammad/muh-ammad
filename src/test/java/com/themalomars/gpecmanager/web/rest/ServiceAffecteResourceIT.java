package com.themalomars.gpecmanager.web.rest;

import com.themalomars.gpecmanager.GpecmanagerApp;
import com.themalomars.gpecmanager.domain.ServiceAffecte;
import com.themalomars.gpecmanager.domain.SecteurActivite;
import com.themalomars.gpecmanager.domain.Employe;
import com.themalomars.gpecmanager.repository.ServiceAffecteRepository;
import com.themalomars.gpecmanager.service.ServiceAffecteService;
import com.themalomars.gpecmanager.service.dto.ServiceAffecteCriteria;
import com.themalomars.gpecmanager.service.ServiceAffecteQueryService;

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
 * Integration tests for the {@link ServiceAffecteResource} REST controller.
 */
@SpringBootTest(classes = GpecmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ServiceAffecteResourceIT {

    private static final Long DEFAULT_CODE_SERVICE = 1L;
    private static final Long UPDATED_CODE_SERVICE = 2L;
    private static final Long SMALLER_CODE_SERVICE = 1L - 1L;

    private static final String DEFAULT_LIBELLE_SERVICE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_SERVICE = "BBBBBBBBBB";

    @Autowired
    private ServiceAffecteRepository serviceAffecteRepository;

    @Autowired
    private ServiceAffecteService serviceAffecteService;

    @Autowired
    private ServiceAffecteQueryService serviceAffecteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restServiceAffecteMockMvc;

    private ServiceAffecte serviceAffecte;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceAffecte createEntity(EntityManager em) {
        ServiceAffecte serviceAffecte = new ServiceAffecte()
            .codeService(DEFAULT_CODE_SERVICE)
            .libelleService(DEFAULT_LIBELLE_SERVICE);
        return serviceAffecte;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceAffecte createUpdatedEntity(EntityManager em) {
        ServiceAffecte serviceAffecte = new ServiceAffecte()
            .codeService(UPDATED_CODE_SERVICE)
            .libelleService(UPDATED_LIBELLE_SERVICE);
        return serviceAffecte;
    }

    @BeforeEach
    public void initTest() {
        serviceAffecte = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceAffecte() throws Exception {
        int databaseSizeBeforeCreate = serviceAffecteRepository.findAll().size();
        // Create the ServiceAffecte
        restServiceAffecteMockMvc.perform(post("/api/service-affectes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceAffecte)))
            .andExpect(status().isCreated());

        // Validate the ServiceAffecte in the database
        List<ServiceAffecte> serviceAffecteList = serviceAffecteRepository.findAll();
        assertThat(serviceAffecteList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceAffecte testServiceAffecte = serviceAffecteList.get(serviceAffecteList.size() - 1);
        assertThat(testServiceAffecte.getCodeService()).isEqualTo(DEFAULT_CODE_SERVICE);
        assertThat(testServiceAffecte.getLibelleService()).isEqualTo(DEFAULT_LIBELLE_SERVICE);
    }

    @Test
    @Transactional
    public void createServiceAffecteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceAffecteRepository.findAll().size();

        // Create the ServiceAffecte with an existing ID
        serviceAffecte.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceAffecteMockMvc.perform(post("/api/service-affectes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceAffecte)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceAffecte in the database
        List<ServiceAffecte> serviceAffecteList = serviceAffecteRepository.findAll();
        assertThat(serviceAffecteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeServiceIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceAffecteRepository.findAll().size();
        // set the field null
        serviceAffecte.setCodeService(null);

        // Create the ServiceAffecte, which fails.


        restServiceAffecteMockMvc.perform(post("/api/service-affectes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceAffecte)))
            .andExpect(status().isBadRequest());

        List<ServiceAffecte> serviceAffecteList = serviceAffecteRepository.findAll();
        assertThat(serviceAffecteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleServiceIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceAffecteRepository.findAll().size();
        // set the field null
        serviceAffecte.setLibelleService(null);

        // Create the ServiceAffecte, which fails.


        restServiceAffecteMockMvc.perform(post("/api/service-affectes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceAffecte)))
            .andExpect(status().isBadRequest());

        List<ServiceAffecte> serviceAffecteList = serviceAffecteRepository.findAll();
        assertThat(serviceAffecteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceAffectes() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);

        // Get all the serviceAffecteList
        restServiceAffecteMockMvc.perform(get("/api/service-affectes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceAffecte.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeService").value(hasItem(DEFAULT_CODE_SERVICE.intValue())))
            .andExpect(jsonPath("$.[*].libelleService").value(hasItem(DEFAULT_LIBELLE_SERVICE)));
    }
    
    @Test
    @Transactional
    public void getServiceAffecte() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);

        // Get the serviceAffecte
        restServiceAffecteMockMvc.perform(get("/api/service-affectes/{id}", serviceAffecte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(serviceAffecte.getId().intValue()))
            .andExpect(jsonPath("$.codeService").value(DEFAULT_CODE_SERVICE.intValue()))
            .andExpect(jsonPath("$.libelleService").value(DEFAULT_LIBELLE_SERVICE));
    }


    @Test
    @Transactional
    public void getServiceAffectesByIdFiltering() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);

        Long id = serviceAffecte.getId();

        defaultServiceAffecteShouldBeFound("id.equals=" + id);
        defaultServiceAffecteShouldNotBeFound("id.notEquals=" + id);

        defaultServiceAffecteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultServiceAffecteShouldNotBeFound("id.greaterThan=" + id);

        defaultServiceAffecteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultServiceAffecteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllServiceAffectesByCodeServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);

        // Get all the serviceAffecteList where codeService equals to DEFAULT_CODE_SERVICE
        defaultServiceAffecteShouldBeFound("codeService.equals=" + DEFAULT_CODE_SERVICE);

        // Get all the serviceAffecteList where codeService equals to UPDATED_CODE_SERVICE
        defaultServiceAffecteShouldNotBeFound("codeService.equals=" + UPDATED_CODE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceAffectesByCodeServiceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);

        // Get all the serviceAffecteList where codeService not equals to DEFAULT_CODE_SERVICE
        defaultServiceAffecteShouldNotBeFound("codeService.notEquals=" + DEFAULT_CODE_SERVICE);

        // Get all the serviceAffecteList where codeService not equals to UPDATED_CODE_SERVICE
        defaultServiceAffecteShouldBeFound("codeService.notEquals=" + UPDATED_CODE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceAffectesByCodeServiceIsInShouldWork() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);

        // Get all the serviceAffecteList where codeService in DEFAULT_CODE_SERVICE or UPDATED_CODE_SERVICE
        defaultServiceAffecteShouldBeFound("codeService.in=" + DEFAULT_CODE_SERVICE + "," + UPDATED_CODE_SERVICE);

        // Get all the serviceAffecteList where codeService equals to UPDATED_CODE_SERVICE
        defaultServiceAffecteShouldNotBeFound("codeService.in=" + UPDATED_CODE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceAffectesByCodeServiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);

        // Get all the serviceAffecteList where codeService is not null
        defaultServiceAffecteShouldBeFound("codeService.specified=true");

        // Get all the serviceAffecteList where codeService is null
        defaultServiceAffecteShouldNotBeFound("codeService.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceAffectesByCodeServiceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);

        // Get all the serviceAffecteList where codeService is greater than or equal to DEFAULT_CODE_SERVICE
        defaultServiceAffecteShouldBeFound("codeService.greaterThanOrEqual=" + DEFAULT_CODE_SERVICE);

        // Get all the serviceAffecteList where codeService is greater than or equal to UPDATED_CODE_SERVICE
        defaultServiceAffecteShouldNotBeFound("codeService.greaterThanOrEqual=" + UPDATED_CODE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceAffectesByCodeServiceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);

        // Get all the serviceAffecteList where codeService is less than or equal to DEFAULT_CODE_SERVICE
        defaultServiceAffecteShouldBeFound("codeService.lessThanOrEqual=" + DEFAULT_CODE_SERVICE);

        // Get all the serviceAffecteList where codeService is less than or equal to SMALLER_CODE_SERVICE
        defaultServiceAffecteShouldNotBeFound("codeService.lessThanOrEqual=" + SMALLER_CODE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceAffectesByCodeServiceIsLessThanSomething() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);

        // Get all the serviceAffecteList where codeService is less than DEFAULT_CODE_SERVICE
        defaultServiceAffecteShouldNotBeFound("codeService.lessThan=" + DEFAULT_CODE_SERVICE);

        // Get all the serviceAffecteList where codeService is less than UPDATED_CODE_SERVICE
        defaultServiceAffecteShouldBeFound("codeService.lessThan=" + UPDATED_CODE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceAffectesByCodeServiceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);

        // Get all the serviceAffecteList where codeService is greater than DEFAULT_CODE_SERVICE
        defaultServiceAffecteShouldNotBeFound("codeService.greaterThan=" + DEFAULT_CODE_SERVICE);

        // Get all the serviceAffecteList where codeService is greater than SMALLER_CODE_SERVICE
        defaultServiceAffecteShouldBeFound("codeService.greaterThan=" + SMALLER_CODE_SERVICE);
    }


    @Test
    @Transactional
    public void getAllServiceAffectesByLibelleServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);

        // Get all the serviceAffecteList where libelleService equals to DEFAULT_LIBELLE_SERVICE
        defaultServiceAffecteShouldBeFound("libelleService.equals=" + DEFAULT_LIBELLE_SERVICE);

        // Get all the serviceAffecteList where libelleService equals to UPDATED_LIBELLE_SERVICE
        defaultServiceAffecteShouldNotBeFound("libelleService.equals=" + UPDATED_LIBELLE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceAffectesByLibelleServiceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);

        // Get all the serviceAffecteList where libelleService not equals to DEFAULT_LIBELLE_SERVICE
        defaultServiceAffecteShouldNotBeFound("libelleService.notEquals=" + DEFAULT_LIBELLE_SERVICE);

        // Get all the serviceAffecteList where libelleService not equals to UPDATED_LIBELLE_SERVICE
        defaultServiceAffecteShouldBeFound("libelleService.notEquals=" + UPDATED_LIBELLE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceAffectesByLibelleServiceIsInShouldWork() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);

        // Get all the serviceAffecteList where libelleService in DEFAULT_LIBELLE_SERVICE or UPDATED_LIBELLE_SERVICE
        defaultServiceAffecteShouldBeFound("libelleService.in=" + DEFAULT_LIBELLE_SERVICE + "," + UPDATED_LIBELLE_SERVICE);

        // Get all the serviceAffecteList where libelleService equals to UPDATED_LIBELLE_SERVICE
        defaultServiceAffecteShouldNotBeFound("libelleService.in=" + UPDATED_LIBELLE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceAffectesByLibelleServiceIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);

        // Get all the serviceAffecteList where libelleService is not null
        defaultServiceAffecteShouldBeFound("libelleService.specified=true");

        // Get all the serviceAffecteList where libelleService is null
        defaultServiceAffecteShouldNotBeFound("libelleService.specified=false");
    }
                @Test
    @Transactional
    public void getAllServiceAffectesByLibelleServiceContainsSomething() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);

        // Get all the serviceAffecteList where libelleService contains DEFAULT_LIBELLE_SERVICE
        defaultServiceAffecteShouldBeFound("libelleService.contains=" + DEFAULT_LIBELLE_SERVICE);

        // Get all the serviceAffecteList where libelleService contains UPDATED_LIBELLE_SERVICE
        defaultServiceAffecteShouldNotBeFound("libelleService.contains=" + UPDATED_LIBELLE_SERVICE);
    }

    @Test
    @Transactional
    public void getAllServiceAffectesByLibelleServiceNotContainsSomething() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);

        // Get all the serviceAffecteList where libelleService does not contain DEFAULT_LIBELLE_SERVICE
        defaultServiceAffecteShouldNotBeFound("libelleService.doesNotContain=" + DEFAULT_LIBELLE_SERVICE);

        // Get all the serviceAffecteList where libelleService does not contain UPDATED_LIBELLE_SERVICE
        defaultServiceAffecteShouldBeFound("libelleService.doesNotContain=" + UPDATED_LIBELLE_SERVICE);
    }


    @Test
    @Transactional
    public void getAllServiceAffectesBySecteurActiviteIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);
        SecteurActivite secteurActivite = SecteurActiviteResourceIT.createEntity(em);
        em.persist(secteurActivite);
        em.flush();
        serviceAffecte.setSecteurActivite(secteurActivite);
        serviceAffecteRepository.saveAndFlush(serviceAffecte);
        Long secteurActiviteId = secteurActivite.getId();

        // Get all the serviceAffecteList where secteurActivite equals to secteurActiviteId
        defaultServiceAffecteShouldBeFound("secteurActiviteId.equals=" + secteurActiviteId);

        // Get all the serviceAffecteList where secteurActivite equals to secteurActiviteId + 1
        defaultServiceAffecteShouldNotBeFound("secteurActiviteId.equals=" + (secteurActiviteId + 1));
    }


    @Test
    @Transactional
    public void getAllServiceAffectesByEmployeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceAffecteRepository.saveAndFlush(serviceAffecte);
        Employe employe = EmployeResourceIT.createEntity(em);
        em.persist(employe);
        em.flush();
        serviceAffecte.addEmploye(employe);
        serviceAffecteRepository.saveAndFlush(serviceAffecte);
        Long employeId = employe.getId();

        // Get all the serviceAffecteList where employe equals to employeId
        defaultServiceAffecteShouldBeFound("employeId.equals=" + employeId);

        // Get all the serviceAffecteList where employe equals to employeId + 1
        defaultServiceAffecteShouldNotBeFound("employeId.equals=" + (employeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceAffecteShouldBeFound(String filter) throws Exception {
        restServiceAffecteMockMvc.perform(get("/api/service-affectes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceAffecte.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeService").value(hasItem(DEFAULT_CODE_SERVICE.intValue())))
            .andExpect(jsonPath("$.[*].libelleService").value(hasItem(DEFAULT_LIBELLE_SERVICE)));

        // Check, that the count call also returns 1
        restServiceAffecteMockMvc.perform(get("/api/service-affectes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceAffecteShouldNotBeFound(String filter) throws Exception {
        restServiceAffecteMockMvc.perform(get("/api/service-affectes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceAffecteMockMvc.perform(get("/api/service-affectes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingServiceAffecte() throws Exception {
        // Get the serviceAffecte
        restServiceAffecteMockMvc.perform(get("/api/service-affectes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceAffecte() throws Exception {
        // Initialize the database
        serviceAffecteService.save(serviceAffecte);

        int databaseSizeBeforeUpdate = serviceAffecteRepository.findAll().size();

        // Update the serviceAffecte
        ServiceAffecte updatedServiceAffecte = serviceAffecteRepository.findById(serviceAffecte.getId()).get();
        // Disconnect from session so that the updates on updatedServiceAffecte are not directly saved in db
        em.detach(updatedServiceAffecte);
        updatedServiceAffecte
            .codeService(UPDATED_CODE_SERVICE)
            .libelleService(UPDATED_LIBELLE_SERVICE);

        restServiceAffecteMockMvc.perform(put("/api/service-affectes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedServiceAffecte)))
            .andExpect(status().isOk());

        // Validate the ServiceAffecte in the database
        List<ServiceAffecte> serviceAffecteList = serviceAffecteRepository.findAll();
        assertThat(serviceAffecteList).hasSize(databaseSizeBeforeUpdate);
        ServiceAffecte testServiceAffecte = serviceAffecteList.get(serviceAffecteList.size() - 1);
        assertThat(testServiceAffecte.getCodeService()).isEqualTo(UPDATED_CODE_SERVICE);
        assertThat(testServiceAffecte.getLibelleService()).isEqualTo(UPDATED_LIBELLE_SERVICE);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceAffecte() throws Exception {
        int databaseSizeBeforeUpdate = serviceAffecteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceAffecteMockMvc.perform(put("/api/service-affectes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(serviceAffecte)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceAffecte in the database
        List<ServiceAffecte> serviceAffecteList = serviceAffecteRepository.findAll();
        assertThat(serviceAffecteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteServiceAffecte() throws Exception {
        // Initialize the database
        serviceAffecteService.save(serviceAffecte);

        int databaseSizeBeforeDelete = serviceAffecteRepository.findAll().size();

        // Delete the serviceAffecte
        restServiceAffecteMockMvc.perform(delete("/api/service-affectes/{id}", serviceAffecte.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ServiceAffecte> serviceAffecteList = serviceAffecteRepository.findAll();
        assertThat(serviceAffecteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
