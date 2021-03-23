package com.themalomars.gpecmanager.web.rest;

import com.themalomars.gpecmanager.GpecmanagerApp;
import com.themalomars.gpecmanager.domain.Distinction;
import com.themalomars.gpecmanager.domain.Employe;
import com.themalomars.gpecmanager.repository.DistinctionRepository;
import com.themalomars.gpecmanager.service.DistinctionService;
import com.themalomars.gpecmanager.service.dto.DistinctionCriteria;
import com.themalomars.gpecmanager.service.DistinctionQueryService;

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
 * Integration tests for the {@link DistinctionResource} REST controller.
 */
@SpringBootTest(classes = GpecmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DistinctionResourceIT {

    private static final String DEFAULT_LIBELLE_DISTINCTION = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_DISTINCTION = "BBBBBBBBBB";

    @Autowired
    private DistinctionRepository distinctionRepository;

    @Autowired
    private DistinctionService distinctionService;

    @Autowired
    private DistinctionQueryService distinctionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDistinctionMockMvc;

    private Distinction distinction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Distinction createEntity(EntityManager em) {
        Distinction distinction = new Distinction()
            .libelleDistinction(DEFAULT_LIBELLE_DISTINCTION);
        return distinction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Distinction createUpdatedEntity(EntityManager em) {
        Distinction distinction = new Distinction()
            .libelleDistinction(UPDATED_LIBELLE_DISTINCTION);
        return distinction;
    }

    @BeforeEach
    public void initTest() {
        distinction = createEntity(em);
    }

    @Test
    @Transactional
    public void createDistinction() throws Exception {
        int databaseSizeBeforeCreate = distinctionRepository.findAll().size();
        // Create the Distinction
        restDistinctionMockMvc.perform(post("/api/distinctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(distinction)))
            .andExpect(status().isCreated());

        // Validate the Distinction in the database
        List<Distinction> distinctionList = distinctionRepository.findAll();
        assertThat(distinctionList).hasSize(databaseSizeBeforeCreate + 1);
        Distinction testDistinction = distinctionList.get(distinctionList.size() - 1);
        assertThat(testDistinction.getLibelleDistinction()).isEqualTo(DEFAULT_LIBELLE_DISTINCTION);
    }

    @Test
    @Transactional
    public void createDistinctionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = distinctionRepository.findAll().size();

        // Create the Distinction with an existing ID
        distinction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDistinctionMockMvc.perform(post("/api/distinctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(distinction)))
            .andExpect(status().isBadRequest());

        // Validate the Distinction in the database
        List<Distinction> distinctionList = distinctionRepository.findAll();
        assertThat(distinctionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleDistinctionIsRequired() throws Exception {
        int databaseSizeBeforeTest = distinctionRepository.findAll().size();
        // set the field null
        distinction.setLibelleDistinction(null);

        // Create the Distinction, which fails.


        restDistinctionMockMvc.perform(post("/api/distinctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(distinction)))
            .andExpect(status().isBadRequest());

        List<Distinction> distinctionList = distinctionRepository.findAll();
        assertThat(distinctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDistinctions() throws Exception {
        // Initialize the database
        distinctionRepository.saveAndFlush(distinction);

        // Get all the distinctionList
        restDistinctionMockMvc.perform(get("/api/distinctions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(distinction.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleDistinction").value(hasItem(DEFAULT_LIBELLE_DISTINCTION)));
    }
    
    @Test
    @Transactional
    public void getDistinction() throws Exception {
        // Initialize the database
        distinctionRepository.saveAndFlush(distinction);

        // Get the distinction
        restDistinctionMockMvc.perform(get("/api/distinctions/{id}", distinction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(distinction.getId().intValue()))
            .andExpect(jsonPath("$.libelleDistinction").value(DEFAULT_LIBELLE_DISTINCTION));
    }


    @Test
    @Transactional
    public void getDistinctionsByIdFiltering() throws Exception {
        // Initialize the database
        distinctionRepository.saveAndFlush(distinction);

        Long id = distinction.getId();

        defaultDistinctionShouldBeFound("id.equals=" + id);
        defaultDistinctionShouldNotBeFound("id.notEquals=" + id);

        defaultDistinctionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDistinctionShouldNotBeFound("id.greaterThan=" + id);

        defaultDistinctionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDistinctionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDistinctionsByLibelleDistinctionIsEqualToSomething() throws Exception {
        // Initialize the database
        distinctionRepository.saveAndFlush(distinction);

        // Get all the distinctionList where libelleDistinction equals to DEFAULT_LIBELLE_DISTINCTION
        defaultDistinctionShouldBeFound("libelleDistinction.equals=" + DEFAULT_LIBELLE_DISTINCTION);

        // Get all the distinctionList where libelleDistinction equals to UPDATED_LIBELLE_DISTINCTION
        defaultDistinctionShouldNotBeFound("libelleDistinction.equals=" + UPDATED_LIBELLE_DISTINCTION);
    }

    @Test
    @Transactional
    public void getAllDistinctionsByLibelleDistinctionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        distinctionRepository.saveAndFlush(distinction);

        // Get all the distinctionList where libelleDistinction not equals to DEFAULT_LIBELLE_DISTINCTION
        defaultDistinctionShouldNotBeFound("libelleDistinction.notEquals=" + DEFAULT_LIBELLE_DISTINCTION);

        // Get all the distinctionList where libelleDistinction not equals to UPDATED_LIBELLE_DISTINCTION
        defaultDistinctionShouldBeFound("libelleDistinction.notEquals=" + UPDATED_LIBELLE_DISTINCTION);
    }

    @Test
    @Transactional
    public void getAllDistinctionsByLibelleDistinctionIsInShouldWork() throws Exception {
        // Initialize the database
        distinctionRepository.saveAndFlush(distinction);

        // Get all the distinctionList where libelleDistinction in DEFAULT_LIBELLE_DISTINCTION or UPDATED_LIBELLE_DISTINCTION
        defaultDistinctionShouldBeFound("libelleDistinction.in=" + DEFAULT_LIBELLE_DISTINCTION + "," + UPDATED_LIBELLE_DISTINCTION);

        // Get all the distinctionList where libelleDistinction equals to UPDATED_LIBELLE_DISTINCTION
        defaultDistinctionShouldNotBeFound("libelleDistinction.in=" + UPDATED_LIBELLE_DISTINCTION);
    }

    @Test
    @Transactional
    public void getAllDistinctionsByLibelleDistinctionIsNullOrNotNull() throws Exception {
        // Initialize the database
        distinctionRepository.saveAndFlush(distinction);

        // Get all the distinctionList where libelleDistinction is not null
        defaultDistinctionShouldBeFound("libelleDistinction.specified=true");

        // Get all the distinctionList where libelleDistinction is null
        defaultDistinctionShouldNotBeFound("libelleDistinction.specified=false");
    }
                @Test
    @Transactional
    public void getAllDistinctionsByLibelleDistinctionContainsSomething() throws Exception {
        // Initialize the database
        distinctionRepository.saveAndFlush(distinction);

        // Get all the distinctionList where libelleDistinction contains DEFAULT_LIBELLE_DISTINCTION
        defaultDistinctionShouldBeFound("libelleDistinction.contains=" + DEFAULT_LIBELLE_DISTINCTION);

        // Get all the distinctionList where libelleDistinction contains UPDATED_LIBELLE_DISTINCTION
        defaultDistinctionShouldNotBeFound("libelleDistinction.contains=" + UPDATED_LIBELLE_DISTINCTION);
    }

    @Test
    @Transactional
    public void getAllDistinctionsByLibelleDistinctionNotContainsSomething() throws Exception {
        // Initialize the database
        distinctionRepository.saveAndFlush(distinction);

        // Get all the distinctionList where libelleDistinction does not contain DEFAULT_LIBELLE_DISTINCTION
        defaultDistinctionShouldNotBeFound("libelleDistinction.doesNotContain=" + DEFAULT_LIBELLE_DISTINCTION);

        // Get all the distinctionList where libelleDistinction does not contain UPDATED_LIBELLE_DISTINCTION
        defaultDistinctionShouldBeFound("libelleDistinction.doesNotContain=" + UPDATED_LIBELLE_DISTINCTION);
    }


    @Test
    @Transactional
    public void getAllDistinctionsByEmployeIsEqualToSomething() throws Exception {
        // Initialize the database
        distinctionRepository.saveAndFlush(distinction);
        Employe employe = EmployeResourceIT.createEntity(em);
        em.persist(employe);
        em.flush();
        distinction.setEmploye(employe);
        distinctionRepository.saveAndFlush(distinction);
        Long employeId = employe.getId();

        // Get all the distinctionList where employe equals to employeId
        defaultDistinctionShouldBeFound("employeId.equals=" + employeId);

        // Get all the distinctionList where employe equals to employeId + 1
        defaultDistinctionShouldNotBeFound("employeId.equals=" + (employeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDistinctionShouldBeFound(String filter) throws Exception {
        restDistinctionMockMvc.perform(get("/api/distinctions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(distinction.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleDistinction").value(hasItem(DEFAULT_LIBELLE_DISTINCTION)));

        // Check, that the count call also returns 1
        restDistinctionMockMvc.perform(get("/api/distinctions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDistinctionShouldNotBeFound(String filter) throws Exception {
        restDistinctionMockMvc.perform(get("/api/distinctions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDistinctionMockMvc.perform(get("/api/distinctions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDistinction() throws Exception {
        // Get the distinction
        restDistinctionMockMvc.perform(get("/api/distinctions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDistinction() throws Exception {
        // Initialize the database
        distinctionService.save(distinction);

        int databaseSizeBeforeUpdate = distinctionRepository.findAll().size();

        // Update the distinction
        Distinction updatedDistinction = distinctionRepository.findById(distinction.getId()).get();
        // Disconnect from session so that the updates on updatedDistinction are not directly saved in db
        em.detach(updatedDistinction);
        updatedDistinction
            .libelleDistinction(UPDATED_LIBELLE_DISTINCTION);

        restDistinctionMockMvc.perform(put("/api/distinctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDistinction)))
            .andExpect(status().isOk());

        // Validate the Distinction in the database
        List<Distinction> distinctionList = distinctionRepository.findAll();
        assertThat(distinctionList).hasSize(databaseSizeBeforeUpdate);
        Distinction testDistinction = distinctionList.get(distinctionList.size() - 1);
        assertThat(testDistinction.getLibelleDistinction()).isEqualTo(UPDATED_LIBELLE_DISTINCTION);
    }

    @Test
    @Transactional
    public void updateNonExistingDistinction() throws Exception {
        int databaseSizeBeforeUpdate = distinctionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDistinctionMockMvc.perform(put("/api/distinctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(distinction)))
            .andExpect(status().isBadRequest());

        // Validate the Distinction in the database
        List<Distinction> distinctionList = distinctionRepository.findAll();
        assertThat(distinctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDistinction() throws Exception {
        // Initialize the database
        distinctionService.save(distinction);

        int databaseSizeBeforeDelete = distinctionRepository.findAll().size();

        // Delete the distinction
        restDistinctionMockMvc.perform(delete("/api/distinctions/{id}", distinction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Distinction> distinctionList = distinctionRepository.findAll();
        assertThat(distinctionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
