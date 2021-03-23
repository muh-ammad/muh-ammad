package com.themalomars.gpecmanager.web.rest;

import com.themalomars.gpecmanager.GpecmanagerApp;
import com.themalomars.gpecmanager.domain.MembreFamille;
import com.themalomars.gpecmanager.domain.Employe;
import com.themalomars.gpecmanager.repository.MembreFamilleRepository;
import com.themalomars.gpecmanager.service.MembreFamilleService;
import com.themalomars.gpecmanager.service.dto.MembreFamilleCriteria;
import com.themalomars.gpecmanager.service.MembreFamilleQueryService;

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
 * Integration tests for the {@link MembreFamilleResource} REST controller.
 */
@SpringBootTest(classes = GpecmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MembreFamilleResourceIT {

    private static final String DEFAULT_PRENOMS = "AAAAAAAAAA";
    private static final String UPDATED_PRENOMS = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_NAISSANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_NAISSANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LIEU_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAISSANCE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_EPOUX = false;
    private static final Boolean UPDATED_EPOUX = true;

    private static final Boolean DEFAULT_EPOUSE = false;
    private static final Boolean UPDATED_EPOUSE = true;

    private static final Boolean DEFAULT_ENFANT = false;
    private static final Boolean UPDATED_ENFANT = true;

    @Autowired
    private MembreFamilleRepository membreFamilleRepository;

    @Autowired
    private MembreFamilleService membreFamilleService;

    @Autowired
    private MembreFamilleQueryService membreFamilleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMembreFamilleMockMvc;

    private MembreFamille membreFamille;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MembreFamille createEntity(EntityManager em) {
        MembreFamille membreFamille = new MembreFamille()
            .prenoms(DEFAULT_PRENOMS)
            .nom(DEFAULT_NOM)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .lieuNaissance(DEFAULT_LIEU_NAISSANCE)
            .epoux(DEFAULT_EPOUX)
            .epouse(DEFAULT_EPOUSE)
            .enfant(DEFAULT_ENFANT);
        return membreFamille;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MembreFamille createUpdatedEntity(EntityManager em) {
        MembreFamille membreFamille = new MembreFamille()
            .prenoms(UPDATED_PRENOMS)
            .nom(UPDATED_NOM)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .epoux(UPDATED_EPOUX)
            .epouse(UPDATED_EPOUSE)
            .enfant(UPDATED_ENFANT);
        return membreFamille;
    }

    @BeforeEach
    public void initTest() {
        membreFamille = createEntity(em);
    }

    @Test
    @Transactional
    public void createMembreFamille() throws Exception {
        int databaseSizeBeforeCreate = membreFamilleRepository.findAll().size();
        // Create the MembreFamille
        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isCreated());

        // Validate the MembreFamille in the database
        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeCreate + 1);
        MembreFamille testMembreFamille = membreFamilleList.get(membreFamilleList.size() - 1);
        assertThat(testMembreFamille.getPrenoms()).isEqualTo(DEFAULT_PRENOMS);
        assertThat(testMembreFamille.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testMembreFamille.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testMembreFamille.getLieuNaissance()).isEqualTo(DEFAULT_LIEU_NAISSANCE);
        assertThat(testMembreFamille.isEpoux()).isEqualTo(DEFAULT_EPOUX);
        assertThat(testMembreFamille.isEpouse()).isEqualTo(DEFAULT_EPOUSE);
        assertThat(testMembreFamille.isEnfant()).isEqualTo(DEFAULT_ENFANT);
    }

    @Test
    @Transactional
    public void createMembreFamilleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = membreFamilleRepository.findAll().size();

        // Create the MembreFamille with an existing ID
        membreFamille.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isBadRequest());

        // Validate the MembreFamille in the database
        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPrenomsIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreFamilleRepository.findAll().size();
        // set the field null
        membreFamille.setPrenoms(null);

        // Create the MembreFamille, which fails.


        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isBadRequest());

        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreFamilleRepository.findAll().size();
        // set the field null
        membreFamille.setNom(null);

        // Create the MembreFamille, which fails.


        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isBadRequest());

        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreFamilleRepository.findAll().size();
        // set the field null
        membreFamille.setDateNaissance(null);

        // Create the MembreFamille, which fails.


        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isBadRequest());

        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLieuNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = membreFamilleRepository.findAll().size();
        // set the field null
        membreFamille.setLieuNaissance(null);

        // Create the MembreFamille, which fails.


        restMembreFamilleMockMvc.perform(post("/api/membre-familles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isBadRequest());

        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMembreFamilles() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList
        restMembreFamilleMockMvc.perform(get("/api/membre-familles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membreFamille.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenoms").value(hasItem(DEFAULT_PRENOMS)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaissance").value(hasItem(DEFAULT_LIEU_NAISSANCE)))
            .andExpect(jsonPath("$.[*].epoux").value(hasItem(DEFAULT_EPOUX.booleanValue())))
            .andExpect(jsonPath("$.[*].epouse").value(hasItem(DEFAULT_EPOUSE.booleanValue())))
            .andExpect(jsonPath("$.[*].enfant").value(hasItem(DEFAULT_ENFANT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMembreFamille() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get the membreFamille
        restMembreFamilleMockMvc.perform(get("/api/membre-familles/{id}", membreFamille.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(membreFamille.getId().intValue()))
            .andExpect(jsonPath("$.prenoms").value(DEFAULT_PRENOMS))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.lieuNaissance").value(DEFAULT_LIEU_NAISSANCE))
            .andExpect(jsonPath("$.epoux").value(DEFAULT_EPOUX.booleanValue()))
            .andExpect(jsonPath("$.epouse").value(DEFAULT_EPOUSE.booleanValue()))
            .andExpect(jsonPath("$.enfant").value(DEFAULT_ENFANT.booleanValue()));
    }


    @Test
    @Transactional
    public void getMembreFamillesByIdFiltering() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        Long id = membreFamille.getId();

        defaultMembreFamilleShouldBeFound("id.equals=" + id);
        defaultMembreFamilleShouldNotBeFound("id.notEquals=" + id);

        defaultMembreFamilleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMembreFamilleShouldNotBeFound("id.greaterThan=" + id);

        defaultMembreFamilleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMembreFamilleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMembreFamillesByPrenomsIsEqualToSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where prenoms equals to DEFAULT_PRENOMS
        defaultMembreFamilleShouldBeFound("prenoms.equals=" + DEFAULT_PRENOMS);

        // Get all the membreFamilleList where prenoms equals to UPDATED_PRENOMS
        defaultMembreFamilleShouldNotBeFound("prenoms.equals=" + UPDATED_PRENOMS);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByPrenomsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where prenoms not equals to DEFAULT_PRENOMS
        defaultMembreFamilleShouldNotBeFound("prenoms.notEquals=" + DEFAULT_PRENOMS);

        // Get all the membreFamilleList where prenoms not equals to UPDATED_PRENOMS
        defaultMembreFamilleShouldBeFound("prenoms.notEquals=" + UPDATED_PRENOMS);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByPrenomsIsInShouldWork() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where prenoms in DEFAULT_PRENOMS or UPDATED_PRENOMS
        defaultMembreFamilleShouldBeFound("prenoms.in=" + DEFAULT_PRENOMS + "," + UPDATED_PRENOMS);

        // Get all the membreFamilleList where prenoms equals to UPDATED_PRENOMS
        defaultMembreFamilleShouldNotBeFound("prenoms.in=" + UPDATED_PRENOMS);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByPrenomsIsNullOrNotNull() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where prenoms is not null
        defaultMembreFamilleShouldBeFound("prenoms.specified=true");

        // Get all the membreFamilleList where prenoms is null
        defaultMembreFamilleShouldNotBeFound("prenoms.specified=false");
    }
                @Test
    @Transactional
    public void getAllMembreFamillesByPrenomsContainsSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where prenoms contains DEFAULT_PRENOMS
        defaultMembreFamilleShouldBeFound("prenoms.contains=" + DEFAULT_PRENOMS);

        // Get all the membreFamilleList where prenoms contains UPDATED_PRENOMS
        defaultMembreFamilleShouldNotBeFound("prenoms.contains=" + UPDATED_PRENOMS);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByPrenomsNotContainsSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where prenoms does not contain DEFAULT_PRENOMS
        defaultMembreFamilleShouldNotBeFound("prenoms.doesNotContain=" + DEFAULT_PRENOMS);

        // Get all the membreFamilleList where prenoms does not contain UPDATED_PRENOMS
        defaultMembreFamilleShouldBeFound("prenoms.doesNotContain=" + UPDATED_PRENOMS);
    }


    @Test
    @Transactional
    public void getAllMembreFamillesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where nom equals to DEFAULT_NOM
        defaultMembreFamilleShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the membreFamilleList where nom equals to UPDATED_NOM
        defaultMembreFamilleShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where nom not equals to DEFAULT_NOM
        defaultMembreFamilleShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the membreFamilleList where nom not equals to UPDATED_NOM
        defaultMembreFamilleShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultMembreFamilleShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the membreFamilleList where nom equals to UPDATED_NOM
        defaultMembreFamilleShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where nom is not null
        defaultMembreFamilleShouldBeFound("nom.specified=true");

        // Get all the membreFamilleList where nom is null
        defaultMembreFamilleShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllMembreFamillesByNomContainsSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where nom contains DEFAULT_NOM
        defaultMembreFamilleShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the membreFamilleList where nom contains UPDATED_NOM
        defaultMembreFamilleShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where nom does not contain DEFAULT_NOM
        defaultMembreFamilleShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the membreFamilleList where nom does not contain UPDATED_NOM
        defaultMembreFamilleShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllMembreFamillesByDateNaissanceIsEqualToSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where dateNaissance equals to DEFAULT_DATE_NAISSANCE
        defaultMembreFamilleShouldBeFound("dateNaissance.equals=" + DEFAULT_DATE_NAISSANCE);

        // Get all the membreFamilleList where dateNaissance equals to UPDATED_DATE_NAISSANCE
        defaultMembreFamilleShouldNotBeFound("dateNaissance.equals=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByDateNaissanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where dateNaissance not equals to DEFAULT_DATE_NAISSANCE
        defaultMembreFamilleShouldNotBeFound("dateNaissance.notEquals=" + DEFAULT_DATE_NAISSANCE);

        // Get all the membreFamilleList where dateNaissance not equals to UPDATED_DATE_NAISSANCE
        defaultMembreFamilleShouldBeFound("dateNaissance.notEquals=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByDateNaissanceIsInShouldWork() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where dateNaissance in DEFAULT_DATE_NAISSANCE or UPDATED_DATE_NAISSANCE
        defaultMembreFamilleShouldBeFound("dateNaissance.in=" + DEFAULT_DATE_NAISSANCE + "," + UPDATED_DATE_NAISSANCE);

        // Get all the membreFamilleList where dateNaissance equals to UPDATED_DATE_NAISSANCE
        defaultMembreFamilleShouldNotBeFound("dateNaissance.in=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByDateNaissanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where dateNaissance is not null
        defaultMembreFamilleShouldBeFound("dateNaissance.specified=true");

        // Get all the membreFamilleList where dateNaissance is null
        defaultMembreFamilleShouldNotBeFound("dateNaissance.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByLieuNaissanceIsEqualToSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where lieuNaissance equals to DEFAULT_LIEU_NAISSANCE
        defaultMembreFamilleShouldBeFound("lieuNaissance.equals=" + DEFAULT_LIEU_NAISSANCE);

        // Get all the membreFamilleList where lieuNaissance equals to UPDATED_LIEU_NAISSANCE
        defaultMembreFamilleShouldNotBeFound("lieuNaissance.equals=" + UPDATED_LIEU_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByLieuNaissanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where lieuNaissance not equals to DEFAULT_LIEU_NAISSANCE
        defaultMembreFamilleShouldNotBeFound("lieuNaissance.notEquals=" + DEFAULT_LIEU_NAISSANCE);

        // Get all the membreFamilleList where lieuNaissance not equals to UPDATED_LIEU_NAISSANCE
        defaultMembreFamilleShouldBeFound("lieuNaissance.notEquals=" + UPDATED_LIEU_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByLieuNaissanceIsInShouldWork() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where lieuNaissance in DEFAULT_LIEU_NAISSANCE or UPDATED_LIEU_NAISSANCE
        defaultMembreFamilleShouldBeFound("lieuNaissance.in=" + DEFAULT_LIEU_NAISSANCE + "," + UPDATED_LIEU_NAISSANCE);

        // Get all the membreFamilleList where lieuNaissance equals to UPDATED_LIEU_NAISSANCE
        defaultMembreFamilleShouldNotBeFound("lieuNaissance.in=" + UPDATED_LIEU_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByLieuNaissanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where lieuNaissance is not null
        defaultMembreFamilleShouldBeFound("lieuNaissance.specified=true");

        // Get all the membreFamilleList where lieuNaissance is null
        defaultMembreFamilleShouldNotBeFound("lieuNaissance.specified=false");
    }
                @Test
    @Transactional
    public void getAllMembreFamillesByLieuNaissanceContainsSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where lieuNaissance contains DEFAULT_LIEU_NAISSANCE
        defaultMembreFamilleShouldBeFound("lieuNaissance.contains=" + DEFAULT_LIEU_NAISSANCE);

        // Get all the membreFamilleList where lieuNaissance contains UPDATED_LIEU_NAISSANCE
        defaultMembreFamilleShouldNotBeFound("lieuNaissance.contains=" + UPDATED_LIEU_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByLieuNaissanceNotContainsSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where lieuNaissance does not contain DEFAULT_LIEU_NAISSANCE
        defaultMembreFamilleShouldNotBeFound("lieuNaissance.doesNotContain=" + DEFAULT_LIEU_NAISSANCE);

        // Get all the membreFamilleList where lieuNaissance does not contain UPDATED_LIEU_NAISSANCE
        defaultMembreFamilleShouldBeFound("lieuNaissance.doesNotContain=" + UPDATED_LIEU_NAISSANCE);
    }


    @Test
    @Transactional
    public void getAllMembreFamillesByEpouxIsEqualToSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where epoux equals to DEFAULT_EPOUX
        defaultMembreFamilleShouldBeFound("epoux.equals=" + DEFAULT_EPOUX);

        // Get all the membreFamilleList where epoux equals to UPDATED_EPOUX
        defaultMembreFamilleShouldNotBeFound("epoux.equals=" + UPDATED_EPOUX);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByEpouxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where epoux not equals to DEFAULT_EPOUX
        defaultMembreFamilleShouldNotBeFound("epoux.notEquals=" + DEFAULT_EPOUX);

        // Get all the membreFamilleList where epoux not equals to UPDATED_EPOUX
        defaultMembreFamilleShouldBeFound("epoux.notEquals=" + UPDATED_EPOUX);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByEpouxIsInShouldWork() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where epoux in DEFAULT_EPOUX or UPDATED_EPOUX
        defaultMembreFamilleShouldBeFound("epoux.in=" + DEFAULT_EPOUX + "," + UPDATED_EPOUX);

        // Get all the membreFamilleList where epoux equals to UPDATED_EPOUX
        defaultMembreFamilleShouldNotBeFound("epoux.in=" + UPDATED_EPOUX);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByEpouxIsNullOrNotNull() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where epoux is not null
        defaultMembreFamilleShouldBeFound("epoux.specified=true");

        // Get all the membreFamilleList where epoux is null
        defaultMembreFamilleShouldNotBeFound("epoux.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByEpouseIsEqualToSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where epouse equals to DEFAULT_EPOUSE
        defaultMembreFamilleShouldBeFound("epouse.equals=" + DEFAULT_EPOUSE);

        // Get all the membreFamilleList where epouse equals to UPDATED_EPOUSE
        defaultMembreFamilleShouldNotBeFound("epouse.equals=" + UPDATED_EPOUSE);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByEpouseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where epouse not equals to DEFAULT_EPOUSE
        defaultMembreFamilleShouldNotBeFound("epouse.notEquals=" + DEFAULT_EPOUSE);

        // Get all the membreFamilleList where epouse not equals to UPDATED_EPOUSE
        defaultMembreFamilleShouldBeFound("epouse.notEquals=" + UPDATED_EPOUSE);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByEpouseIsInShouldWork() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where epouse in DEFAULT_EPOUSE or UPDATED_EPOUSE
        defaultMembreFamilleShouldBeFound("epouse.in=" + DEFAULT_EPOUSE + "," + UPDATED_EPOUSE);

        // Get all the membreFamilleList where epouse equals to UPDATED_EPOUSE
        defaultMembreFamilleShouldNotBeFound("epouse.in=" + UPDATED_EPOUSE);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByEpouseIsNullOrNotNull() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where epouse is not null
        defaultMembreFamilleShouldBeFound("epouse.specified=true");

        // Get all the membreFamilleList where epouse is null
        defaultMembreFamilleShouldNotBeFound("epouse.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByEnfantIsEqualToSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where enfant equals to DEFAULT_ENFANT
        defaultMembreFamilleShouldBeFound("enfant.equals=" + DEFAULT_ENFANT);

        // Get all the membreFamilleList where enfant equals to UPDATED_ENFANT
        defaultMembreFamilleShouldNotBeFound("enfant.equals=" + UPDATED_ENFANT);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByEnfantIsNotEqualToSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where enfant not equals to DEFAULT_ENFANT
        defaultMembreFamilleShouldNotBeFound("enfant.notEquals=" + DEFAULT_ENFANT);

        // Get all the membreFamilleList where enfant not equals to UPDATED_ENFANT
        defaultMembreFamilleShouldBeFound("enfant.notEquals=" + UPDATED_ENFANT);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByEnfantIsInShouldWork() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where enfant in DEFAULT_ENFANT or UPDATED_ENFANT
        defaultMembreFamilleShouldBeFound("enfant.in=" + DEFAULT_ENFANT + "," + UPDATED_ENFANT);

        // Get all the membreFamilleList where enfant equals to UPDATED_ENFANT
        defaultMembreFamilleShouldNotBeFound("enfant.in=" + UPDATED_ENFANT);
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByEnfantIsNullOrNotNull() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);

        // Get all the membreFamilleList where enfant is not null
        defaultMembreFamilleShouldBeFound("enfant.specified=true");

        // Get all the membreFamilleList where enfant is null
        defaultMembreFamilleShouldNotBeFound("enfant.specified=false");
    }

    @Test
    @Transactional
    public void getAllMembreFamillesByEmployeIsEqualToSomething() throws Exception {
        // Initialize the database
        membreFamilleRepository.saveAndFlush(membreFamille);
        Employe employe = EmployeResourceIT.createEntity(em);
        em.persist(employe);
        em.flush();
        membreFamille.setEmploye(employe);
        membreFamilleRepository.saveAndFlush(membreFamille);
        Long employeId = employe.getId();

        // Get all the membreFamilleList where employe equals to employeId
        defaultMembreFamilleShouldBeFound("employeId.equals=" + employeId);

        // Get all the membreFamilleList where employe equals to employeId + 1
        defaultMembreFamilleShouldNotBeFound("employeId.equals=" + (employeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMembreFamilleShouldBeFound(String filter) throws Exception {
        restMembreFamilleMockMvc.perform(get("/api/membre-familles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(membreFamille.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenoms").value(hasItem(DEFAULT_PRENOMS)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaissance").value(hasItem(DEFAULT_LIEU_NAISSANCE)))
            .andExpect(jsonPath("$.[*].epoux").value(hasItem(DEFAULT_EPOUX.booleanValue())))
            .andExpect(jsonPath("$.[*].epouse").value(hasItem(DEFAULT_EPOUSE.booleanValue())))
            .andExpect(jsonPath("$.[*].enfant").value(hasItem(DEFAULT_ENFANT.booleanValue())));

        // Check, that the count call also returns 1
        restMembreFamilleMockMvc.perform(get("/api/membre-familles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMembreFamilleShouldNotBeFound(String filter) throws Exception {
        restMembreFamilleMockMvc.perform(get("/api/membre-familles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMembreFamilleMockMvc.perform(get("/api/membre-familles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingMembreFamille() throws Exception {
        // Get the membreFamille
        restMembreFamilleMockMvc.perform(get("/api/membre-familles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMembreFamille() throws Exception {
        // Initialize the database
        membreFamilleService.save(membreFamille);

        int databaseSizeBeforeUpdate = membreFamilleRepository.findAll().size();

        // Update the membreFamille
        MembreFamille updatedMembreFamille = membreFamilleRepository.findById(membreFamille.getId()).get();
        // Disconnect from session so that the updates on updatedMembreFamille are not directly saved in db
        em.detach(updatedMembreFamille);
        updatedMembreFamille
            .prenoms(UPDATED_PRENOMS)
            .nom(UPDATED_NOM)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .epoux(UPDATED_EPOUX)
            .epouse(UPDATED_EPOUSE)
            .enfant(UPDATED_ENFANT);

        restMembreFamilleMockMvc.perform(put("/api/membre-familles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMembreFamille)))
            .andExpect(status().isOk());

        // Validate the MembreFamille in the database
        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeUpdate);
        MembreFamille testMembreFamille = membreFamilleList.get(membreFamilleList.size() - 1);
        assertThat(testMembreFamille.getPrenoms()).isEqualTo(UPDATED_PRENOMS);
        assertThat(testMembreFamille.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testMembreFamille.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testMembreFamille.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testMembreFamille.isEpoux()).isEqualTo(UPDATED_EPOUX);
        assertThat(testMembreFamille.isEpouse()).isEqualTo(UPDATED_EPOUSE);
        assertThat(testMembreFamille.isEnfant()).isEqualTo(UPDATED_ENFANT);
    }

    @Test
    @Transactional
    public void updateNonExistingMembreFamille() throws Exception {
        int databaseSizeBeforeUpdate = membreFamilleRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMembreFamilleMockMvc.perform(put("/api/membre-familles")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(membreFamille)))
            .andExpect(status().isBadRequest());

        // Validate the MembreFamille in the database
        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMembreFamille() throws Exception {
        // Initialize the database
        membreFamilleService.save(membreFamille);

        int databaseSizeBeforeDelete = membreFamilleRepository.findAll().size();

        // Delete the membreFamille
        restMembreFamilleMockMvc.perform(delete("/api/membre-familles/{id}", membreFamille.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MembreFamille> membreFamilleList = membreFamilleRepository.findAll();
        assertThat(membreFamilleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
