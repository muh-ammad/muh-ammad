package com.themalomars.gpecmanager.web.rest;

import com.themalomars.gpecmanager.GpecmanagerApp;
import com.themalomars.gpecmanager.domain.Employe;
import com.themalomars.gpecmanager.domain.Contrat;
import com.themalomars.gpecmanager.domain.Diplome;
import com.themalomars.gpecmanager.domain.Fonction;
import com.themalomars.gpecmanager.domain.MembreFamille;
import com.themalomars.gpecmanager.domain.Distinction;
import com.themalomars.gpecmanager.domain.OperationExterieur;
import com.themalomars.gpecmanager.domain.ServiceAffecte;
import com.themalomars.gpecmanager.domain.ServiceFrequente;
import com.themalomars.gpecmanager.repository.EmployeRepository;
import com.themalomars.gpecmanager.service.EmployeService;
import com.themalomars.gpecmanager.service.dto.EmployeCriteria;
import com.themalomars.gpecmanager.service.EmployeQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.themalomars.gpecmanager.domain.enumeration.GroupeSanguin;
import com.themalomars.gpecmanager.domain.enumeration.Matrimonial;
import com.themalomars.gpecmanager.domain.enumeration.Sexe;
import com.themalomars.gpecmanager.domain.enumeration.Disponibilite;
/**
 * Integration tests for the {@link EmployeResource} REST controller.
 */
