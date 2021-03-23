package com.themalomars.gpecmanager.web.rest;

import com.themalomars.gpecmanager.GpecmanagerApp;
import com.themalomars.gpecmanager.domain.Diplome;
import com.themalomars.gpecmanager.domain.Employe;
import com.themalomars.gpecmanager.repository.DiplomeRepository;
import com.themalomars.gpecmanager.service.DiplomeService;
import com.themalomars.gpecmanager.service.dto.DiplomeCriteria;
import com.themalomars.gpecmanager.service.DiplomeQueryService;

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
 * Integration tests for the {@link DiplomeResource} REST controller.
 */
@SpringBootTest(classes = GpecmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class DiplomeResourceIT {

    private static final String DEFAULT_LIBELLE_DIPLOME = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_DIPLOME = "BBBBBBBBBB";

    private static final Instant DEFAULT_ANNEE_DIPLOME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ANNEE_DIPLOME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DiplomeRepository diplomeRepository;

    @Autowired
    private DiplomeService diplomeService;

    @Autowired
    private DiplomeQueryService diplomeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiplomeMockMvc;

    private Diplome diplome;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diplome createEntity(EntityManager em) {
        Diplome diplome = new Diplome()
            .libelleDiplome(DEFAULT_LIBELLE_DIPLOME)
            .anneeDiplome(DEFAULT_ANNEE_DIPLOME);
        return diplome;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diplome createUpdatedEntity(EntityManager em) {
        Diplome diplome = new Diplome()
            .libelleDiplome(UPDATED_LIBELLE_DIPLOME)
            .anneeDiplome(UPDATED_ANNEE_DIPLOME);
        return diplome;
    }

    @BeforeEach
    public void initTest() {
        diplome = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiplome() throws Exception {
        int databaseSizeBeforeCreate = diplomeRepository.findAll().size();
        // Create the Diplome
        restDiplomeMockMvc.perform(post("/api/diplomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diplome)))
            .andExpect(status().isCreated());

        // Validate the Diplome in the database
        List<Diplome> diplomeList = diplomeRepository.findAll();
        assertThat(diplomeList).hasSize(databaseSizeBeforeCreate + 1);
        Diplome testDiplome = diplomeList.get(diplomeList.size() - 1);
        assertThat(testDiplome.getLibelleDiplome()).isEqualTo(DEFAULT_LIBELLE_DIPLOME);
        assertThat(testDiplome.getAnneeDiplome()).isEqualTo(DEFAULT_ANNEE_DIPLOME);
    }

    @Test
    @Transactional
    public void createDiplomeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = diplomeRepository.findAll().size();

        // Create the Diplome with an existing ID
        diplome.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiplomeMockMvc.perform(post("/api/diplomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diplome)))
            .andExpect(status().isBadRequest());

        // Validate the Diplome in the database
        List<Diplome> diplomeList = diplomeRepository.findAll();
        assertThat(diplomeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleDiplomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = diplomeRepository.findAll().size();
        // set the field null
        diplome.setLibelleDiplome(null);

        // Create the Diplome, which fails.


        restDiplomeMockMvc.perform(post("/api/diplomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diplome)))
            .andExpect(status().isBadRequest());

        List<Diplome> diplomeList = diplomeRepository.findAll();
        assertThat(diplomeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiplomes() throws Exception {
        // Initialize the database
        diplomeRepository.saveAndFlush(diplome);

        // Get all the diplomeList
        restDiplomeMockMvc.perform(get("/api/diplomes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diplome.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleDiplome").value(hasItem(DEFAULT_LIBELLE_DIPLOME)))
            .andExpect(jsonPath("$.[*].anneeDiplome").value(hasItem(DEFAULT_ANNEE_DIPLOME.toString())));
    }
    
    @Test
    @Transactional
    public void getDiplome() throws Exception {
        // Initialize the database
        diplomeRepository.saveAndFlush(diplome);

        // Get the diplome
        restDiplomeMockMvc.perform(get("/api/diplomes/{id}", diplome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(diplome.getId().intValue()))
            .andExpect(jsonPath("$.libelleDiplome").value(DEFAULT_LIBELLE_DIPLOME))
            .andExpect(jsonPath("$.anneeDiplome").value(DEFAULT_ANNEE_DIPLOME.toString()));
    }


    @Test
    @Transactional
    public void getDiplomesByIdFiltering() throws Exception {
        // Initialize the database
        diplomeRepository.saveAndFlush(diplome);

        Long id = diplome.getId();

        defaultDiplomeShouldBeFound("id.equals=" + id);
        defaultDiplomeShouldNotBeFound("id.notEquals=" + id);

        defaultDiplomeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDiplomeShouldNotBeFound("id.greaterThan=" + id);

        defaultDiplomeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDiplomeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDiplomesByLibelleDiplomeIsEqualToSomething() throws Exception {
        // Initialize the database
        diplomeRepository.saveAndFlush(diplome);

        // Get all the diplomeList where libelleDiplome equals to DEFAULT_LIBELLE_DIPLOME
        defaultDiplomeShouldBeFound("libelleDiplome.equals=" + DEFAULT_LIBELLE_DIPLOME);

        // Get all the diplomeList where libelleDiplome equals to UPDATED_LIBELLE_DIPLOME
        defaultDiplomeShouldNotBeFound("libelleDiplome.equals=" + UPDATED_LIBELLE_DIPLOME);
    }

    @Test
    @Transactional
    public void getAllDiplomesByLibelleDiplomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        diplomeRepository.saveAndFlush(diplome);

        // Get all the diplomeList where libelleDiplome not equals to DEFAULT_LIBELLE_DIPLOME
        defaultDiplomeShouldNotBeFound("libelleDiplome.notEquals=" + DEFAULT_LIBELLE_DIPLOME);

        // Get all the diplomeList where libelleDiplome not equals to UPDATED_LIBELLE_DIPLOME
        defaultDiplomeShouldBeFound("libelleDiplome.notEquals=" + UPDATED_LIBELLE_DIPLOME);
    }

    @Test
    @Transactional
    public void getAllDiplomesByLibelleDiplomeIsInShouldWork() throws Exception {
        // Initialize the database
        diplomeRepository.saveAndFlush(diplome);

        // Get all the diplomeList where libelleDiplome in DEFAULT_LIBELLE_DIPLOME or UPDATED_LIBELLE_DIPLOME
        defaultDiplomeShouldBeFound("libelleDiplome.in=" + DEFAULT_LIBELLE_DIPLOME + "," + UPDATED_LIBELLE_DIPLOME);

        // Get all the diplomeList where libelleDiplome equals to UPDATED_LIBELLE_DIPLOME
        defaultDiplomeShouldNotBeFound("libelleDiplome.in=" + UPDATED_LIBELLE_DIPLOME);
    }

    @Test
    @Transactional
    public void getAllDiplomesByLibelleDiplomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        diplomeRepository.saveAndFlush(diplome);

        // Get all the diplomeList where libelleDiplome is not null
        defaultDiplomeShouldBeFound("libelleDiplome.specified=true");

        // Get all the diplomeList where libelleDiplome is null
        defaultDiplomeShouldNotBeFound("libelleDiplome.specified=false");
    }
                @Test
    @Transactional
    public void getAllDiplomesByLibelleDiplomeContainsSomething() throws Exception {
        // Initialize the database
        diplomeRepository.saveAndFlush(diplome);

        // Get all the diplomeList where libelleDiplome contains DEFAULT_LIBELLE_DIPLOME
        defaultDiplomeShouldBeFound("libelleDiplome.contains=" + DEFAULT_LIBELLE_DIPLOME);

        // Get all the diplomeList where libelleDiplome contains UPDATED_LIBELLE_DIPLOME
        defaultDiplomeShouldNotBeFound("libelleDiplome.contains=" + UPDATED_LIBELLE_DIPLOME);
    }

    @Test
    @Transactional
    public void getAllDiplomesByLibelleDiplomeNotContainsSomething() throws Exception {
        // Initialize the database
        diplomeRepository.saveAndFlush(diplome);

        // Get all the diplomeList where libelleDiplome does not contain DEFAULT_LIBELLE_DIPLOME
        defaultDiplomeShouldNotBeFound("libelleDiplome.doesNotContain=" + DEFAULT_LIBELLE_DIPLOME);

        // Get all the diplomeList where libelleDiplome does not contain UPDATED_LIBELLE_DIPLOME
        defaultDiplomeShouldBeFound("libelleDiplome.doesNotContain=" + UPDATED_LIBELLE_DIPLOME);
    }


    @Test
    @Transactional
    public void getAllDiplomesByAnneeDiplomeIsEqualToSomething() throws Exception {
        // Initialize the database
        diplomeRepository.saveAndFlush(diplome);

        // Get all the diplomeList where anneeDiplome equals to DEFAULT_ANNEE_DIPLOME
        defaultDiplomeShouldBeFound("anneeDiplome.equals=" + DEFAULT_ANNEE_DIPLOME);

        // Get all the diplomeList where anneeDiplome equals to UPDATED_ANNEE_DIPLOME
        defaultDiplomeShouldNotBeFound("anneeDiplome.equals=" + UPDATED_ANNEE_DIPLOME);
    }

    @Test
    @Transactional
    public void getAllDiplomesByAnneeDiplomeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        diplomeRepository.saveAndFlush(diplome);

        // Get all the diplomeList where anneeDiplome not equals to DEFAULT_ANNEE_DIPLOME
        defaultDiplomeShouldNotBeFound("anneeDiplome.notEquals=" + DEFAULT_ANNEE_DIPLOME);

        // Get all the diplomeList where anneeDiplome not equals to UPDATED_ANNEE_DIPLOME
        defaultDiplomeShouldBeFound("anneeDiplome.notEquals=" + UPDATED_ANNEE_DIPLOME);
    }

    @Test
    @Transactional
    public void getAllDiplomesByAnneeDiplomeIsInShouldWork() throws Exception {
        // Initialize the database
        diplomeRepository.saveAndFlush(diplome);

        // Get all the diplomeList where anneeDiplome in DEFAULT_ANNEE_DIPLOME or UPDATED_ANNEE_DIPLOME
        defaultDiplomeShouldBeFound("anneeDiplome.in=" + DEFAULT_ANNEE_DIPLOME + "," + UPDATED_ANNEE_DIPLOME);

        // Get all the diplomeList where anneeDiplome equals to UPDATED_ANNEE_DIPLOME
        defaultDiplomeShouldNotBeFound("anneeDiplome.in=" + UPDATED_ANNEE_DIPLOME);
    }

    @Test
    @Transactional
    public void getAllDiplomesByAnneeDiplomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        diplomeRepository.saveAndFlush(diplome);

        // Get all the diplomeList where anneeDiplome is not null
        defaultDiplomeShouldBeFound("anneeDiplome.specified=true");

        // Get all the diplomeList where anneeDiplome is null
        defaultDiplomeShouldNotBeFound("anneeDiplome.specified=false");
    }

    @Test
    @Transactional
    public void getAllDiplomesByEmployeIsEqualToSomething() throws Exception {
        // Initialize the database
        diplomeRepository.saveAndFlush(diplome);
        Employe employe = EmployeResourceIT.createEntity(em);
        em.persist(employe);
        em.flush();
        diplome.setEmploye(employe);
        diplomeRepository.saveAndFlush(diplome);
        Long employeId = employe.getId();

        // Get all the diplomeList where employe equals to employeId
        defaultDiplomeShouldBeFound("employeId.equals=" + employeId);

        // Get all the diplomeList where employe equals to employeId + 1
        defaultDiplomeShouldNotBeFound("employeId.equals=" + (employeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDiplomeShouldBeFound(String filter) throws Exception {
        restDiplomeMockMvc.perform(get("/api/diplomes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diplome.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleDiplome").value(hasItem(DEFAULT_LIBELLE_DIPLOME)))
            .andExpect(jsonPath("$.[*].anneeDiplome").value(hasItem(DEFAULT_ANNEE_DIPLOME.toString())));

        // Check, that the count call also returns 1
        restDiplomeMockMvc.perform(get("/api/diplomes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDiplomeShouldNotBeFound(String filter) throws Exception {
        restDiplomeMockMvc.perform(get("/api/diplomes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDiplomeMockMvc.perform(get("/api/diplomes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDiplome() throws Exception {
        // Get the diplome
        restDiplomeMockMvc.perform(get("/api/diplomes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiplome() throws Exception {
        // Initialize the database
        diplomeService.save(diplome);

        int databaseSizeBeforeUpdate = diplomeRepository.findAll().size();

        // Update the diplome
        Diplome updatedDiplome = diplomeRepository.findById(diplome.getId()).get();
        // Disconnect from session so that the updates on updatedDiplome are not directly saved in db
        em.detach(updatedDiplome);
        updatedDiplome
            .libelleDiplome(UPDATED_LIBELLE_DIPLOME)
            .anneeDiplome(UPDATED_ANNEE_DIPLOME);

        restDiplomeMockMvc.perform(put("/api/diplomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiplome)))
            .andExpect(status().isOk());

        // Validate the Diplome in the database
        List<Diplome> diplomeList = diplomeRepository.findAll();
        assertThat(diplomeList).hasSize(databaseSizeBeforeUpdate);
        Diplome testDiplome = diplomeList.get(diplomeList.size() - 1);
        assertThat(testDiplome.getLibelleDiplome()).isEqualTo(UPDATED_LIBELLE_DIPLOME);
        assertThat(testDiplome.getAnneeDiplome()).isEqualTo(UPDATED_ANNEE_DIPLOME);
    }

    @Test
    @Transactional
    public void updateNonExistingDiplome() throws Exception {
        int databaseSizeBeforeUpdate = diplomeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiplomeMockMvc.perform(put("/api/diplomes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(diplome)))
            .andExpect(status().isBadRequest());

        // Validate the Diplome in the database
        List<Diplome> diplomeList = diplomeRepository.findAll();
        assertThat(diplomeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiplome() throws Exception {
        // Initialize the database
        diplomeService.save(diplome);

        int databaseSizeBeforeDelete = diplomeRepository.findAll().size();

        // Delete the diplome
        restDiplomeMockMvc.perform(delete("/api/diplomes/{id}", diplome.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Diplome> diplomeList = diplomeRepository.findAll();
        assertThat(diplomeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
