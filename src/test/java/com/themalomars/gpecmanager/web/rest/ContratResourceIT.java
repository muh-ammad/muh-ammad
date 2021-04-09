package com.themalomars.gpecmanager.web.rest;

import com.themalomars.gpecmanager.GpecmanagerApp;
import com.themalomars.gpecmanager.domain.Contrat;
import com.themalomars.gpecmanager.domain.Employe;
import com.themalomars.gpecmanager.repository.ContratRepository;
import com.themalomars.gpecmanager.service.ContratService;
import com.themalomars.gpecmanager.service.dto.ContratCriteria;
import com.themalomars.gpecmanager.service.ContratQueryService;

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

import com.themalomars.gpecmanager.domain.enumeration.NiveauEtude;
import com.themalomars.gpecmanager.domain.enumeration.TypeContrat;
/**
 * Integration tests for the {@link ContratResource} REST controller.
 */
@SpringBootTest(classes = GpecmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContratResourceIT {

    private static final String DEFAULT_NUMERO_CONTRAT = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CONTRAT = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_CONTRAT = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_CONTRAT = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_DEBUT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DEBUT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final NiveauEtude DEFAULT_NIVEAU_ETUDE = NiveauEtude.AUCUN;
    private static final NiveauEtude UPDATED_NIVEAU_ETUDE = NiveauEtude.PRIMAIRE;

    private static final TypeContrat DEFAULT_TYPE_CONTRAT = TypeContrat.PRESTATAIRE;
    private static final TypeContrat UPDATED_TYPE_CONTRAT = TypeContrat.CDD;

    @Autowired
    private ContratRepository contratRepository;

    @Autowired
    private ContratService contratService;

    @Autowired
    private ContratQueryService contratQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContratMockMvc;

    private Contrat contrat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contrat createEntity(EntityManager em) {
        Contrat contrat = new Contrat()
            .numeroContrat(DEFAULT_NUMERO_CONTRAT)
            .libelleContrat(DEFAULT_LIBELLE_CONTRAT)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .niveauEtude(DEFAULT_NIVEAU_ETUDE)
            .typeContrat(DEFAULT_TYPE_CONTRAT);
        return contrat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contrat createUpdatedEntity(EntityManager em) {
        Contrat contrat = new Contrat()
            .numeroContrat(UPDATED_NUMERO_CONTRAT)
            .libelleContrat(UPDATED_LIBELLE_CONTRAT)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .niveauEtude(UPDATED_NIVEAU_ETUDE)
            .typeContrat(UPDATED_TYPE_CONTRAT);
        return contrat;
    }

    @BeforeEach
    public void initTest() {
        contrat = createEntity(em);
    }

    @Test
    @Transactional
    public void createContrat() throws Exception {
        int databaseSizeBeforeCreate = contratRepository.findAll().size();
        // Create the Contrat
        restContratMockMvc.perform(post("/api/contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrat)))
            .andExpect(status().isCreated());

        // Validate the Contrat in the database
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeCreate + 1);
        Contrat testContrat = contratList.get(contratList.size() - 1);
        assertThat(testContrat.getNumeroContrat()).isEqualTo(DEFAULT_NUMERO_CONTRAT);
        assertThat(testContrat.getLibelleContrat()).isEqualTo(DEFAULT_LIBELLE_CONTRAT);
        assertThat(testContrat.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testContrat.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testContrat.getNiveauEtude()).isEqualTo(DEFAULT_NIVEAU_ETUDE);
        assertThat(testContrat.getTypeContrat()).isEqualTo(DEFAULT_TYPE_CONTRAT);
    }

    @Test
    @Transactional
    public void createContratWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contratRepository.findAll().size();

        // Create the Contrat with an existing ID
        contrat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContratMockMvc.perform(post("/api/contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrat)))
            .andExpect(status().isBadRequest());

        // Validate the Contrat in the database
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroContratIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratRepository.findAll().size();
        // set the field null
        contrat.setNumeroContrat(null);

        // Create the Contrat, which fails.


        restContratMockMvc.perform(post("/api/contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrat)))
            .andExpect(status().isBadRequest());

        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleContratIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratRepository.findAll().size();
        // set the field null
        contrat.setLibelleContrat(null);

        // Create the Contrat, which fails.


        restContratMockMvc.perform(post("/api/contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrat)))
            .andExpect(status().isBadRequest());

        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateDebutIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratRepository.findAll().size();
        // set the field null
        contrat.setDateDebut(null);

        // Create the Contrat, which fails.


        restContratMockMvc.perform(post("/api/contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrat)))
            .andExpect(status().isBadRequest());

        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratRepository.findAll().size();
        // set the field null
        contrat.setDateFin(null);

        // Create the Contrat, which fails.


        restContratMockMvc.perform(post("/api/contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrat)))
            .andExpect(status().isBadRequest());

        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNiveauEtudeIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratRepository.findAll().size();
        // set the field null
        contrat.setNiveauEtude(null);

        // Create the Contrat, which fails.


        restContratMockMvc.perform(post("/api/contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrat)))
            .andExpect(status().isBadRequest());

        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeContratIsRequired() throws Exception {
        int databaseSizeBeforeTest = contratRepository.findAll().size();
        // set the field null
        contrat.setTypeContrat(null);

        // Create the Contrat, which fails.


        restContratMockMvc.perform(post("/api/contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrat)))
            .andExpect(status().isBadRequest());

        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContrats() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList
        restContratMockMvc.perform(get("/api/contrats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroContrat").value(hasItem(DEFAULT_NUMERO_CONTRAT)))
            .andExpect(jsonPath("$.[*].libelleContrat").value(hasItem(DEFAULT_LIBELLE_CONTRAT)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].niveauEtude").value(hasItem(DEFAULT_NIVEAU_ETUDE.toString())))
            .andExpect(jsonPath("$.[*].typeContrat").value(hasItem(DEFAULT_TYPE_CONTRAT.toString())));
    }
    
    @Test
    @Transactional
    public void getContrat() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get the contrat
        restContratMockMvc.perform(get("/api/contrats/{id}", contrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contrat.getId().intValue()))
            .andExpect(jsonPath("$.numeroContrat").value(DEFAULT_NUMERO_CONTRAT))
            .andExpect(jsonPath("$.libelleContrat").value(DEFAULT_LIBELLE_CONTRAT))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.niveauEtude").value(DEFAULT_NIVEAU_ETUDE.toString()))
            .andExpect(jsonPath("$.typeContrat").value(DEFAULT_TYPE_CONTRAT.toString()));
    }


    @Test
    @Transactional
    public void getContratsByIdFiltering() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        Long id = contrat.getId();

        defaultContratShouldBeFound("id.equals=" + id);
        defaultContratShouldNotBeFound("id.notEquals=" + id);

        defaultContratShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultContratShouldNotBeFound("id.greaterThan=" + id);

        defaultContratShouldBeFound("id.lessThanOrEqual=" + id);
        defaultContratShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllContratsByNumeroContratIsEqualToSomething() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where numeroContrat equals to DEFAULT_NUMERO_CONTRAT
        defaultContratShouldBeFound("numeroContrat.equals=" + DEFAULT_NUMERO_CONTRAT);

        // Get all the contratList where numeroContrat equals to UPDATED_NUMERO_CONTRAT
        defaultContratShouldNotBeFound("numeroContrat.equals=" + UPDATED_NUMERO_CONTRAT);
    }

    @Test
    @Transactional
    public void getAllContratsByNumeroContratIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where numeroContrat not equals to DEFAULT_NUMERO_CONTRAT
        defaultContratShouldNotBeFound("numeroContrat.notEquals=" + DEFAULT_NUMERO_CONTRAT);

        // Get all the contratList where numeroContrat not equals to UPDATED_NUMERO_CONTRAT
        defaultContratShouldBeFound("numeroContrat.notEquals=" + UPDATED_NUMERO_CONTRAT);
    }

    @Test
    @Transactional
    public void getAllContratsByNumeroContratIsInShouldWork() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where numeroContrat in DEFAULT_NUMERO_CONTRAT or UPDATED_NUMERO_CONTRAT
        defaultContratShouldBeFound("numeroContrat.in=" + DEFAULT_NUMERO_CONTRAT + "," + UPDATED_NUMERO_CONTRAT);

        // Get all the contratList where numeroContrat equals to UPDATED_NUMERO_CONTRAT
        defaultContratShouldNotBeFound("numeroContrat.in=" + UPDATED_NUMERO_CONTRAT);
    }

    @Test
    @Transactional
    public void getAllContratsByNumeroContratIsNullOrNotNull() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where numeroContrat is not null
        defaultContratShouldBeFound("numeroContrat.specified=true");

        // Get all the contratList where numeroContrat is null
        defaultContratShouldNotBeFound("numeroContrat.specified=false");
    }
                @Test
    @Transactional
    public void getAllContratsByNumeroContratContainsSomething() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where numeroContrat contains DEFAULT_NUMERO_CONTRAT
        defaultContratShouldBeFound("numeroContrat.contains=" + DEFAULT_NUMERO_CONTRAT);

        // Get all the contratList where numeroContrat contains UPDATED_NUMERO_CONTRAT
        defaultContratShouldNotBeFound("numeroContrat.contains=" + UPDATED_NUMERO_CONTRAT);
    }

    @Test
    @Transactional
    public void getAllContratsByNumeroContratNotContainsSomething() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where numeroContrat does not contain DEFAULT_NUMERO_CONTRAT
        defaultContratShouldNotBeFound("numeroContrat.doesNotContain=" + DEFAULT_NUMERO_CONTRAT);

        // Get all the contratList where numeroContrat does not contain UPDATED_NUMERO_CONTRAT
        defaultContratShouldBeFound("numeroContrat.doesNotContain=" + UPDATED_NUMERO_CONTRAT);
    }


    @Test
    @Transactional
    public void getAllContratsByLibelleContratIsEqualToSomething() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where libelleContrat equals to DEFAULT_LIBELLE_CONTRAT
        defaultContratShouldBeFound("libelleContrat.equals=" + DEFAULT_LIBELLE_CONTRAT);

        // Get all the contratList where libelleContrat equals to UPDATED_LIBELLE_CONTRAT
        defaultContratShouldNotBeFound("libelleContrat.equals=" + UPDATED_LIBELLE_CONTRAT);
    }

    @Test
    @Transactional
    public void getAllContratsByLibelleContratIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where libelleContrat not equals to DEFAULT_LIBELLE_CONTRAT
        defaultContratShouldNotBeFound("libelleContrat.notEquals=" + DEFAULT_LIBELLE_CONTRAT);

        // Get all the contratList where libelleContrat not equals to UPDATED_LIBELLE_CONTRAT
        defaultContratShouldBeFound("libelleContrat.notEquals=" + UPDATED_LIBELLE_CONTRAT);
    }

    @Test
    @Transactional
    public void getAllContratsByLibelleContratIsInShouldWork() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where libelleContrat in DEFAULT_LIBELLE_CONTRAT or UPDATED_LIBELLE_CONTRAT
        defaultContratShouldBeFound("libelleContrat.in=" + DEFAULT_LIBELLE_CONTRAT + "," + UPDATED_LIBELLE_CONTRAT);

        // Get all the contratList where libelleContrat equals to UPDATED_LIBELLE_CONTRAT
        defaultContratShouldNotBeFound("libelleContrat.in=" + UPDATED_LIBELLE_CONTRAT);
    }

    @Test
    @Transactional
    public void getAllContratsByLibelleContratIsNullOrNotNull() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where libelleContrat is not null
        defaultContratShouldBeFound("libelleContrat.specified=true");

        // Get all the contratList where libelleContrat is null
        defaultContratShouldNotBeFound("libelleContrat.specified=false");
    }
                @Test
    @Transactional
    public void getAllContratsByLibelleContratContainsSomething() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where libelleContrat contains DEFAULT_LIBELLE_CONTRAT
        defaultContratShouldBeFound("libelleContrat.contains=" + DEFAULT_LIBELLE_CONTRAT);

        // Get all the contratList where libelleContrat contains UPDATED_LIBELLE_CONTRAT
        defaultContratShouldNotBeFound("libelleContrat.contains=" + UPDATED_LIBELLE_CONTRAT);
    }

    @Test
    @Transactional
    public void getAllContratsByLibelleContratNotContainsSomething() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where libelleContrat does not contain DEFAULT_LIBELLE_CONTRAT
        defaultContratShouldNotBeFound("libelleContrat.doesNotContain=" + DEFAULT_LIBELLE_CONTRAT);

        // Get all the contratList where libelleContrat does not contain UPDATED_LIBELLE_CONTRAT
        defaultContratShouldBeFound("libelleContrat.doesNotContain=" + UPDATED_LIBELLE_CONTRAT);
    }


    @Test
    @Transactional
    public void getAllContratsByDateDebutIsEqualToSomething() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where dateDebut equals to DEFAULT_DATE_DEBUT
        defaultContratShouldBeFound("dateDebut.equals=" + DEFAULT_DATE_DEBUT);

        // Get all the contratList where dateDebut equals to UPDATED_DATE_DEBUT
        defaultContratShouldNotBeFound("dateDebut.equals=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllContratsByDateDebutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where dateDebut not equals to DEFAULT_DATE_DEBUT
        defaultContratShouldNotBeFound("dateDebut.notEquals=" + DEFAULT_DATE_DEBUT);

        // Get all the contratList where dateDebut not equals to UPDATED_DATE_DEBUT
        defaultContratShouldBeFound("dateDebut.notEquals=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllContratsByDateDebutIsInShouldWork() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where dateDebut in DEFAULT_DATE_DEBUT or UPDATED_DATE_DEBUT
        defaultContratShouldBeFound("dateDebut.in=" + DEFAULT_DATE_DEBUT + "," + UPDATED_DATE_DEBUT);

        // Get all the contratList where dateDebut equals to UPDATED_DATE_DEBUT
        defaultContratShouldNotBeFound("dateDebut.in=" + UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    public void getAllContratsByDateDebutIsNullOrNotNull() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where dateDebut is not null
        defaultContratShouldBeFound("dateDebut.specified=true");

        // Get all the contratList where dateDebut is null
        defaultContratShouldNotBeFound("dateDebut.specified=false");
    }

    @Test
    @Transactional
    public void getAllContratsByDateFinIsEqualToSomething() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where dateFin equals to DEFAULT_DATE_FIN
        defaultContratShouldBeFound("dateFin.equals=" + DEFAULT_DATE_FIN);

        // Get all the contratList where dateFin equals to UPDATED_DATE_FIN
        defaultContratShouldNotBeFound("dateFin.equals=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllContratsByDateFinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where dateFin not equals to DEFAULT_DATE_FIN
        defaultContratShouldNotBeFound("dateFin.notEquals=" + DEFAULT_DATE_FIN);

        // Get all the contratList where dateFin not equals to UPDATED_DATE_FIN
        defaultContratShouldBeFound("dateFin.notEquals=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllContratsByDateFinIsInShouldWork() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where dateFin in DEFAULT_DATE_FIN or UPDATED_DATE_FIN
        defaultContratShouldBeFound("dateFin.in=" + DEFAULT_DATE_FIN + "," + UPDATED_DATE_FIN);

        // Get all the contratList where dateFin equals to UPDATED_DATE_FIN
        defaultContratShouldNotBeFound("dateFin.in=" + UPDATED_DATE_FIN);
    }

    @Test
    @Transactional
    public void getAllContratsByDateFinIsNullOrNotNull() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where dateFin is not null
        defaultContratShouldBeFound("dateFin.specified=true");

        // Get all the contratList where dateFin is null
        defaultContratShouldNotBeFound("dateFin.specified=false");
    }

    @Test
    @Transactional
    public void getAllContratsByNiveauEtudeIsEqualToSomething() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where niveauEtude equals to DEFAULT_NIVEAU_ETUDE
        defaultContratShouldBeFound("niveauEtude.equals=" + DEFAULT_NIVEAU_ETUDE);

        // Get all the contratList where niveauEtude equals to UPDATED_NIVEAU_ETUDE
        defaultContratShouldNotBeFound("niveauEtude.equals=" + UPDATED_NIVEAU_ETUDE);
    }

    @Test
    @Transactional
    public void getAllContratsByNiveauEtudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where niveauEtude not equals to DEFAULT_NIVEAU_ETUDE
        defaultContratShouldNotBeFound("niveauEtude.notEquals=" + DEFAULT_NIVEAU_ETUDE);

        // Get all the contratList where niveauEtude not equals to UPDATED_NIVEAU_ETUDE
        defaultContratShouldBeFound("niveauEtude.notEquals=" + UPDATED_NIVEAU_ETUDE);
    }

    @Test
    @Transactional
    public void getAllContratsByNiveauEtudeIsInShouldWork() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where niveauEtude in DEFAULT_NIVEAU_ETUDE or UPDATED_NIVEAU_ETUDE
        defaultContratShouldBeFound("niveauEtude.in=" + DEFAULT_NIVEAU_ETUDE + "," + UPDATED_NIVEAU_ETUDE);

        // Get all the contratList where niveauEtude equals to UPDATED_NIVEAU_ETUDE
        defaultContratShouldNotBeFound("niveauEtude.in=" + UPDATED_NIVEAU_ETUDE);
    }

    @Test
    @Transactional
    public void getAllContratsByNiveauEtudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where niveauEtude is not null
        defaultContratShouldBeFound("niveauEtude.specified=true");

        // Get all the contratList where niveauEtude is null
        defaultContratShouldNotBeFound("niveauEtude.specified=false");
    }

    @Test
    @Transactional
    public void getAllContratsByTypeContratIsEqualToSomething() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where typeContrat equals to DEFAULT_TYPE_CONTRAT
        defaultContratShouldBeFound("typeContrat.equals=" + DEFAULT_TYPE_CONTRAT);

        // Get all the contratList where typeContrat equals to UPDATED_TYPE_CONTRAT
        defaultContratShouldNotBeFound("typeContrat.equals=" + UPDATED_TYPE_CONTRAT);
    }

    @Test
    @Transactional
    public void getAllContratsByTypeContratIsNotEqualToSomething() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where typeContrat not equals to DEFAULT_TYPE_CONTRAT
        defaultContratShouldNotBeFound("typeContrat.notEquals=" + DEFAULT_TYPE_CONTRAT);

        // Get all the contratList where typeContrat not equals to UPDATED_TYPE_CONTRAT
        defaultContratShouldBeFound("typeContrat.notEquals=" + UPDATED_TYPE_CONTRAT);
    }

    @Test
    @Transactional
    public void getAllContratsByTypeContratIsInShouldWork() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where typeContrat in DEFAULT_TYPE_CONTRAT or UPDATED_TYPE_CONTRAT
        defaultContratShouldBeFound("typeContrat.in=" + DEFAULT_TYPE_CONTRAT + "," + UPDATED_TYPE_CONTRAT);

        // Get all the contratList where typeContrat equals to UPDATED_TYPE_CONTRAT
        defaultContratShouldNotBeFound("typeContrat.in=" + UPDATED_TYPE_CONTRAT);
    }

    @Test
    @Transactional
    public void getAllContratsByTypeContratIsNullOrNotNull() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);

        // Get all the contratList where typeContrat is not null
        defaultContratShouldBeFound("typeContrat.specified=true");

        // Get all the contratList where typeContrat is null
        defaultContratShouldNotBeFound("typeContrat.specified=false");
    }

    @Test
    @Transactional
    public void getAllContratsByEmployeIsEqualToSomething() throws Exception {
        // Initialize the database
        contratRepository.saveAndFlush(contrat);
        Employe employe = EmployeResourceIT.createEntity(em);
        em.persist(employe);
        em.flush();
        contrat.setEmploye(employe);
        contratRepository.saveAndFlush(contrat);
        Long employeId = employe.getId();

        // Get all the contratList where employe equals to employeId
        defaultContratShouldBeFound("employeId.equals=" + employeId);

        // Get all the contratList where employe equals to employeId + 1
        defaultContratShouldNotBeFound("employeId.equals=" + (employeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultContratShouldBeFound(String filter) throws Exception {
        restContratMockMvc.perform(get("/api/contrats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contrat.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroContrat").value(hasItem(DEFAULT_NUMERO_CONTRAT)))
            .andExpect(jsonPath("$.[*].libelleContrat").value(hasItem(DEFAULT_LIBELLE_CONTRAT)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].niveauEtude").value(hasItem(DEFAULT_NIVEAU_ETUDE.toString())))
            .andExpect(jsonPath("$.[*].typeContrat").value(hasItem(DEFAULT_TYPE_CONTRAT.toString())));

        // Check, that the count call also returns 1
        restContratMockMvc.perform(get("/api/contrats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultContratShouldNotBeFound(String filter) throws Exception {
        restContratMockMvc.perform(get("/api/contrats?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restContratMockMvc.perform(get("/api/contrats/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingContrat() throws Exception {
        // Get the contrat
        restContratMockMvc.perform(get("/api/contrats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContrat() throws Exception {
        // Initialize the database
        contratService.save(contrat);

        int databaseSizeBeforeUpdate = contratRepository.findAll().size();

        // Update the contrat
        Contrat updatedContrat = contratRepository.findById(contrat.getId()).get();
        // Disconnect from session so that the updates on updatedContrat are not directly saved in db
        em.detach(updatedContrat);
        updatedContrat
            .numeroContrat(UPDATED_NUMERO_CONTRAT)
            .libelleContrat(UPDATED_LIBELLE_CONTRAT)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .niveauEtude(UPDATED_NIVEAU_ETUDE)
            .typeContrat(UPDATED_TYPE_CONTRAT);

        restContratMockMvc.perform(put("/api/contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContrat)))
            .andExpect(status().isOk());

        // Validate the Contrat in the database
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeUpdate);
        Contrat testContrat = contratList.get(contratList.size() - 1);
        assertThat(testContrat.getNumeroContrat()).isEqualTo(UPDATED_NUMERO_CONTRAT);
        assertThat(testContrat.getLibelleContrat()).isEqualTo(UPDATED_LIBELLE_CONTRAT);
        assertThat(testContrat.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testContrat.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testContrat.getNiveauEtude()).isEqualTo(UPDATED_NIVEAU_ETUDE);
        assertThat(testContrat.getTypeContrat()).isEqualTo(UPDATED_TYPE_CONTRAT);
    }

    @Test
    @Transactional
    public void updateNonExistingContrat() throws Exception {
        int databaseSizeBeforeUpdate = contratRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContratMockMvc.perform(put("/api/contrats")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contrat)))
            .andExpect(status().isBadRequest());

        // Validate the Contrat in the database
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContrat() throws Exception {
        // Initialize the database
        contratService.save(contrat);

        int databaseSizeBeforeDelete = contratRepository.findAll().size();

        // Delete the contrat
        restContratMockMvc.perform(delete("/api/contrats/{id}", contrat.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contrat> contratList = contratRepository.findAll();
        assertThat(contratList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