@SpringBootTest(classes = GpecmanagerApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeResourceIT {

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOMS = "AAAAAAAAAA";
    private static final String UPDATED_PRENOMS = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_INTITULE_EMPLOYE = "AAAAAAAAAA";
    private static final String UPDATED_INTITULE_EMPLOYE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_NAISSANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_NAISSANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LIEU_NAISSANCE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LIEU_NAISSANCE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NUMERO_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_EMBAUCHEMENT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_EMBAUCHEMENT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_RETRAITE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_RETRAITE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_AGE = 1L;
    private static final Long UPDATED_AGE = 2L;
    private static final Long SMALLER_AGE = 1L - 1L;

    private static final String DEFAULT_NUMERO_CNI = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CNI = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_IPRES = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_IPRES = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_CSS = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_CSS = "BBBBBBBBBB";

    private static final GroupeSanguin DEFAULT_GROUPE_SANGUIN = GroupeSanguin.AP;
    private static final GroupeSanguin UPDATED_GROUPE_SANGUIN = GroupeSanguin.AM;

    private static final Matrimonial DEFAULT_SITUATION_MATRIMONIAL = Matrimonial.CELIBATAIRE;
    private static final Matrimonial UPDATED_SITUATION_MATRIMONIAL = Matrimonial.MARIE;

    private static final Sexe DEFAULT_SEXE = Sexe.FEMININ;
    private static final Sexe UPDATED_SEXE = Sexe.MASCULIN;

    private static final Disponibilite DEFAULT_DISPONIBILITE = Disponibilite.ENCOURS;
    private static final Disponibilite UPDATED_DISPONIBILITE = Disponibilite.ARRETE;

    @Autowired
    private EmployeRepository employeRepository;

    @Mock
    private EmployeRepository employeRepositoryMock;

    @Mock
    private EmployeService employeServiceMock;

    @Autowired
    private EmployeService employeService;

    @Autowired
    private EmployeQueryService employeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeMockMvc;

    private Employe employe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employe createEntity(EntityManager em) {
        Employe employe = new Employe()
            .matricule(DEFAULT_MATRICULE)
            .prenoms(DEFAULT_PRENOMS)
            .nom(DEFAULT_NOM)
            .intituleEmploye(DEFAULT_INTITULE_EMPLOYE)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .lieuNaissance(DEFAULT_LIEU_NAISSANCE)
            .numeroTelephone(DEFAULT_NUMERO_TELEPHONE)
            .adresse(DEFAULT_ADRESSE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .email(DEFAULT_EMAIL)
            .dateEmbauchement(DEFAULT_DATE_EMBAUCHEMENT)
            .dateRetraite(DEFAULT_DATE_RETRAITE)
            .age(DEFAULT_AGE)
            .numeroCni(DEFAULT_NUMERO_CNI)
            .numeroIpres(DEFAULT_NUMERO_IPRES)
            .numeroCss(DEFAULT_NUMERO_CSS)
            .groupeSanguin(DEFAULT_GROUPE_SANGUIN)
            .situationMatrimonial(DEFAULT_SITUATION_MATRIMONIAL)
            .sexe(DEFAULT_SEXE)
            .disponibilite(DEFAULT_DISPONIBILITE);
        return employe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employe createUpdatedEntity(EntityManager em) {
        Employe employe = new Employe()
            .matricule(UPDATED_MATRICULE)
            .prenoms(UPDATED_PRENOMS)
            .nom(UPDATED_NOM)
            .intituleEmploye(UPDATED_INTITULE_EMPLOYE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .numeroTelephone(UPDATED_NUMERO_TELEPHONE)
            .adresse(UPDATED_ADRESSE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .dateEmbauchement(UPDATED_DATE_EMBAUCHEMENT)
            .dateRetraite(UPDATED_DATE_RETRAITE)
            .age(UPDATED_AGE)
            .numeroCni(UPDATED_NUMERO_CNI)
            .numeroIpres(UPDATED_NUMERO_IPRES)
            .numeroCss(UPDATED_NUMERO_CSS)
            .groupeSanguin(UPDATED_GROUPE_SANGUIN)
            .situationMatrimonial(UPDATED_SITUATION_MATRIMONIAL)
            .sexe(UPDATED_SEXE)
            .disponibilite(UPDATED_DISPONIBILITE);
        return employe;
    }

    @BeforeEach
    public void initTest() {
        employe = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmploye() throws Exception {
        int databaseSizeBeforeCreate = employeRepository.findAll().size();
        // Create the Employe
        restEmployeMockMvc.perform(post("/api/employes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employe)))
            .andExpect(status().isCreated());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeCreate + 1);
        Employe testEmploye = employeList.get(employeList.size() - 1);
        assertThat(testEmploye.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testEmploye.getPrenoms()).isEqualTo(DEFAULT_PRENOMS);
        assertThat(testEmploye.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testEmploye.getIntituleEmploye()).isEqualTo(DEFAULT_INTITULE_EMPLOYE);
        assertThat(testEmploye.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testEmploye.getLieuNaissance()).isEqualTo(DEFAULT_LIEU_NAISSANCE);
        assertThat(testEmploye.getNumeroTelephone()).isEqualTo(DEFAULT_NUMERO_TELEPHONE);
        assertThat(testEmploye.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testEmploye.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testEmploye.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testEmploye.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmploye.getDateEmbauchement()).isEqualTo(DEFAULT_DATE_EMBAUCHEMENT);
        assertThat(testEmploye.getDateRetraite()).isEqualTo(DEFAULT_DATE_RETRAITE);
        assertThat(testEmploye.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testEmploye.getNumeroCni()).isEqualTo(DEFAULT_NUMERO_CNI);
        assertThat(testEmploye.getNumeroIpres()).isEqualTo(DEFAULT_NUMERO_IPRES);
        assertThat(testEmploye.getNumeroCss()).isEqualTo(DEFAULT_NUMERO_CSS);
        assertThat(testEmploye.getGroupeSanguin()).isEqualTo(DEFAULT_GROUPE_SANGUIN);
        assertThat(testEmploye.getSituationMatrimonial()).isEqualTo(DEFAULT_SITUATION_MATRIMONIAL);
        assertThat(testEmploye.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testEmploye.getDisponibilite()).isEqualTo(DEFAULT_DISPONIBILITE);
    }

    @Test
    @Transactional
    public void createEmployeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeRepository.findAll().size();

        // Create the Employe with an existing ID
        employe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeMockMvc.perform(post("/api/employes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employe)))
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkMatriculeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeRepository.findAll().size();
        // set the field null
        employe.setMatricule(null);

        // Create the Employe, which fails.


        restEmployeMockMvc.perform(post("/api/employes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employe)))
            .andExpect(status().isBadRequest());

        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomsIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeRepository.findAll().size();
        // set the field null
        employe.setPrenoms(null);

        // Create the Employe, which fails.


        restEmployeMockMvc.perform(post("/api/employes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employe)))
            .andExpect(status().isBadRequest());

        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeRepository.findAll().size();
        // set the field null
        employe.setNom(null);

        // Create the Employe, which fails.


        restEmployeMockMvc.perform(post("/api/employes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employe)))
            .andExpect(status().isBadRequest());

        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeRepository.findAll().size();
        // set the field null
        employe.setDateNaissance(null);

        // Create the Employe, which fails.


        restEmployeMockMvc.perform(post("/api/employes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employe)))
            .andExpect(status().isBadRequest());

        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLieuNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeRepository.findAll().size();
        // set the field null
        employe.setLieuNaissance(null);

        // Create the Employe, which fails.


        restEmployeMockMvc.perform(post("/api/employes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employe)))
            .andExpect(status().isBadRequest());

        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeRepository.findAll().size();
        // set the field null
        employe.setNumeroTelephone(null);

        // Create the Employe, which fails.


        restEmployeMockMvc.perform(post("/api/employes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employe)))
            .andExpect(status().isBadRequest());

        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployes() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList
        restEmployeMockMvc.perform(get("/api/employes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employe.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].prenoms").value(hasItem(DEFAULT_PRENOMS)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].intituleEmploye").value(hasItem(DEFAULT_INTITULE_EMPLOYE)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaissance").value(hasItem(DEFAULT_LIEU_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].numeroTelephone").value(hasItem(DEFAULT_NUMERO_TELEPHONE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dateEmbauchement").value(hasItem(DEFAULT_DATE_EMBAUCHEMENT.toString())))
            .andExpect(jsonPath("$.[*].dateRetraite").value(hasItem(DEFAULT_DATE_RETRAITE.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.intValue())))
            .andExpect(jsonPath("$.[*].numeroCni").value(hasItem(DEFAULT_NUMERO_CNI)))
            .andExpect(jsonPath("$.[*].numeroIpres").value(hasItem(DEFAULT_NUMERO_IPRES)))
            .andExpect(jsonPath("$.[*].numeroCss").value(hasItem(DEFAULT_NUMERO_CSS)))
            .andExpect(jsonPath("$.[*].groupeSanguin").value(hasItem(DEFAULT_GROUPE_SANGUIN.toString())))
            .andExpect(jsonPath("$.[*].situationMatrimonial").value(hasItem(DEFAULT_SITUATION_MATRIMONIAL.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].disponibilite").value(hasItem(DEFAULT_DISPONIBILITE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllEmployesWithEagerRelationshipsIsEnabled() throws Exception {
        when(employeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmployeMockMvc.perform(get("/api/employes?eagerload=true"))
            .andExpect(status().isOk());

        verify(employeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllEmployesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(employeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmployeMockMvc.perform(get("/api/employes?eagerload=true"))
            .andExpect(status().isOk());

        verify(employeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getEmploye() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get the employe
        restEmployeMockMvc.perform(get("/api/employes/{id}", employe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employe.getId().intValue()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.prenoms").value(DEFAULT_PRENOMS))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.intituleEmploye").value(DEFAULT_INTITULE_EMPLOYE))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.lieuNaissance").value(DEFAULT_LIEU_NAISSANCE.toString()))
            .andExpect(jsonPath("$.numeroTelephone").value(DEFAULT_NUMERO_TELEPHONE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.dateEmbauchement").value(DEFAULT_DATE_EMBAUCHEMENT.toString()))
            .andExpect(jsonPath("$.dateRetraite").value(DEFAULT_DATE_RETRAITE.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.intValue()))
            .andExpect(jsonPath("$.numeroCni").value(DEFAULT_NUMERO_CNI))
            .andExpect(jsonPath("$.numeroIpres").value(DEFAULT_NUMERO_IPRES))
            .andExpect(jsonPath("$.numeroCss").value(DEFAULT_NUMERO_CSS))
            .andExpect(jsonPath("$.groupeSanguin").value(DEFAULT_GROUPE_SANGUIN.toString()))
            .andExpect(jsonPath("$.situationMatrimonial").value(DEFAULT_SITUATION_MATRIMONIAL.toString()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.disponibilite").value(DEFAULT_DISPONIBILITE.toString()));
    }


    @Test
    @Transactional
    public void getEmployesByIdFiltering() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        Long id = employe.getId();

        defaultEmployeShouldBeFound("id.equals=" + id);
        defaultEmployeShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEmployesByMatriculeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where matricule equals to DEFAULT_MATRICULE
        defaultEmployeShouldBeFound("matricule.equals=" + DEFAULT_MATRICULE);

        // Get all the employeList where matricule equals to UPDATED_MATRICULE
        defaultEmployeShouldNotBeFound("matricule.equals=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllEmployesByMatriculeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where matricule not equals to DEFAULT_MATRICULE
        defaultEmployeShouldNotBeFound("matricule.notEquals=" + DEFAULT_MATRICULE);

        // Get all the employeList where matricule not equals to UPDATED_MATRICULE
        defaultEmployeShouldBeFound("matricule.notEquals=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllEmployesByMatriculeIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where matricule in DEFAULT_MATRICULE or UPDATED_MATRICULE
        defaultEmployeShouldBeFound("matricule.in=" + DEFAULT_MATRICULE + "," + UPDATED_MATRICULE);

        // Get all the employeList where matricule equals to UPDATED_MATRICULE
        defaultEmployeShouldNotBeFound("matricule.in=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllEmployesByMatriculeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where matricule is not null
        defaultEmployeShouldBeFound("matricule.specified=true");

        // Get all the employeList where matricule is null
        defaultEmployeShouldNotBeFound("matricule.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployesByMatriculeContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where matricule contains DEFAULT_MATRICULE
        defaultEmployeShouldBeFound("matricule.contains=" + DEFAULT_MATRICULE);

        // Get all the employeList where matricule contains UPDATED_MATRICULE
        defaultEmployeShouldNotBeFound("matricule.contains=" + UPDATED_MATRICULE);
    }

    @Test
    @Transactional
    public void getAllEmployesByMatriculeNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where matricule does not contain DEFAULT_MATRICULE
        defaultEmployeShouldNotBeFound("matricule.doesNotContain=" + DEFAULT_MATRICULE);

        // Get all the employeList where matricule does not contain UPDATED_MATRICULE
        defaultEmployeShouldBeFound("matricule.doesNotContain=" + UPDATED_MATRICULE);
    }


    @Test
    @Transactional
    public void getAllEmployesByPrenomsIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where prenoms equals to DEFAULT_PRENOMS
        defaultEmployeShouldBeFound("prenoms.equals=" + DEFAULT_PRENOMS);

        // Get all the employeList where prenoms equals to UPDATED_PRENOMS
        defaultEmployeShouldNotBeFound("prenoms.equals=" + UPDATED_PRENOMS);
    }

    @Test
    @Transactional
    public void getAllEmployesByPrenomsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where prenoms not equals to DEFAULT_PRENOMS
        defaultEmployeShouldNotBeFound("prenoms.notEquals=" + DEFAULT_PRENOMS);

        // Get all the employeList where prenoms not equals to UPDATED_PRENOMS
        defaultEmployeShouldBeFound("prenoms.notEquals=" + UPDATED_PRENOMS);
    }

    @Test
    @Transactional
    public void getAllEmployesByPrenomsIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where prenoms in DEFAULT_PRENOMS or UPDATED_PRENOMS
        defaultEmployeShouldBeFound("prenoms.in=" + DEFAULT_PRENOMS + "," + UPDATED_PRENOMS);

        // Get all the employeList where prenoms equals to UPDATED_PRENOMS
        defaultEmployeShouldNotBeFound("prenoms.in=" + UPDATED_PRENOMS);
    }

    @Test
    @Transactional
    public void getAllEmployesByPrenomsIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where prenoms is not null
        defaultEmployeShouldBeFound("prenoms.specified=true");

        // Get all the employeList where prenoms is null
        defaultEmployeShouldNotBeFound("prenoms.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployesByPrenomsContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where prenoms contains DEFAULT_PRENOMS
        defaultEmployeShouldBeFound("prenoms.contains=" + DEFAULT_PRENOMS);

        // Get all the employeList where prenoms contains UPDATED_PRENOMS
        defaultEmployeShouldNotBeFound("prenoms.contains=" + UPDATED_PRENOMS);
    }

    @Test
    @Transactional
    public void getAllEmployesByPrenomsNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where prenoms does not contain DEFAULT_PRENOMS
        defaultEmployeShouldNotBeFound("prenoms.doesNotContain=" + DEFAULT_PRENOMS);

        // Get all the employeList where prenoms does not contain UPDATED_PRENOMS
        defaultEmployeShouldBeFound("prenoms.doesNotContain=" + UPDATED_PRENOMS);
    }


    @Test
    @Transactional
    public void getAllEmployesByNomIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where nom equals to DEFAULT_NOM
        defaultEmployeShouldBeFound("nom.equals=" + DEFAULT_NOM);

        // Get all the employeList where nom equals to UPDATED_NOM
        defaultEmployeShouldNotBeFound("nom.equals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEmployesByNomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where nom not equals to DEFAULT_NOM
        defaultEmployeShouldNotBeFound("nom.notEquals=" + DEFAULT_NOM);

        // Get all the employeList where nom not equals to UPDATED_NOM
        defaultEmployeShouldBeFound("nom.notEquals=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEmployesByNomIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where nom in DEFAULT_NOM or UPDATED_NOM
        defaultEmployeShouldBeFound("nom.in=" + DEFAULT_NOM + "," + UPDATED_NOM);

        // Get all the employeList where nom equals to UPDATED_NOM
        defaultEmployeShouldNotBeFound("nom.in=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEmployesByNomIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where nom is not null
        defaultEmployeShouldBeFound("nom.specified=true");

        // Get all the employeList where nom is null
        defaultEmployeShouldNotBeFound("nom.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployesByNomContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where nom contains DEFAULT_NOM
        defaultEmployeShouldBeFound("nom.contains=" + DEFAULT_NOM);

        // Get all the employeList where nom contains UPDATED_NOM
        defaultEmployeShouldNotBeFound("nom.contains=" + UPDATED_NOM);
    }

    @Test
    @Transactional
    public void getAllEmployesByNomNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where nom does not contain DEFAULT_NOM
        defaultEmployeShouldNotBeFound("nom.doesNotContain=" + DEFAULT_NOM);

        // Get all the employeList where nom does not contain UPDATED_NOM
        defaultEmployeShouldBeFound("nom.doesNotContain=" + UPDATED_NOM);
    }


    @Test
    @Transactional
    public void getAllEmployesByIntituleEmployeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where intituleEmploye equals to DEFAULT_INTITULE_EMPLOYE
        defaultEmployeShouldBeFound("intituleEmploye.equals=" + DEFAULT_INTITULE_EMPLOYE);

        // Get all the employeList where intituleEmploye equals to UPDATED_INTITULE_EMPLOYE
        defaultEmployeShouldNotBeFound("intituleEmploye.equals=" + UPDATED_INTITULE_EMPLOYE);
    }

    @Test
    @Transactional
    public void getAllEmployesByIntituleEmployeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where intituleEmploye not equals to DEFAULT_INTITULE_EMPLOYE
        defaultEmployeShouldNotBeFound("intituleEmploye.notEquals=" + DEFAULT_INTITULE_EMPLOYE);

        // Get all the employeList where intituleEmploye not equals to UPDATED_INTITULE_EMPLOYE
        defaultEmployeShouldBeFound("intituleEmploye.notEquals=" + UPDATED_INTITULE_EMPLOYE);
    }

    @Test
    @Transactional
    public void getAllEmployesByIntituleEmployeIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where intituleEmploye in DEFAULT_INTITULE_EMPLOYE or UPDATED_INTITULE_EMPLOYE
        defaultEmployeShouldBeFound("intituleEmploye.in=" + DEFAULT_INTITULE_EMPLOYE + "," + UPDATED_INTITULE_EMPLOYE);

        // Get all the employeList where intituleEmploye equals to UPDATED_INTITULE_EMPLOYE
        defaultEmployeShouldNotBeFound("intituleEmploye.in=" + UPDATED_INTITULE_EMPLOYE);
    }

    @Test
    @Transactional
    public void getAllEmployesByIntituleEmployeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where intituleEmploye is not null
        defaultEmployeShouldBeFound("intituleEmploye.specified=true");

        // Get all the employeList where intituleEmploye is null
        defaultEmployeShouldNotBeFound("intituleEmploye.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployesByIntituleEmployeContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where intituleEmploye contains DEFAULT_INTITULE_EMPLOYE
        defaultEmployeShouldBeFound("intituleEmploye.contains=" + DEFAULT_INTITULE_EMPLOYE);

        // Get all the employeList where intituleEmploye contains UPDATED_INTITULE_EMPLOYE
        defaultEmployeShouldNotBeFound("intituleEmploye.contains=" + UPDATED_INTITULE_EMPLOYE);
    }

    @Test
    @Transactional
    public void getAllEmployesByIntituleEmployeNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where intituleEmploye does not contain DEFAULT_INTITULE_EMPLOYE
        defaultEmployeShouldNotBeFound("intituleEmploye.doesNotContain=" + DEFAULT_INTITULE_EMPLOYE);

        // Get all the employeList where intituleEmploye does not contain UPDATED_INTITULE_EMPLOYE
        defaultEmployeShouldBeFound("intituleEmploye.doesNotContain=" + UPDATED_INTITULE_EMPLOYE);
    }


    @Test
    @Transactional
    public void getAllEmployesByDateNaissanceIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where dateNaissance equals to DEFAULT_DATE_NAISSANCE
        defaultEmployeShouldBeFound("dateNaissance.equals=" + DEFAULT_DATE_NAISSANCE);

        // Get all the employeList where dateNaissance equals to UPDATED_DATE_NAISSANCE
        defaultEmployeShouldNotBeFound("dateNaissance.equals=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllEmployesByDateNaissanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where dateNaissance not equals to DEFAULT_DATE_NAISSANCE
        defaultEmployeShouldNotBeFound("dateNaissance.notEquals=" + DEFAULT_DATE_NAISSANCE);

        // Get all the employeList where dateNaissance not equals to UPDATED_DATE_NAISSANCE
        defaultEmployeShouldBeFound("dateNaissance.notEquals=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllEmployesByDateNaissanceIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where dateNaissance in DEFAULT_DATE_NAISSANCE or UPDATED_DATE_NAISSANCE
        defaultEmployeShouldBeFound("dateNaissance.in=" + DEFAULT_DATE_NAISSANCE + "," + UPDATED_DATE_NAISSANCE);

        // Get all the employeList where dateNaissance equals to UPDATED_DATE_NAISSANCE
        defaultEmployeShouldNotBeFound("dateNaissance.in=" + UPDATED_DATE_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllEmployesByDateNaissanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where dateNaissance is not null
        defaultEmployeShouldBeFound("dateNaissance.specified=true");

        // Get all the employeList where dateNaissance is null
        defaultEmployeShouldNotBeFound("dateNaissance.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesByLieuNaissanceIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where lieuNaissance equals to DEFAULT_LIEU_NAISSANCE
        defaultEmployeShouldBeFound("lieuNaissance.equals=" + DEFAULT_LIEU_NAISSANCE);

        // Get all the employeList where lieuNaissance equals to UPDATED_LIEU_NAISSANCE
        defaultEmployeShouldNotBeFound("lieuNaissance.equals=" + UPDATED_LIEU_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllEmployesByLieuNaissanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where lieuNaissance not equals to DEFAULT_LIEU_NAISSANCE
        defaultEmployeShouldNotBeFound("lieuNaissance.notEquals=" + DEFAULT_LIEU_NAISSANCE);

        // Get all the employeList where lieuNaissance not equals to UPDATED_LIEU_NAISSANCE
        defaultEmployeShouldBeFound("lieuNaissance.notEquals=" + UPDATED_LIEU_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllEmployesByLieuNaissanceIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where lieuNaissance in DEFAULT_LIEU_NAISSANCE or UPDATED_LIEU_NAISSANCE
        defaultEmployeShouldBeFound("lieuNaissance.in=" + DEFAULT_LIEU_NAISSANCE + "," + UPDATED_LIEU_NAISSANCE);

        // Get all the employeList where lieuNaissance equals to UPDATED_LIEU_NAISSANCE
        defaultEmployeShouldNotBeFound("lieuNaissance.in=" + UPDATED_LIEU_NAISSANCE);
    }

    @Test
    @Transactional
    public void getAllEmployesByLieuNaissanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where lieuNaissance is not null
        defaultEmployeShouldBeFound("lieuNaissance.specified=true");

        // Get all the employeList where lieuNaissance is null
        defaultEmployeShouldNotBeFound("lieuNaissance.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesByNumeroTelephoneIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroTelephone equals to DEFAULT_NUMERO_TELEPHONE
        defaultEmployeShouldBeFound("numeroTelephone.equals=" + DEFAULT_NUMERO_TELEPHONE);

        // Get all the employeList where numeroTelephone equals to UPDATED_NUMERO_TELEPHONE
        defaultEmployeShouldNotBeFound("numeroTelephone.equals=" + UPDATED_NUMERO_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumeroTelephoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroTelephone not equals to DEFAULT_NUMERO_TELEPHONE
        defaultEmployeShouldNotBeFound("numeroTelephone.notEquals=" + DEFAULT_NUMERO_TELEPHONE);

        // Get all the employeList where numeroTelephone not equals to UPDATED_NUMERO_TELEPHONE
        defaultEmployeShouldBeFound("numeroTelephone.notEquals=" + UPDATED_NUMERO_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumeroTelephoneIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroTelephone in DEFAULT_NUMERO_TELEPHONE or UPDATED_NUMERO_TELEPHONE
        defaultEmployeShouldBeFound("numeroTelephone.in=" + DEFAULT_NUMERO_TELEPHONE + "," + UPDATED_NUMERO_TELEPHONE);

        // Get all the employeList where numeroTelephone equals to UPDATED_NUMERO_TELEPHONE
        defaultEmployeShouldNotBeFound("numeroTelephone.in=" + UPDATED_NUMERO_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumeroTelephoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroTelephone is not null
        defaultEmployeShouldBeFound("numeroTelephone.specified=true");

        // Get all the employeList where numeroTelephone is null
        defaultEmployeShouldNotBeFound("numeroTelephone.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployesByNumeroTelephoneContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroTelephone contains DEFAULT_NUMERO_TELEPHONE
        defaultEmployeShouldBeFound("numeroTelephone.contains=" + DEFAULT_NUMERO_TELEPHONE);

        // Get all the employeList where numeroTelephone contains UPDATED_NUMERO_TELEPHONE
        defaultEmployeShouldNotBeFound("numeroTelephone.contains=" + UPDATED_NUMERO_TELEPHONE);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumeroTelephoneNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroTelephone does not contain DEFAULT_NUMERO_TELEPHONE
        defaultEmployeShouldNotBeFound("numeroTelephone.doesNotContain=" + DEFAULT_NUMERO_TELEPHONE);

        // Get all the employeList where numeroTelephone does not contain UPDATED_NUMERO_TELEPHONE
        defaultEmployeShouldBeFound("numeroTelephone.doesNotContain=" + UPDATED_NUMERO_TELEPHONE);
    }


    @Test
    @Transactional
    public void getAllEmployesByAdresseIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where adresse equals to DEFAULT_ADRESSE
        defaultEmployeShouldBeFound("adresse.equals=" + DEFAULT_ADRESSE);

        // Get all the employeList where adresse equals to UPDATED_ADRESSE
        defaultEmployeShouldNotBeFound("adresse.equals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllEmployesByAdresseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where adresse not equals to DEFAULT_ADRESSE
        defaultEmployeShouldNotBeFound("adresse.notEquals=" + DEFAULT_ADRESSE);

        // Get all the employeList where adresse not equals to UPDATED_ADRESSE
        defaultEmployeShouldBeFound("adresse.notEquals=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllEmployesByAdresseIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where adresse in DEFAULT_ADRESSE or UPDATED_ADRESSE
        defaultEmployeShouldBeFound("adresse.in=" + DEFAULT_ADRESSE + "," + UPDATED_ADRESSE);

        // Get all the employeList where adresse equals to UPDATED_ADRESSE
        defaultEmployeShouldNotBeFound("adresse.in=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllEmployesByAdresseIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where adresse is not null
        defaultEmployeShouldBeFound("adresse.specified=true");

        // Get all the employeList where adresse is null
        defaultEmployeShouldNotBeFound("adresse.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployesByAdresseContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where adresse contains DEFAULT_ADRESSE
        defaultEmployeShouldBeFound("adresse.contains=" + DEFAULT_ADRESSE);

        // Get all the employeList where adresse contains UPDATED_ADRESSE
        defaultEmployeShouldNotBeFound("adresse.contains=" + UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void getAllEmployesByAdresseNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where adresse does not contain DEFAULT_ADRESSE
        defaultEmployeShouldNotBeFound("adresse.doesNotContain=" + DEFAULT_ADRESSE);

        // Get all the employeList where adresse does not contain UPDATED_ADRESSE
        defaultEmployeShouldBeFound("adresse.doesNotContain=" + UPDATED_ADRESSE);
    }


    @Test
    @Transactional
    public void getAllEmployesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where email equals to DEFAULT_EMAIL
        defaultEmployeShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the employeList where email equals to UPDATED_EMAIL
        defaultEmployeShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployesByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where email not equals to DEFAULT_EMAIL
        defaultEmployeShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the employeList where email not equals to UPDATED_EMAIL
        defaultEmployeShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEmployeShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the employeList where email equals to UPDATED_EMAIL
        defaultEmployeShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where email is not null
        defaultEmployeShouldBeFound("email.specified=true");

        // Get all the employeList where email is null
        defaultEmployeShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployesByEmailContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where email contains DEFAULT_EMAIL
        defaultEmployeShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the employeList where email contains UPDATED_EMAIL
        defaultEmployeShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where email does not contain DEFAULT_EMAIL
        defaultEmployeShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the employeList where email does not contain UPDATED_EMAIL
        defaultEmployeShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllEmployesByDateEmbauchementIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where dateEmbauchement equals to DEFAULT_DATE_EMBAUCHEMENT
        defaultEmployeShouldBeFound("dateEmbauchement.equals=" + DEFAULT_DATE_EMBAUCHEMENT);

        // Get all the employeList where dateEmbauchement equals to UPDATED_DATE_EMBAUCHEMENT
        defaultEmployeShouldNotBeFound("dateEmbauchement.equals=" + UPDATED_DATE_EMBAUCHEMENT);
    }

    @Test
    @Transactional
    public void getAllEmployesByDateEmbauchementIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where dateEmbauchement not equals to DEFAULT_DATE_EMBAUCHEMENT
        defaultEmployeShouldNotBeFound("dateEmbauchement.notEquals=" + DEFAULT_DATE_EMBAUCHEMENT);

        // Get all the employeList where dateEmbauchement not equals to UPDATED_DATE_EMBAUCHEMENT
        defaultEmployeShouldBeFound("dateEmbauchement.notEquals=" + UPDATED_DATE_EMBAUCHEMENT);
    }

    @Test
    @Transactional
    public void getAllEmployesByDateEmbauchementIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where dateEmbauchement in DEFAULT_DATE_EMBAUCHEMENT or UPDATED_DATE_EMBAUCHEMENT
        defaultEmployeShouldBeFound("dateEmbauchement.in=" + DEFAULT_DATE_EMBAUCHEMENT + "," + UPDATED_DATE_EMBAUCHEMENT);

        // Get all the employeList where dateEmbauchement equals to UPDATED_DATE_EMBAUCHEMENT
        defaultEmployeShouldNotBeFound("dateEmbauchement.in=" + UPDATED_DATE_EMBAUCHEMENT);
    }

    @Test
    @Transactional
    public void getAllEmployesByDateEmbauchementIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where dateEmbauchement is not null
        defaultEmployeShouldBeFound("dateEmbauchement.specified=true");

        // Get all the employeList where dateEmbauchement is null
        defaultEmployeShouldNotBeFound("dateEmbauchement.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesByDateRetraiteIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where dateRetraite equals to DEFAULT_DATE_RETRAITE
        defaultEmployeShouldBeFound("dateRetraite.equals=" + DEFAULT_DATE_RETRAITE);

        // Get all the employeList where dateRetraite equals to UPDATED_DATE_RETRAITE
        defaultEmployeShouldNotBeFound("dateRetraite.equals=" + UPDATED_DATE_RETRAITE);
    }

    @Test
    @Transactional
    public void getAllEmployesByDateRetraiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where dateRetraite not equals to DEFAULT_DATE_RETRAITE
        defaultEmployeShouldNotBeFound("dateRetraite.notEquals=" + DEFAULT_DATE_RETRAITE);

        // Get all the employeList where dateRetraite not equals to UPDATED_DATE_RETRAITE
        defaultEmployeShouldBeFound("dateRetraite.notEquals=" + UPDATED_DATE_RETRAITE);
    }

    @Test
    @Transactional
    public void getAllEmployesByDateRetraiteIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where dateRetraite in DEFAULT_DATE_RETRAITE or UPDATED_DATE_RETRAITE
        defaultEmployeShouldBeFound("dateRetraite.in=" + DEFAULT_DATE_RETRAITE + "," + UPDATED_DATE_RETRAITE);

        // Get all the employeList where dateRetraite equals to UPDATED_DATE_RETRAITE
        defaultEmployeShouldNotBeFound("dateRetraite.in=" + UPDATED_DATE_RETRAITE);
    }

    @Test
    @Transactional
    public void getAllEmployesByDateRetraiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where dateRetraite is not null
        defaultEmployeShouldBeFound("dateRetraite.specified=true");

        // Get all the employeList where dateRetraite is null
        defaultEmployeShouldNotBeFound("dateRetraite.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where age equals to DEFAULT_AGE
        defaultEmployeShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the employeList where age equals to UPDATED_AGE
        defaultEmployeShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllEmployesByAgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where age not equals to DEFAULT_AGE
        defaultEmployeShouldNotBeFound("age.notEquals=" + DEFAULT_AGE);

        // Get all the employeList where age not equals to UPDATED_AGE
        defaultEmployeShouldBeFound("age.notEquals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllEmployesByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where age in DEFAULT_AGE or UPDATED_AGE
        defaultEmployeShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the employeList where age equals to UPDATED_AGE
        defaultEmployeShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllEmployesByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where age is not null
        defaultEmployeShouldBeFound("age.specified=true");

        // Get all the employeList where age is null
        defaultEmployeShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesByAgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where age is greater than or equal to DEFAULT_AGE
        defaultEmployeShouldBeFound("age.greaterThanOrEqual=" + DEFAULT_AGE);

        // Get all the employeList where age is greater than or equal to UPDATED_AGE
        defaultEmployeShouldNotBeFound("age.greaterThanOrEqual=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllEmployesByAgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where age is less than or equal to DEFAULT_AGE
        defaultEmployeShouldBeFound("age.lessThanOrEqual=" + DEFAULT_AGE);

        // Get all the employeList where age is less than or equal to SMALLER_AGE
        defaultEmployeShouldNotBeFound("age.lessThanOrEqual=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    public void getAllEmployesByAgeIsLessThanSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where age is less than DEFAULT_AGE
        defaultEmployeShouldNotBeFound("age.lessThan=" + DEFAULT_AGE);

        // Get all the employeList where age is less than UPDATED_AGE
        defaultEmployeShouldBeFound("age.lessThan=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllEmployesByAgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where age is greater than DEFAULT_AGE
        defaultEmployeShouldNotBeFound("age.greaterThan=" + DEFAULT_AGE);

        // Get all the employeList where age is greater than SMALLER_AGE
        defaultEmployeShouldBeFound("age.greaterThan=" + SMALLER_AGE);
    }


    @Test
    @Transactional
    public void getAllEmployesByNumeroCniIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroCni equals to DEFAULT_NUMERO_CNI
        defaultEmployeShouldBeFound("numeroCni.equals=" + DEFAULT_NUMERO_CNI);

        // Get all the employeList where numeroCni equals to UPDATED_NUMERO_CNI
        defaultEmployeShouldNotBeFound("numeroCni.equals=" + UPDATED_NUMERO_CNI);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumeroCniIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroCni not equals to DEFAULT_NUMERO_CNI
        defaultEmployeShouldNotBeFound("numeroCni.notEquals=" + DEFAULT_NUMERO_CNI);

        // Get all the employeList where numeroCni not equals to UPDATED_NUMERO_CNI
        defaultEmployeShouldBeFound("numeroCni.notEquals=" + UPDATED_NUMERO_CNI);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumeroCniIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroCni in DEFAULT_NUMERO_CNI or UPDATED_NUMERO_CNI
        defaultEmployeShouldBeFound("numeroCni.in=" + DEFAULT_NUMERO_CNI + "," + UPDATED_NUMERO_CNI);

        // Get all the employeList where numeroCni equals to UPDATED_NUMERO_CNI
        defaultEmployeShouldNotBeFound("numeroCni.in=" + UPDATED_NUMERO_CNI);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumeroCniIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroCni is not null
        defaultEmployeShouldBeFound("numeroCni.specified=true");

        // Get all the employeList where numeroCni is null
        defaultEmployeShouldNotBeFound("numeroCni.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployesByNumeroCniContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroCni contains DEFAULT_NUMERO_CNI
        defaultEmployeShouldBeFound("numeroCni.contains=" + DEFAULT_NUMERO_CNI);

        // Get all the employeList where numeroCni contains UPDATED_NUMERO_CNI
        defaultEmployeShouldNotBeFound("numeroCni.contains=" + UPDATED_NUMERO_CNI);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumeroCniNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroCni does not contain DEFAULT_NUMERO_CNI
        defaultEmployeShouldNotBeFound("numeroCni.doesNotContain=" + DEFAULT_NUMERO_CNI);

        // Get all the employeList where numeroCni does not contain UPDATED_NUMERO_CNI
        defaultEmployeShouldBeFound("numeroCni.doesNotContain=" + UPDATED_NUMERO_CNI);
    }


    @Test
    @Transactional
    public void getAllEmployesByNumeroIpresIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroIpres equals to DEFAULT_NUMERO_IPRES
        defaultEmployeShouldBeFound("numeroIpres.equals=" + DEFAULT_NUMERO_IPRES);

        // Get all the employeList where numeroIpres equals to UPDATED_NUMERO_IPRES
        defaultEmployeShouldNotBeFound("numeroIpres.equals=" + UPDATED_NUMERO_IPRES);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumeroIpresIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroIpres not equals to DEFAULT_NUMERO_IPRES
        defaultEmployeShouldNotBeFound("numeroIpres.notEquals=" + DEFAULT_NUMERO_IPRES);

        // Get all the employeList where numeroIpres not equals to UPDATED_NUMERO_IPRES
        defaultEmployeShouldBeFound("numeroIpres.notEquals=" + UPDATED_NUMERO_IPRES);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumeroIpresIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroIpres in DEFAULT_NUMERO_IPRES or UPDATED_NUMERO_IPRES
        defaultEmployeShouldBeFound("numeroIpres.in=" + DEFAULT_NUMERO_IPRES + "," + UPDATED_NUMERO_IPRES);

        // Get all the employeList where numeroIpres equals to UPDATED_NUMERO_IPRES
        defaultEmployeShouldNotBeFound("numeroIpres.in=" + UPDATED_NUMERO_IPRES);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumeroIpresIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroIpres is not null
        defaultEmployeShouldBeFound("numeroIpres.specified=true");

        // Get all the employeList where numeroIpres is null
        defaultEmployeShouldNotBeFound("numeroIpres.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployesByNumeroIpresContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroIpres contains DEFAULT_NUMERO_IPRES
        defaultEmployeShouldBeFound("numeroIpres.contains=" + DEFAULT_NUMERO_IPRES);

        // Get all the employeList where numeroIpres contains UPDATED_NUMERO_IPRES
        defaultEmployeShouldNotBeFound("numeroIpres.contains=" + UPDATED_NUMERO_IPRES);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumeroIpresNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroIpres does not contain DEFAULT_NUMERO_IPRES
        defaultEmployeShouldNotBeFound("numeroIpres.doesNotContain=" + DEFAULT_NUMERO_IPRES);

        // Get all the employeList where numeroIpres does not contain UPDATED_NUMERO_IPRES
        defaultEmployeShouldBeFound("numeroIpres.doesNotContain=" + UPDATED_NUMERO_IPRES);
    }


    @Test
    @Transactional
    public void getAllEmployesByNumeroCssIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroCss equals to DEFAULT_NUMERO_CSS
        defaultEmployeShouldBeFound("numeroCss.equals=" + DEFAULT_NUMERO_CSS);

        // Get all the employeList where numeroCss equals to UPDATED_NUMERO_CSS
        defaultEmployeShouldNotBeFound("numeroCss.equals=" + UPDATED_NUMERO_CSS);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumeroCssIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroCss not equals to DEFAULT_NUMERO_CSS
        defaultEmployeShouldNotBeFound("numeroCss.notEquals=" + DEFAULT_NUMERO_CSS);

        // Get all the employeList where numeroCss not equals to UPDATED_NUMERO_CSS
        defaultEmployeShouldBeFound("numeroCss.notEquals=" + UPDATED_NUMERO_CSS);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumeroCssIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroCss in DEFAULT_NUMERO_CSS or UPDATED_NUMERO_CSS
        defaultEmployeShouldBeFound("numeroCss.in=" + DEFAULT_NUMERO_CSS + "," + UPDATED_NUMERO_CSS);

        // Get all the employeList where numeroCss equals to UPDATED_NUMERO_CSS
        defaultEmployeShouldNotBeFound("numeroCss.in=" + UPDATED_NUMERO_CSS);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumeroCssIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroCss is not null
        defaultEmployeShouldBeFound("numeroCss.specified=true");

        // Get all the employeList where numeroCss is null
        defaultEmployeShouldNotBeFound("numeroCss.specified=false");
    }
                @Test
    @Transactional
    public void getAllEmployesByNumeroCssContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroCss contains DEFAULT_NUMERO_CSS
        defaultEmployeShouldBeFound("numeroCss.contains=" + DEFAULT_NUMERO_CSS);

        // Get all the employeList where numeroCss contains UPDATED_NUMERO_CSS
        defaultEmployeShouldNotBeFound("numeroCss.contains=" + UPDATED_NUMERO_CSS);
    }

    @Test
    @Transactional
    public void getAllEmployesByNumeroCssNotContainsSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where numeroCss does not contain DEFAULT_NUMERO_CSS
        defaultEmployeShouldNotBeFound("numeroCss.doesNotContain=" + DEFAULT_NUMERO_CSS);

        // Get all the employeList where numeroCss does not contain UPDATED_NUMERO_CSS
        defaultEmployeShouldBeFound("numeroCss.doesNotContain=" + UPDATED_NUMERO_CSS);
    }


    @Test
    @Transactional
    public void getAllEmployesByGroupeSanguinIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where groupeSanguin equals to DEFAULT_GROUPE_SANGUIN
        defaultEmployeShouldBeFound("groupeSanguin.equals=" + DEFAULT_GROUPE_SANGUIN);

        // Get all the employeList where groupeSanguin equals to UPDATED_GROUPE_SANGUIN
        defaultEmployeShouldNotBeFound("groupeSanguin.equals=" + UPDATED_GROUPE_SANGUIN);
    }

    @Test
    @Transactional
    public void getAllEmployesByGroupeSanguinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where groupeSanguin not equals to DEFAULT_GROUPE_SANGUIN
        defaultEmployeShouldNotBeFound("groupeSanguin.notEquals=" + DEFAULT_GROUPE_SANGUIN);

        // Get all the employeList where groupeSanguin not equals to UPDATED_GROUPE_SANGUIN
        defaultEmployeShouldBeFound("groupeSanguin.notEquals=" + UPDATED_GROUPE_SANGUIN);
    }

    @Test
    @Transactional
    public void getAllEmployesByGroupeSanguinIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where groupeSanguin in DEFAULT_GROUPE_SANGUIN or UPDATED_GROUPE_SANGUIN
        defaultEmployeShouldBeFound("groupeSanguin.in=" + DEFAULT_GROUPE_SANGUIN + "," + UPDATED_GROUPE_SANGUIN);

        // Get all the employeList where groupeSanguin equals to UPDATED_GROUPE_SANGUIN
        defaultEmployeShouldNotBeFound("groupeSanguin.in=" + UPDATED_GROUPE_SANGUIN);
    }

    @Test
    @Transactional
    public void getAllEmployesByGroupeSanguinIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where groupeSanguin is not null
        defaultEmployeShouldBeFound("groupeSanguin.specified=true");

        // Get all the employeList where groupeSanguin is null
        defaultEmployeShouldNotBeFound("groupeSanguin.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesBySituationMatrimonialIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where situationMatrimonial equals to DEFAULT_SITUATION_MATRIMONIAL
        defaultEmployeShouldBeFound("situationMatrimonial.equals=" + DEFAULT_SITUATION_MATRIMONIAL);

        // Get all the employeList where situationMatrimonial equals to UPDATED_SITUATION_MATRIMONIAL
        defaultEmployeShouldNotBeFound("situationMatrimonial.equals=" + UPDATED_SITUATION_MATRIMONIAL);
    }

    @Test
    @Transactional
    public void getAllEmployesBySituationMatrimonialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where situationMatrimonial not equals to DEFAULT_SITUATION_MATRIMONIAL
        defaultEmployeShouldNotBeFound("situationMatrimonial.notEquals=" + DEFAULT_SITUATION_MATRIMONIAL);

        // Get all the employeList where situationMatrimonial not equals to UPDATED_SITUATION_MATRIMONIAL
        defaultEmployeShouldBeFound("situationMatrimonial.notEquals=" + UPDATED_SITUATION_MATRIMONIAL);
    }

    @Test
    @Transactional
    public void getAllEmployesBySituationMatrimonialIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where situationMatrimonial in DEFAULT_SITUATION_MATRIMONIAL or UPDATED_SITUATION_MATRIMONIAL
        defaultEmployeShouldBeFound("situationMatrimonial.in=" + DEFAULT_SITUATION_MATRIMONIAL + "," + UPDATED_SITUATION_MATRIMONIAL);

        // Get all the employeList where situationMatrimonial equals to UPDATED_SITUATION_MATRIMONIAL
        defaultEmployeShouldNotBeFound("situationMatrimonial.in=" + UPDATED_SITUATION_MATRIMONIAL);
    }

    @Test
    @Transactional
    public void getAllEmployesBySituationMatrimonialIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where situationMatrimonial is not null
        defaultEmployeShouldBeFound("situationMatrimonial.specified=true");

        // Get all the employeList where situationMatrimonial is null
        defaultEmployeShouldNotBeFound("situationMatrimonial.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesBySexeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where sexe equals to DEFAULT_SEXE
        defaultEmployeShouldBeFound("sexe.equals=" + DEFAULT_SEXE);

        // Get all the employeList where sexe equals to UPDATED_SEXE
        defaultEmployeShouldNotBeFound("sexe.equals=" + UPDATED_SEXE);
    }

    @Test
    @Transactional
    public void getAllEmployesBySexeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where sexe not equals to DEFAULT_SEXE
        defaultEmployeShouldNotBeFound("sexe.notEquals=" + DEFAULT_SEXE);

        // Get all the employeList where sexe not equals to UPDATED_SEXE
        defaultEmployeShouldBeFound("sexe.notEquals=" + UPDATED_SEXE);
    }

    @Test
    @Transactional
    public void getAllEmployesBySexeIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where sexe in DEFAULT_SEXE or UPDATED_SEXE
        defaultEmployeShouldBeFound("sexe.in=" + DEFAULT_SEXE + "," + UPDATED_SEXE);

        // Get all the employeList where sexe equals to UPDATED_SEXE
        defaultEmployeShouldNotBeFound("sexe.in=" + UPDATED_SEXE);
    }

    @Test
    @Transactional
    public void getAllEmployesBySexeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where sexe is not null
        defaultEmployeShouldBeFound("sexe.specified=true");

        // Get all the employeList where sexe is null
        defaultEmployeShouldNotBeFound("sexe.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesByDisponibiliteIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where disponibilite equals to DEFAULT_DISPONIBILITE
        defaultEmployeShouldBeFound("disponibilite.equals=" + DEFAULT_DISPONIBILITE);

        // Get all the employeList where disponibilite equals to UPDATED_DISPONIBILITE
        defaultEmployeShouldNotBeFound("disponibilite.equals=" + UPDATED_DISPONIBILITE);
    }

    @Test
    @Transactional
    public void getAllEmployesByDisponibiliteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where disponibilite not equals to DEFAULT_DISPONIBILITE
        defaultEmployeShouldNotBeFound("disponibilite.notEquals=" + DEFAULT_DISPONIBILITE);

        // Get all the employeList where disponibilite not equals to UPDATED_DISPONIBILITE
        defaultEmployeShouldBeFound("disponibilite.notEquals=" + UPDATED_DISPONIBILITE);
    }

    @Test
    @Transactional
    public void getAllEmployesByDisponibiliteIsInShouldWork() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where disponibilite in DEFAULT_DISPONIBILITE or UPDATED_DISPONIBILITE
        defaultEmployeShouldBeFound("disponibilite.in=" + DEFAULT_DISPONIBILITE + "," + UPDATED_DISPONIBILITE);

        // Get all the employeList where disponibilite equals to UPDATED_DISPONIBILITE
        defaultEmployeShouldNotBeFound("disponibilite.in=" + UPDATED_DISPONIBILITE);
    }

    @Test
    @Transactional
    public void getAllEmployesByDisponibiliteIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList where disponibilite is not null
        defaultEmployeShouldBeFound("disponibilite.specified=true");

        // Get all the employeList where disponibilite is null
        defaultEmployeShouldNotBeFound("disponibilite.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployesByContratIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);
        Contrat contrat = ContratResourceIT.createEntity(em);
        em.persist(contrat);
        em.flush();
        employe.setContrat(contrat);
        employeRepository.saveAndFlush(employe);
        Long contratId = contrat.getId();

        // Get all the employeList where contrat equals to contratId
        defaultEmployeShouldBeFound("contratId.equals=" + contratId);

        // Get all the employeList where contrat equals to contratId + 1
        defaultEmployeShouldNotBeFound("contratId.equals=" + (contratId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployesByDiplomeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);
        Diplome diplome = DiplomeResourceIT.createEntity(em);
        em.persist(diplome);
        em.flush();
        employe.addDiplome(diplome);
        employeRepository.saveAndFlush(employe);
        Long diplomeId = diplome.getId();

        // Get all the employeList where diplome equals to diplomeId
        defaultEmployeShouldBeFound("diplomeId.equals=" + diplomeId);

        // Get all the employeList where diplome equals to diplomeId + 1
        defaultEmployeShouldNotBeFound("diplomeId.equals=" + (diplomeId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployesByFonctionIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);
        Fonction fonction = FonctionResourceIT.createEntity(em);
        em.persist(fonction);
        em.flush();
        employe.addFonction(fonction);
        employeRepository.saveAndFlush(employe);
        Long fonctionId = fonction.getId();

        // Get all the employeList where fonction equals to fonctionId
        defaultEmployeShouldBeFound("fonctionId.equals=" + fonctionId);

        // Get all the employeList where fonction equals to fonctionId + 1
        defaultEmployeShouldNotBeFound("fonctionId.equals=" + (fonctionId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployesByMembreFamilleIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);
        MembreFamille membreFamille = MembreFamilleResourceIT.createEntity(em);
        em.persist(membreFamille);
        em.flush();
        employe.addMembreFamille(membreFamille);
        employeRepository.saveAndFlush(employe);
        Long membreFamilleId = membreFamille.getId();

        // Get all the employeList where membreFamille equals to membreFamilleId
        defaultEmployeShouldBeFound("membreFamilleId.equals=" + membreFamilleId);

        // Get all the employeList where membreFamille equals to membreFamilleId + 1
        defaultEmployeShouldNotBeFound("membreFamilleId.equals=" + (membreFamilleId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployesByDistinctionIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);
        Distinction distinction = DistinctionResourceIT.createEntity(em);
        em.persist(distinction);
        em.flush();
        employe.addDistinction(distinction);
        employeRepository.saveAndFlush(employe);
        Long distinctionId = distinction.getId();

        // Get all the employeList where distinction equals to distinctionId
        defaultEmployeShouldBeFound("distinctionId.equals=" + distinctionId);

        // Get all the employeList where distinction equals to distinctionId + 1
        defaultEmployeShouldNotBeFound("distinctionId.equals=" + (distinctionId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployesByOperationExterieurIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);
        OperationExterieur operationExterieur = OperationExterieurResourceIT.createEntity(em);
        em.persist(operationExterieur);
        em.flush();
        employe.addOperationExterieur(operationExterieur);
        employeRepository.saveAndFlush(employe);
        Long operationExterieurId = operationExterieur.getId();

        // Get all the employeList where operationExterieur equals to operationExterieurId
        defaultEmployeShouldBeFound("operationExterieurId.equals=" + operationExterieurId);

        // Get all the employeList where operationExterieur equals to operationExterieurId + 1
        defaultEmployeShouldNotBeFound("operationExterieurId.equals=" + (operationExterieurId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployesByServiceIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);
        ServiceAffecte service = ServiceAffecteResourceIT.createEntity(em);
        em.persist(service);
        em.flush();
        employe.addService(service);
        employeRepository.saveAndFlush(employe);
        Long serviceId = service.getId();

        // Get all the employeList where service equals to serviceId
        defaultEmployeShouldBeFound("serviceId.equals=" + serviceId);

        // Get all the employeList where service equals to serviceId + 1
        defaultEmployeShouldNotBeFound("serviceId.equals=" + (serviceId + 1));
    }


    @Test
    @Transactional
    public void getAllEmployesByServiceFrequenteIsEqualToSomething() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);
        ServiceFrequente serviceFrequente = ServiceFrequenteResourceIT.createEntity(em);
        em.persist(serviceFrequente);
        em.flush();
        employe.addServiceFrequente(serviceFrequente);
        employeRepository.saveAndFlush(employe);
        Long serviceFrequenteId = serviceFrequente.getId();

        // Get all the employeList where serviceFrequente equals to serviceFrequenteId
        defaultEmployeShouldBeFound("serviceFrequenteId.equals=" + serviceFrequenteId);

        // Get all the employeList where serviceFrequente equals to serviceFrequenteId + 1
        defaultEmployeShouldNotBeFound("serviceFrequenteId.equals=" + (serviceFrequenteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeShouldBeFound(String filter) throws Exception {
        restEmployeMockMvc.perform(get("/api/employes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employe.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].prenoms").value(hasItem(DEFAULT_PRENOMS)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].intituleEmploye").value(hasItem(DEFAULT_INTITULE_EMPLOYE)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaissance").value(hasItem(DEFAULT_LIEU_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].numeroTelephone").value(hasItem(DEFAULT_NUMERO_TELEPHONE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dateEmbauchement").value(hasItem(DEFAULT_DATE_EMBAUCHEMENT.toString())))
            .andExpect(jsonPath("$.[*].dateRetraite").value(hasItem(DEFAULT_DATE_RETRAITE.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.intValue())))
            .andExpect(jsonPath("$.[*].numeroCni").value(hasItem(DEFAULT_NUMERO_CNI)))
            .andExpect(jsonPath("$.[*].numeroIpres").value(hasItem(DEFAULT_NUMERO_IPRES)))
            .andExpect(jsonPath("$.[*].numeroCss").value(hasItem(DEFAULT_NUMERO_CSS)))
            .andExpect(jsonPath("$.[*].groupeSanguin").value(hasItem(DEFAULT_GROUPE_SANGUIN.toString())))
            .andExpect(jsonPath("$.[*].situationMatrimonial").value(hasItem(DEFAULT_SITUATION_MATRIMONIAL.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].disponibilite").value(hasItem(DEFAULT_DISPONIBILITE.toString())));

        // Check, that the count call also returns 1
        restEmployeMockMvc.perform(get("/api/employes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeShouldNotBeFound(String filter) throws Exception {
        restEmployeMockMvc.perform(get("/api/employes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeMockMvc.perform(get("/api/employes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEmploye() throws Exception {
        // Get the employe
        restEmployeMockMvc.perform(get("/api/employes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmploye() throws Exception {
        // Initialize the database
        employeService.save(employe);

        int databaseSizeBeforeUpdate = employeRepository.findAll().size();

        // Update the employe
        Employe updatedEmploye = employeRepository.findById(employe.getId()).get();
        // Disconnect from session so that the updates on updatedEmploye are not directly saved in db
        em.detach(updatedEmploye);
        updatedEmploye
            .matricule(UPDATED_MATRICULE)
            .prenoms(UPDATED_PRENOMS)
            .nom(UPDATED_NOM)
            .intituleEmploye(UPDATED_INTITULE_EMPLOYE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .numeroTelephone(UPDATED_NUMERO_TELEPHONE)
            .adresse(UPDATED_ADRESSE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .email(UPDATED_EMAIL)
            .dateEmbauchement(UPDATED_DATE_EMBAUCHEMENT)
            .dateRetraite(UPDATED_DATE_RETRAITE)
            .age(UPDATED_AGE)
            .numeroCni(UPDATED_NUMERO_CNI)
            .numeroIpres(UPDATED_NUMERO_IPRES)
            .numeroCss(UPDATED_NUMERO_CSS)
            .groupeSanguin(UPDATED_GROUPE_SANGUIN)
            .situationMatrimonial(UPDATED_SITUATION_MATRIMONIAL)
            .sexe(UPDATED_SEXE)
            .disponibilite(UPDATED_DISPONIBILITE);

        restEmployeMockMvc.perform(put("/api/employes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmploye)))
            .andExpect(status().isOk());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
        Employe testEmploye = employeList.get(employeList.size() - 1);
        assertThat(testEmploye.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEmploye.getPrenoms()).isEqualTo(UPDATED_PRENOMS);
        assertThat(testEmploye.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testEmploye.getIntituleEmploye()).isEqualTo(UPDATED_INTITULE_EMPLOYE);
        assertThat(testEmploye.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testEmploye.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testEmploye.getNumeroTelephone()).isEqualTo(UPDATED_NUMERO_TELEPHONE);
        assertThat(testEmploye.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testEmploye.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testEmploye.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testEmploye.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmploye.getDateEmbauchement()).isEqualTo(UPDATED_DATE_EMBAUCHEMENT);
        assertThat(testEmploye.getDateRetraite()).isEqualTo(UPDATED_DATE_RETRAITE);
        assertThat(testEmploye.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testEmploye.getNumeroCni()).isEqualTo(UPDATED_NUMERO_CNI);
        assertThat(testEmploye.getNumeroIpres()).isEqualTo(UPDATED_NUMERO_IPRES);
        assertThat(testEmploye.getNumeroCss()).isEqualTo(UPDATED_NUMERO_CSS);
        assertThat(testEmploye.getGroupeSanguin()).isEqualTo(UPDATED_GROUPE_SANGUIN);
        assertThat(testEmploye.getSituationMatrimonial()).isEqualTo(UPDATED_SITUATION_MATRIMONIAL);
        assertThat(testEmploye.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testEmploye.getDisponibilite()).isEqualTo(UPDATED_DISPONIBILITE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeMockMvc.perform(put("/api/employes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(employe)))
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmploye() throws Exception {
        // Initialize the database
        employeService.save(employe);

        int databaseSizeBeforeDelete = employeRepository.findAll().size();

        // Delete the employe
        restEmployeMockMvc.perform(delete("/api/employes/{id}", employe.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
