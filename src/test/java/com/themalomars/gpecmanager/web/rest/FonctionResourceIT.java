package com.themalomars.gpecmanager.web.rest;

import com.themalomars.gpecmanager.GpecmanagerApp;
import com.themalomars.gpecmanager.domain.Fonction;
import com.themalomars.gpecmanager.domain.Specialite;
import com.themalomars.gpecmanager.domain.Employe;
import com.themalomars.gpecmanager.repository.FonctionRepository;
import com.themalomars.gpecmanager.service.FonctionService;
import com.themalomars.gpecmanager.service.dto.FonctionCriteria;
import com.themalomars.gpecmanager.service.FonctionQueryService;

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
 * Integration tests for the {@link FonctionResource} REST controller.
 */
@SpringBootTest(classes = GpecmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FonctionResourceIT {

    private static final String DEFAULT_LIBELLE_FONCTION = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_FONCTION = "BBBBBBBBBB";

    @Autowired
    private FonctionRepository fonctionRepository;

    @Autowired
    private FonctionService fonctionService;

    @Autowired
    private FonctionQueryService fonctionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFonctionMockMvc;

    private Fonction fonction;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fonction createEntity(EntityManager em) {
        Fonction fonction = new Fonction()
            .libelleFonction(DEFAULT_LIBELLE_FONCTION);
        return fonction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fonction createUpdatedEntity(EntityManager em) {
        Fonction fonction = new Fonction()
            .libelleFonction(UPDATED_LIBELLE_FONCTION);
        return fonction;
    }

    @BeforeEach
    public void initTest() {
        fonction = createEntity(em);
    }

    @Test
    @Transactional
    public void createFonction() throws Exception {
        int databaseSizeBeforeCreate = fonctionRepository.findAll().size();
        // Create the Fonction
        restFonctionMockMvc.perform(post("/api/fonctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fonction)))
            .andExpect(status().isCreated());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeCreate + 1);
        Fonction testFonction = fonctionList.get(fonctionList.size() - 1);
        assertThat(testFonction.getLibelleFonction()).isEqualTo(DEFAULT_LIBELLE_FONCTION);
    }

    @Test
    @Transactional
    public void createFonctionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fonctionRepository.findAll().size();

        // Create the Fonction with an existing ID
        fonction.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFonctionMockMvc.perform(post("/api/fonctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fonction)))
            .andExpect(status().isBadRequest());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleFonctionIsRequired() throws Exception {
        int databaseSizeBeforeTest = fonctionRepository.findAll().size();
        // set the field null
        fonction.setLibelleFonction(null);

        // Create the Fonction, which fails.


        restFonctionMockMvc.perform(post("/api/fonctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fonction)))
            .andExpect(status().isBadRequest());

        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFonctions() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList
        restFonctionMockMvc.perform(get("/api/fonctions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fonction.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleFonction").value(hasItem(DEFAULT_LIBELLE_FONCTION)));
    }
    
    @Test
    @Transactional
    public void getFonction() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get the fonction
        restFonctionMockMvc.perform(get("/api/fonctions/{id}", fonction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fonction.getId().intValue()))
            .andExpect(jsonPath("$.libelleFonction").value(DEFAULT_LIBELLE_FONCTION));
    }


    @Test
    @Transactional
    public void getFonctionsByIdFiltering() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        Long id = fonction.getId();

        defaultFonctionShouldBeFound("id.equals=" + id);
        defaultFonctionShouldNotBeFound("id.notEquals=" + id);

        defaultFonctionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFonctionShouldNotBeFound("id.greaterThan=" + id);

        defaultFonctionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFonctionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFonctionsByLibelleFonctionIsEqualToSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where libelleFonction equals to DEFAULT_LIBELLE_FONCTION
        defaultFonctionShouldBeFound("libelleFonction.equals=" + DEFAULT_LIBELLE_FONCTION);

        // Get all the fonctionList where libelleFonction equals to UPDATED_LIBELLE_FONCTION
        defaultFonctionShouldNotBeFound("libelleFonction.equals=" + UPDATED_LIBELLE_FONCTION);
    }

    @Test
    @Transactional
    public void getAllFonctionsByLibelleFonctionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where libelleFonction not equals to DEFAULT_LIBELLE_FONCTION
        defaultFonctionShouldNotBeFound("libelleFonction.notEquals=" + DEFAULT_LIBELLE_FONCTION);

        // Get all the fonctionList where libelleFonction not equals to UPDATED_LIBELLE_FONCTION
        defaultFonctionShouldBeFound("libelleFonction.notEquals=" + UPDATED_LIBELLE_FONCTION);
    }

    @Test
    @Transactional
    public void getAllFonctionsByLibelleFonctionIsInShouldWork() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where libelleFonction in DEFAULT_LIBELLE_FONCTION or UPDATED_LIBELLE_FONCTION
        defaultFonctionShouldBeFound("libelleFonction.in=" + DEFAULT_LIBELLE_FONCTION + "," + UPDATED_LIBELLE_FONCTION);

        // Get all the fonctionList where libelleFonction equals to UPDATED_LIBELLE_FONCTION
        defaultFonctionShouldNotBeFound("libelleFonction.in=" + UPDATED_LIBELLE_FONCTION);
    }

    @Test
    @Transactional
    public void getAllFonctionsByLibelleFonctionIsNullOrNotNull() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where libelleFonction is not null
        defaultFonctionShouldBeFound("libelleFonction.specified=true");

        // Get all the fonctionList where libelleFonction is null
        defaultFonctionShouldNotBeFound("libelleFonction.specified=false");
    }
                @Test
    @Transactional
    public void getAllFonctionsByLibelleFonctionContainsSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where libelleFonction contains DEFAULT_LIBELLE_FONCTION
        defaultFonctionShouldBeFound("libelleFonction.contains=" + DEFAULT_LIBELLE_FONCTION);

        // Get all the fonctionList where libelleFonction contains UPDATED_LIBELLE_FONCTION
        defaultFonctionShouldNotBeFound("libelleFonction.contains=" + UPDATED_LIBELLE_FONCTION);
    }

    @Test
    @Transactional
    public void getAllFonctionsByLibelleFonctionNotContainsSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);

        // Get all the fonctionList where libelleFonction does not contain DEFAULT_LIBELLE_FONCTION
        defaultFonctionShouldNotBeFound("libelleFonction.doesNotContain=" + DEFAULT_LIBELLE_FONCTION);

        // Get all the fonctionList where libelleFonction does not contain UPDATED_LIBELLE_FONCTION
        defaultFonctionShouldBeFound("libelleFonction.doesNotContain=" + UPDATED_LIBELLE_FONCTION);
    }


    @Test
    @Transactional
    public void getAllFonctionsBySpecialiteIsEqualToSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);
        Specialite specialite = SpecialiteResourceIT.createEntity(em);
        em.persist(specialite);
        em.flush();
        fonction.addSpecialite(specialite);
        fonctionRepository.saveAndFlush(fonction);
        Long specialiteId = specialite.getId();

        // Get all the fonctionList where specialite equals to specialiteId
        defaultFonctionShouldBeFound("specialiteId.equals=" + specialiteId);

        // Get all the fonctionList where specialite equals to specialiteId + 1
        defaultFonctionShouldNotBeFound("specialiteId.equals=" + (specialiteId + 1));
    }


    @Test
    @Transactional
    public void getAllFonctionsByEmployeIsEqualToSomething() throws Exception {
        // Initialize the database
        fonctionRepository.saveAndFlush(fonction);
        Employe employe = EmployeResourceIT.createEntity(em);
        em.persist(employe);
        em.flush();
        fonction.setEmploye(employe);
        fonctionRepository.saveAndFlush(fonction);
        Long employeId = employe.getId();

        // Get all the fonctionList where employe equals to employeId
        defaultFonctionShouldBeFound("employeId.equals=" + employeId);

        // Get all the fonctionList where employe equals to employeId + 1
        defaultFonctionShouldNotBeFound("employeId.equals=" + (employeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFonctionShouldBeFound(String filter) throws Exception {
        restFonctionMockMvc.perform(get("/api/fonctions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fonction.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleFonction").value(hasItem(DEFAULT_LIBELLE_FONCTION)));

        // Check, that the count call also returns 1
        restFonctionMockMvc.perform(get("/api/fonctions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFonctionShouldNotBeFound(String filter) throws Exception {
        restFonctionMockMvc.perform(get("/api/fonctions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFonctionMockMvc.perform(get("/api/fonctions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFonction() throws Exception {
        // Get the fonction
        restFonctionMockMvc.perform(get("/api/fonctions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFonction() throws Exception {
        // Initialize the database
        fonctionService.save(fonction);

        int databaseSizeBeforeUpdate = fonctionRepository.findAll().size();

        // Update the fonction
        Fonction updatedFonction = fonctionRepository.findById(fonction.getId()).get();
        // Disconnect from session so that the updates on updatedFonction are not directly saved in db
        em.detach(updatedFonction);
        updatedFonction
            .libelleFonction(UPDATED_LIBELLE_FONCTION);

        restFonctionMockMvc.perform(put("/api/fonctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFonction)))
            .andExpect(status().isOk());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeUpdate);
        Fonction testFonction = fonctionList.get(fonctionList.size() - 1);
        assertThat(testFonction.getLibelleFonction()).isEqualTo(UPDATED_LIBELLE_FONCTION);
    }

    @Test
    @Transactional
    public void updateNonExistingFonction() throws Exception {
        int databaseSizeBeforeUpdate = fonctionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFonctionMockMvc.perform(put("/api/fonctions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fonction)))
            .andExpect(status().isBadRequest());

        // Validate the Fonction in the database
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFonction() throws Exception {
        // Initialize the database
        fonctionService.save(fonction);

        int databaseSizeBeforeDelete = fonctionRepository.findAll().size();

        // Delete the fonction
        restFonctionMockMvc.perform(delete("/api/fonctions/{id}", fonction.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fonction> fonctionList = fonctionRepository.findAll();
        assertThat(fonctionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
