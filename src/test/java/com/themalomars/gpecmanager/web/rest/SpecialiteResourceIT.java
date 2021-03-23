package com.themalomars.gpecmanager.web.rest;

import com.themalomars.gpecmanager.GpecmanagerApp;
import com.themalomars.gpecmanager.domain.Specialite;
import com.themalomars.gpecmanager.domain.Fonction;
import com.themalomars.gpecmanager.repository.SpecialiteRepository;
import com.themalomars.gpecmanager.service.SpecialiteService;
import com.themalomars.gpecmanager.service.dto.SpecialiteCriteria;
import com.themalomars.gpecmanager.service.SpecialiteQueryService;

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
 * Integration tests for the {@link SpecialiteResource} REST controller.
 */
@SpringBootTest(classes = GpecmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SpecialiteResourceIT {

    private static final String DEFAULT_LIBELLE_SPECIALITE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_SPECIALITE = "BBBBBBBBBB";

    @Autowired
    private SpecialiteRepository specialiteRepository;

    @Autowired
    private SpecialiteService specialiteService;

    @Autowired
    private SpecialiteQueryService specialiteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSpecialiteMockMvc;

    private Specialite specialite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specialite createEntity(EntityManager em) {
        Specialite specialite = new Specialite()
            .libelleSpecialite(DEFAULT_LIBELLE_SPECIALITE);
        return specialite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Specialite createUpdatedEntity(EntityManager em) {
        Specialite specialite = new Specialite()
            .libelleSpecialite(UPDATED_LIBELLE_SPECIALITE);
        return specialite;
    }

    @BeforeEach
    public void initTest() {
        specialite = createEntity(em);
    }

    @Test
    @Transactional
    public void createSpecialite() throws Exception {
        int databaseSizeBeforeCreate = specialiteRepository.findAll().size();
        // Create the Specialite
        restSpecialiteMockMvc.perform(post("/api/specialites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(specialite)))
            .andExpect(status().isCreated());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeCreate + 1);
        Specialite testSpecialite = specialiteList.get(specialiteList.size() - 1);
        assertThat(testSpecialite.getLibelleSpecialite()).isEqualTo(DEFAULT_LIBELLE_SPECIALITE);
    }

    @Test
    @Transactional
    public void createSpecialiteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = specialiteRepository.findAll().size();

        // Create the Specialite with an existing ID
        specialite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpecialiteMockMvc.perform(post("/api/specialites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(specialite)))
            .andExpect(status().isBadRequest());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleSpecialiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = specialiteRepository.findAll().size();
        // set the field null
        specialite.setLibelleSpecialite(null);

        // Create the Specialite, which fails.


        restSpecialiteMockMvc.perform(post("/api/specialites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(specialite)))
            .andExpect(status().isBadRequest());

        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSpecialites() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get all the specialiteList
        restSpecialiteMockMvc.perform(get("/api/specialites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialite.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleSpecialite").value(hasItem(DEFAULT_LIBELLE_SPECIALITE)));
    }
    
    @Test
    @Transactional
    public void getSpecialite() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get the specialite
        restSpecialiteMockMvc.perform(get("/api/specialites/{id}", specialite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(specialite.getId().intValue()))
            .andExpect(jsonPath("$.libelleSpecialite").value(DEFAULT_LIBELLE_SPECIALITE));
    }


    @Test
    @Transactional
    public void getSpecialitesByIdFiltering() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        Long id = specialite.getId();

        defaultSpecialiteShouldBeFound("id.equals=" + id);
        defaultSpecialiteShouldNotBeFound("id.notEquals=" + id);

        defaultSpecialiteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSpecialiteShouldNotBeFound("id.greaterThan=" + id);

        defaultSpecialiteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSpecialiteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSpecialitesByLibelleSpecialiteIsEqualToSomething() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get all the specialiteList where libelleSpecialite equals to DEFAULT_LIBELLE_SPECIALITE
        defaultSpecialiteShouldBeFound("libelleSpecialite.equals=" + DEFAULT_LIBELLE_SPECIALITE);

        // Get all the specialiteList where libelleSpecialite equals to UPDATED_LIBELLE_SPECIALITE
        defaultSpecialiteShouldNotBeFound("libelleSpecialite.equals=" + UPDATED_LIBELLE_SPECIALITE);
    }

    @Test
    @Transactional
    public void getAllSpecialitesByLibelleSpecialiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get all the specialiteList where libelleSpecialite not equals to DEFAULT_LIBELLE_SPECIALITE
        defaultSpecialiteShouldNotBeFound("libelleSpecialite.notEquals=" + DEFAULT_LIBELLE_SPECIALITE);

        // Get all the specialiteList where libelleSpecialite not equals to UPDATED_LIBELLE_SPECIALITE
        defaultSpecialiteShouldBeFound("libelleSpecialite.notEquals=" + UPDATED_LIBELLE_SPECIALITE);
    }

    @Test
    @Transactional
    public void getAllSpecialitesByLibelleSpecialiteIsInShouldWork() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get all the specialiteList where libelleSpecialite in DEFAULT_LIBELLE_SPECIALITE or UPDATED_LIBELLE_SPECIALITE
        defaultSpecialiteShouldBeFound("libelleSpecialite.in=" + DEFAULT_LIBELLE_SPECIALITE + "," + UPDATED_LIBELLE_SPECIALITE);

        // Get all the specialiteList where libelleSpecialite equals to UPDATED_LIBELLE_SPECIALITE
        defaultSpecialiteShouldNotBeFound("libelleSpecialite.in=" + UPDATED_LIBELLE_SPECIALITE);
    }

    @Test
    @Transactional
    public void getAllSpecialitesByLibelleSpecialiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get all the specialiteList where libelleSpecialite is not null
        defaultSpecialiteShouldBeFound("libelleSpecialite.specified=true");

        // Get all the specialiteList where libelleSpecialite is null
        defaultSpecialiteShouldNotBeFound("libelleSpecialite.specified=false");
    }
                @Test
    @Transactional
    public void getAllSpecialitesByLibelleSpecialiteContainsSomething() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get all the specialiteList where libelleSpecialite contains DEFAULT_LIBELLE_SPECIALITE
        defaultSpecialiteShouldBeFound("libelleSpecialite.contains=" + DEFAULT_LIBELLE_SPECIALITE);

        // Get all the specialiteList where libelleSpecialite contains UPDATED_LIBELLE_SPECIALITE
        defaultSpecialiteShouldNotBeFound("libelleSpecialite.contains=" + UPDATED_LIBELLE_SPECIALITE);
    }

    @Test
    @Transactional
    public void getAllSpecialitesByLibelleSpecialiteNotContainsSomething() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);

        // Get all the specialiteList where libelleSpecialite does not contain DEFAULT_LIBELLE_SPECIALITE
        defaultSpecialiteShouldNotBeFound("libelleSpecialite.doesNotContain=" + DEFAULT_LIBELLE_SPECIALITE);

        // Get all the specialiteList where libelleSpecialite does not contain UPDATED_LIBELLE_SPECIALITE
        defaultSpecialiteShouldBeFound("libelleSpecialite.doesNotContain=" + UPDATED_LIBELLE_SPECIALITE);
    }


    @Test
    @Transactional
    public void getAllSpecialitesByFonctionIsEqualToSomething() throws Exception {
        // Initialize the database
        specialiteRepository.saveAndFlush(specialite);
        Fonction fonction = FonctionResourceIT.createEntity(em);
        em.persist(fonction);
        em.flush();
        specialite.setFonction(fonction);
        specialiteRepository.saveAndFlush(specialite);
        Long fonctionId = fonction.getId();

        // Get all the specialiteList where fonction equals to fonctionId
        defaultSpecialiteShouldBeFound("fonctionId.equals=" + fonctionId);

        // Get all the specialiteList where fonction equals to fonctionId + 1
        defaultSpecialiteShouldNotBeFound("fonctionId.equals=" + (fonctionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSpecialiteShouldBeFound(String filter) throws Exception {
        restSpecialiteMockMvc.perform(get("/api/specialites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(specialite.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleSpecialite").value(hasItem(DEFAULT_LIBELLE_SPECIALITE)));

        // Check, that the count call also returns 1
        restSpecialiteMockMvc.perform(get("/api/specialites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSpecialiteShouldNotBeFound(String filter) throws Exception {
        restSpecialiteMockMvc.perform(get("/api/specialites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSpecialiteMockMvc.perform(get("/api/specialites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSpecialite() throws Exception {
        // Get the specialite
        restSpecialiteMockMvc.perform(get("/api/specialites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSpecialite() throws Exception {
        // Initialize the database
        specialiteService.save(specialite);

        int databaseSizeBeforeUpdate = specialiteRepository.findAll().size();

        // Update the specialite
        Specialite updatedSpecialite = specialiteRepository.findById(specialite.getId()).get();
        // Disconnect from session so that the updates on updatedSpecialite are not directly saved in db
        em.detach(updatedSpecialite);
        updatedSpecialite
            .libelleSpecialite(UPDATED_LIBELLE_SPECIALITE);

        restSpecialiteMockMvc.perform(put("/api/specialites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpecialite)))
            .andExpect(status().isOk());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeUpdate);
        Specialite testSpecialite = specialiteList.get(specialiteList.size() - 1);
        assertThat(testSpecialite.getLibelleSpecialite()).isEqualTo(UPDATED_LIBELLE_SPECIALITE);
    }

    @Test
    @Transactional
    public void updateNonExistingSpecialite() throws Exception {
        int databaseSizeBeforeUpdate = specialiteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpecialiteMockMvc.perform(put("/api/specialites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(specialite)))
            .andExpect(status().isBadRequest());

        // Validate the Specialite in the database
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSpecialite() throws Exception {
        // Initialize the database
        specialiteService.save(specialite);

        int databaseSizeBeforeDelete = specialiteRepository.findAll().size();

        // Delete the specialite
        restSpecialiteMockMvc.perform(delete("/api/specialites/{id}", specialite.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Specialite> specialiteList = specialiteRepository.findAll();
        assertThat(specialiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
