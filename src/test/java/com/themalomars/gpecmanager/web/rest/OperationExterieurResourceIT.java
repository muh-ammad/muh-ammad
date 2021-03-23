package com.themalomars.gpecmanager.web.rest;

import com.themalomars.gpecmanager.GpecmanagerApp;
import com.themalomars.gpecmanager.domain.OperationExterieur;
import com.themalomars.gpecmanager.domain.Employe;
import com.themalomars.gpecmanager.repository.OperationExterieurRepository;
import com.themalomars.gpecmanager.service.OperationExterieurService;
import com.themalomars.gpecmanager.service.dto.OperationExterieurCriteria;
import com.themalomars.gpecmanager.service.OperationExterieurQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OperationExterieurResource} REST controller.
 */
@SpringBootTest(classes = GpecmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OperationExterieurResourceIT {

    private static final String DEFAULT_LIEU_OPEX = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_OPEX = "BBBBBBBBBB";

    private static final Instant DEFAULT_ANNEE_OPEX = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ANNEE_OPEX = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private OperationExterieurRepository operationExterieurRepository;

    @Autowired
    private OperationExterieurService operationExterieurService;

    @Autowired
    private OperationExterieurQueryService operationExterieurQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOperationExterieurMockMvc;

    private OperationExterieur operationExterieur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationExterieur createEntity(EntityManager em) {
        OperationExterieur operationExterieur = new OperationExterieur()
            .lieuOpex(DEFAULT_LIEU_OPEX)
            .anneeOpex(DEFAULT_ANNEE_OPEX);
        return operationExterieur;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OperationExterieur createUpdatedEntity(EntityManager em) {
        OperationExterieur operationExterieur = new OperationExterieur()
            .lieuOpex(UPDATED_LIEU_OPEX)
            .anneeOpex(UPDATED_ANNEE_OPEX);
        return operationExterieur;
    }

    @BeforeEach
    public void initTest() {
        operationExterieur = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperationExterieur() throws Exception {
        int databaseSizeBeforeCreate = operationExterieurRepository.findAll().size();
        // Create the OperationExterieur
        restOperationExterieurMockMvc.perform(post("/api/operation-exterieurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operationExterieur)))
            .andExpect(status().isCreated());

        // Validate the OperationExterieur in the database
        List<OperationExterieur> operationExterieurList = operationExterieurRepository.findAll();
        assertThat(operationExterieurList).hasSize(databaseSizeBeforeCreate + 1);
        OperationExterieur testOperationExterieur = operationExterieurList.get(operationExterieurList.size() - 1);
        assertThat(testOperationExterieur.getLieuOpex()).isEqualTo(DEFAULT_LIEU_OPEX);
        assertThat(testOperationExterieur.getAnneeOpex()).isEqualTo(DEFAULT_ANNEE_OPEX);
    }

    @Test
    @Transactional
    public void createOperationExterieurWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operationExterieurRepository.findAll().size();

        // Create the OperationExterieur with an existing ID
        operationExterieur.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperationExterieurMockMvc.perform(post("/api/operation-exterieurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operationExterieur)))
            .andExpect(status().isBadRequest());

        // Validate the OperationExterieur in the database
        List<OperationExterieur> operationExterieurList = operationExterieurRepository.findAll();
        assertThat(operationExterieurList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLieuOpexIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationExterieurRepository.findAll().size();
        // set the field null
        operationExterieur.setLieuOpex(null);

        // Create the OperationExterieur, which fails.


        restOperationExterieurMockMvc.perform(post("/api/operation-exterieurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operationExterieur)))
            .andExpect(status().isBadRequest());

        List<OperationExterieur> operationExterieurList = operationExterieurRepository.findAll();
        assertThat(operationExterieurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnneeOpexIsRequired() throws Exception {
        int databaseSizeBeforeTest = operationExterieurRepository.findAll().size();
        // set the field null
        operationExterieur.setAnneeOpex(null);

        // Create the OperationExterieur, which fails.


        restOperationExterieurMockMvc.perform(post("/api/operation-exterieurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operationExterieur)))
            .andExpect(status().isBadRequest());

        List<OperationExterieur> operationExterieurList = operationExterieurRepository.findAll();
        assertThat(operationExterieurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOperationExterieurs() throws Exception {
        // Initialize the database
        operationExterieurRepository.saveAndFlush(operationExterieur);

        // Get all the operationExterieurList
        restOperationExterieurMockMvc.perform(get("/api/operation-exterieurs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operationExterieur.getId().intValue())))
            .andExpect(jsonPath("$.[*].lieuOpex").value(hasItem(DEFAULT_LIEU_OPEX)))
            .andExpect(jsonPath("$.[*].anneeOpex").value(hasItem(DEFAULT_ANNEE_OPEX.toString())));
    }
    
    @Test
    @Transactional
    public void getOperationExterieur() throws Exception {
        // Initialize the database
        operationExterieurRepository.saveAndFlush(operationExterieur);

        // Get the operationExterieur
        restOperationExterieurMockMvc.perform(get("/api/operation-exterieurs/{id}", operationExterieur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(operationExterieur.getId().intValue()))
            .andExpect(jsonPath("$.lieuOpex").value(DEFAULT_LIEU_OPEX))
            .andExpect(jsonPath("$.anneeOpex").value(DEFAULT_ANNEE_OPEX.toString()));
    }


    @Test
    @Transactional
    public void getOperationExterieursByIdFiltering() throws Exception {
        // Initialize the database
        operationExterieurRepository.saveAndFlush(operationExterieur);

        Long id = operationExterieur.getId();

        defaultOperationExterieurShouldBeFound("id.equals=" + id);
        defaultOperationExterieurShouldNotBeFound("id.notEquals=" + id);

        defaultOperationExterieurShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOperationExterieurShouldNotBeFound("id.greaterThan=" + id);

        defaultOperationExterieurShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOperationExterieurShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOperationExterieursByLieuOpexIsEqualToSomething() throws Exception {
        // Initialize the database
        operationExterieurRepository.saveAndFlush(operationExterieur);

        // Get all the operationExterieurList where lieuOpex equals to DEFAULT_LIEU_OPEX
        defaultOperationExterieurShouldBeFound("lieuOpex.equals=" + DEFAULT_LIEU_OPEX);

        // Get all the operationExterieurList where lieuOpex equals to UPDATED_LIEU_OPEX
        defaultOperationExterieurShouldNotBeFound("lieuOpex.equals=" + UPDATED_LIEU_OPEX);
    }

    @Test
    @Transactional
    public void getAllOperationExterieursByLieuOpexIsNotEqualToSomething() throws Exception {
        // Initialize the database
        operationExterieurRepository.saveAndFlush(operationExterieur);

        // Get all the operationExterieurList where lieuOpex not equals to DEFAULT_LIEU_OPEX
        defaultOperationExterieurShouldNotBeFound("lieuOpex.notEquals=" + DEFAULT_LIEU_OPEX);

        // Get all the operationExterieurList where lieuOpex not equals to UPDATED_LIEU_OPEX
        defaultOperationExterieurShouldBeFound("lieuOpex.notEquals=" + UPDATED_LIEU_OPEX);
    }

    @Test
    @Transactional
    public void getAllOperationExterieursByLieuOpexIsInShouldWork() throws Exception {
        // Initialize the database
        operationExterieurRepository.saveAndFlush(operationExterieur);

        // Get all the operationExterieurList where lieuOpex in DEFAULT_LIEU_OPEX or UPDATED_LIEU_OPEX
        defaultOperationExterieurShouldBeFound("lieuOpex.in=" + DEFAULT_LIEU_OPEX + "," + UPDATED_LIEU_OPEX);

        // Get all the operationExterieurList where lieuOpex equals to UPDATED_LIEU_OPEX
        defaultOperationExterieurShouldNotBeFound("lieuOpex.in=" + UPDATED_LIEU_OPEX);
    }

    @Test
    @Transactional
    public void getAllOperationExterieursByLieuOpexIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationExterieurRepository.saveAndFlush(operationExterieur);

        // Get all the operationExterieurList where lieuOpex is not null
        defaultOperationExterieurShouldBeFound("lieuOpex.specified=true");

        // Get all the operationExterieurList where lieuOpex is null
        defaultOperationExterieurShouldNotBeFound("lieuOpex.specified=false");
    }
                @Test
    @Transactional
    public void getAllOperationExterieursByLieuOpexContainsSomething() throws Exception {
        // Initialize the database
        operationExterieurRepository.saveAndFlush(operationExterieur);

        // Get all the operationExterieurList where lieuOpex contains DEFAULT_LIEU_OPEX
        defaultOperationExterieurShouldBeFound("lieuOpex.contains=" + DEFAULT_LIEU_OPEX);

        // Get all the operationExterieurList where lieuOpex contains UPDATED_LIEU_OPEX
        defaultOperationExterieurShouldNotBeFound("lieuOpex.contains=" + UPDATED_LIEU_OPEX);
    }

    @Test
    @Transactional
    public void getAllOperationExterieursByLieuOpexNotContainsSomething() throws Exception {
        // Initialize the database
        operationExterieurRepository.saveAndFlush(operationExterieur);

        // Get all the operationExterieurList where lieuOpex does not contain DEFAULT_LIEU_OPEX
        defaultOperationExterieurShouldNotBeFound("lieuOpex.doesNotContain=" + DEFAULT_LIEU_OPEX);

        // Get all the operationExterieurList where lieuOpex does not contain UPDATED_LIEU_OPEX
        defaultOperationExterieurShouldBeFound("lieuOpex.doesNotContain=" + UPDATED_LIEU_OPEX);
    }


    @Test
    @Transactional
    public void getAllOperationExterieursByAnneeOpexIsEqualToSomething() throws Exception {
        // Initialize the database
        operationExterieurRepository.saveAndFlush(operationExterieur);

        // Get all the operationExterieurList where anneeOpex equals to DEFAULT_ANNEE_OPEX
        defaultOperationExterieurShouldBeFound("anneeOpex.equals=" + DEFAULT_ANNEE_OPEX);

        // Get all the operationExterieurList where anneeOpex equals to UPDATED_ANNEE_OPEX
        defaultOperationExterieurShouldNotBeFound("anneeOpex.equals=" + UPDATED_ANNEE_OPEX);
    }

    @Test
    @Transactional
    public void getAllOperationExterieursByAnneeOpexIsNotEqualToSomething() throws Exception {
        // Initialize the database
        operationExterieurRepository.saveAndFlush(operationExterieur);

        // Get all the operationExterieurList where anneeOpex not equals to DEFAULT_ANNEE_OPEX
        defaultOperationExterieurShouldNotBeFound("anneeOpex.notEquals=" + DEFAULT_ANNEE_OPEX);

        // Get all the operationExterieurList where anneeOpex not equals to UPDATED_ANNEE_OPEX
        defaultOperationExterieurShouldBeFound("anneeOpex.notEquals=" + UPDATED_ANNEE_OPEX);
    }

    @Test
    @Transactional
    public void getAllOperationExterieursByAnneeOpexIsInShouldWork() throws Exception {
        // Initialize the database
        operationExterieurRepository.saveAndFlush(operationExterieur);

        // Get all the operationExterieurList where anneeOpex in DEFAULT_ANNEE_OPEX or UPDATED_ANNEE_OPEX
        defaultOperationExterieurShouldBeFound("anneeOpex.in=" + DEFAULT_ANNEE_OPEX + "," + UPDATED_ANNEE_OPEX);

        // Get all the operationExterieurList where anneeOpex equals to UPDATED_ANNEE_OPEX
        defaultOperationExterieurShouldNotBeFound("anneeOpex.in=" + UPDATED_ANNEE_OPEX);
    }

    @Test
    @Transactional
    public void getAllOperationExterieursByAnneeOpexIsNullOrNotNull() throws Exception {
        // Initialize the database
        operationExterieurRepository.saveAndFlush(operationExterieur);

        // Get all the operationExterieurList where anneeOpex is not null
        defaultOperationExterieurShouldBeFound("anneeOpex.specified=true");

        // Get all the operationExterieurList where anneeOpex is null
        defaultOperationExterieurShouldNotBeFound("anneeOpex.specified=false");
    }

    @Test
    @Transactional
    public void getAllOperationExterieursByEmployeIsEqualToSomething() throws Exception {
        // Initialize the database
        operationExterieurRepository.saveAndFlush(operationExterieur);
        Employe employe = EmployeResourceIT.createEntity(em);
        em.persist(employe);
        em.flush();
        operationExterieur.setEmploye(employe);
        operationExterieurRepository.saveAndFlush(operationExterieur);
        Long employeId = employe.getId();

        // Get all the operationExterieurList where employe equals to employeId
        defaultOperationExterieurShouldBeFound("employeId.equals=" + employeId);

        // Get all the operationExterieurList where employe equals to employeId + 1
        defaultOperationExterieurShouldNotBeFound("employeId.equals=" + (employeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOperationExterieurShouldBeFound(String filter) throws Exception {
        restOperationExterieurMockMvc.perform(get("/api/operation-exterieurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operationExterieur.getId().intValue())))
            .andExpect(jsonPath("$.[*].lieuOpex").value(hasItem(DEFAULT_LIEU_OPEX)))
            .andExpect(jsonPath("$.[*].anneeOpex").value(hasItem(DEFAULT_ANNEE_OPEX.toString())));

        // Check, that the count call also returns 1
        restOperationExterieurMockMvc.perform(get("/api/operation-exterieurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOperationExterieurShouldNotBeFound(String filter) throws Exception {
        restOperationExterieurMockMvc.perform(get("/api/operation-exterieurs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOperationExterieurMockMvc.perform(get("/api/operation-exterieurs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOperationExterieur() throws Exception {
        // Get the operationExterieur
        restOperationExterieurMockMvc.perform(get("/api/operation-exterieurs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperationExterieur() throws Exception {
        // Initialize the database
        operationExterieurService.save(operationExterieur);

        int databaseSizeBeforeUpdate = operationExterieurRepository.findAll().size();

        // Update the operationExterieur
        OperationExterieur updatedOperationExterieur = operationExterieurRepository.findById(operationExterieur.getId()).get();
        // Disconnect from session so that the updates on updatedOperationExterieur are not directly saved in db
        em.detach(updatedOperationExterieur);
        updatedOperationExterieur
            .lieuOpex(UPDATED_LIEU_OPEX)
            .anneeOpex(UPDATED_ANNEE_OPEX);

        restOperationExterieurMockMvc.perform(put("/api/operation-exterieurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOperationExterieur)))
            .andExpect(status().isOk());

        // Validate the OperationExterieur in the database
        List<OperationExterieur> operationExterieurList = operationExterieurRepository.findAll();
        assertThat(operationExterieurList).hasSize(databaseSizeBeforeUpdate);
        OperationExterieur testOperationExterieur = operationExterieurList.get(operationExterieurList.size() - 1);
        assertThat(testOperationExterieur.getLieuOpex()).isEqualTo(UPDATED_LIEU_OPEX);
        assertThat(testOperationExterieur.getAnneeOpex()).isEqualTo(UPDATED_ANNEE_OPEX);
    }

    @Test
    @Transactional
    public void updateNonExistingOperationExterieur() throws Exception {
        int databaseSizeBeforeUpdate = operationExterieurRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOperationExterieurMockMvc.perform(put("/api/operation-exterieurs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(operationExterieur)))
            .andExpect(status().isBadRequest());

        // Validate the OperationExterieur in the database
        List<OperationExterieur> operationExterieurList = operationExterieurRepository.findAll();
        assertThat(operationExterieurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOperationExterieur() throws Exception {
        // Initialize the database
        operationExterieurService.save(operationExterieur);

        int databaseSizeBeforeDelete = operationExterieurRepository.findAll().size();

        // Delete the operationExterieur
        restOperationExterieurMockMvc.perform(delete("/api/operation-exterieurs/{id}", operationExterieur.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OperationExterieur> operationExterieurList = operationExterieurRepository.findAll();
        assertThat(operationExterieurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
