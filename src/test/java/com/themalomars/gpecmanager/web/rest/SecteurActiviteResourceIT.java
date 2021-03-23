package com.themalomars.gpecmanager.web.rest;

import com.themalomars.gpecmanager.GpecmanagerApp;
import com.themalomars.gpecmanager.domain.SecteurActivite;
import com.themalomars.gpecmanager.repository.SecteurActiviteRepository;
import com.themalomars.gpecmanager.service.SecteurActiviteService;
import com.themalomars.gpecmanager.service.dto.SecteurActiviteCriteria;
import com.themalomars.gpecmanager.service.SecteurActiviteQueryService;

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
 * Integration tests for the {@link SecteurActiviteResource} REST controller.
 */
@SpringBootTest(classes = GpecmanagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SecteurActiviteResourceIT {

    private static final String DEFAULT_LIBELLE_ACTIVITE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_ACTIVITE = "BBBBBBBBBB";

    @Autowired
    private SecteurActiviteRepository secteurActiviteRepository;

    @Autowired
    private SecteurActiviteService secteurActiviteService;

    @Autowired
    private SecteurActiviteQueryService secteurActiviteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSecteurActiviteMockMvc;

    private SecteurActivite secteurActivite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecteurActivite createEntity(EntityManager em) {
        SecteurActivite secteurActivite = new SecteurActivite()
            .libelleActivite(DEFAULT_LIBELLE_ACTIVITE);
        return secteurActivite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SecteurActivite createUpdatedEntity(EntityManager em) {
        SecteurActivite secteurActivite = new SecteurActivite()
            .libelleActivite(UPDATED_LIBELLE_ACTIVITE);
        return secteurActivite;
    }

    @BeforeEach
    public void initTest() {
        secteurActivite = createEntity(em);
    }

    @Test
    @Transactional
    public void createSecteurActivite() throws Exception {
        int databaseSizeBeforeCreate = secteurActiviteRepository.findAll().size();
        // Create the SecteurActivite
        restSecteurActiviteMockMvc.perform(post("/api/secteur-activites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(secteurActivite)))
            .andExpect(status().isCreated());

        // Validate the SecteurActivite in the database
        List<SecteurActivite> secteurActiviteList = secteurActiviteRepository.findAll();
        assertThat(secteurActiviteList).hasSize(databaseSizeBeforeCreate + 1);
        SecteurActivite testSecteurActivite = secteurActiviteList.get(secteurActiviteList.size() - 1);
        assertThat(testSecteurActivite.getLibelleActivite()).isEqualTo(DEFAULT_LIBELLE_ACTIVITE);
    }

    @Test
    @Transactional
    public void createSecteurActiviteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = secteurActiviteRepository.findAll().size();

        // Create the SecteurActivite with an existing ID
        secteurActivite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecteurActiviteMockMvc.perform(post("/api/secteur-activites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(secteurActivite)))
            .andExpect(status().isBadRequest());

        // Validate the SecteurActivite in the database
        List<SecteurActivite> secteurActiviteList = secteurActiviteRepository.findAll();
        assertThat(secteurActiviteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLibelleActiviteIsRequired() throws Exception {
        int databaseSizeBeforeTest = secteurActiviteRepository.findAll().size();
        // set the field null
        secteurActivite.setLibelleActivite(null);

        // Create the SecteurActivite, which fails.


        restSecteurActiviteMockMvc.perform(post("/api/secteur-activites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(secteurActivite)))
            .andExpect(status().isBadRequest());

        List<SecteurActivite> secteurActiviteList = secteurActiviteRepository.findAll();
        assertThat(secteurActiviteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSecteurActivites() throws Exception {
        // Initialize the database
        secteurActiviteRepository.saveAndFlush(secteurActivite);

        // Get all the secteurActiviteList
        restSecteurActiviteMockMvc.perform(get("/api/secteur-activites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(secteurActivite.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleActivite").value(hasItem(DEFAULT_LIBELLE_ACTIVITE)));
    }
    
    @Test
    @Transactional
    public void getSecteurActivite() throws Exception {
        // Initialize the database
        secteurActiviteRepository.saveAndFlush(secteurActivite);

        // Get the secteurActivite
        restSecteurActiviteMockMvc.perform(get("/api/secteur-activites/{id}", secteurActivite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(secteurActivite.getId().intValue()))
            .andExpect(jsonPath("$.libelleActivite").value(DEFAULT_LIBELLE_ACTIVITE));
    }


    @Test
    @Transactional
    public void getSecteurActivitesByIdFiltering() throws Exception {
        // Initialize the database
        secteurActiviteRepository.saveAndFlush(secteurActivite);

        Long id = secteurActivite.getId();

        defaultSecteurActiviteShouldBeFound("id.equals=" + id);
        defaultSecteurActiviteShouldNotBeFound("id.notEquals=" + id);

        defaultSecteurActiviteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSecteurActiviteShouldNotBeFound("id.greaterThan=" + id);

        defaultSecteurActiviteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSecteurActiviteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSecteurActivitesByLibelleActiviteIsEqualToSomething() throws Exception {
        // Initialize the database
        secteurActiviteRepository.saveAndFlush(secteurActivite);

        // Get all the secteurActiviteList where libelleActivite equals to DEFAULT_LIBELLE_ACTIVITE
        defaultSecteurActiviteShouldBeFound("libelleActivite.equals=" + DEFAULT_LIBELLE_ACTIVITE);

        // Get all the secteurActiviteList where libelleActivite equals to UPDATED_LIBELLE_ACTIVITE
        defaultSecteurActiviteShouldNotBeFound("libelleActivite.equals=" + UPDATED_LIBELLE_ACTIVITE);
    }

    @Test
    @Transactional
    public void getAllSecteurActivitesByLibelleActiviteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        secteurActiviteRepository.saveAndFlush(secteurActivite);

        // Get all the secteurActiviteList where libelleActivite not equals to DEFAULT_LIBELLE_ACTIVITE
        defaultSecteurActiviteShouldNotBeFound("libelleActivite.notEquals=" + DEFAULT_LIBELLE_ACTIVITE);

        // Get all the secteurActiviteList where libelleActivite not equals to UPDATED_LIBELLE_ACTIVITE
        defaultSecteurActiviteShouldBeFound("libelleActivite.notEquals=" + UPDATED_LIBELLE_ACTIVITE);
    }

    @Test
    @Transactional
    public void getAllSecteurActivitesByLibelleActiviteIsInShouldWork() throws Exception {
        // Initialize the database
        secteurActiviteRepository.saveAndFlush(secteurActivite);

        // Get all the secteurActiviteList where libelleActivite in DEFAULT_LIBELLE_ACTIVITE or UPDATED_LIBELLE_ACTIVITE
        defaultSecteurActiviteShouldBeFound("libelleActivite.in=" + DEFAULT_LIBELLE_ACTIVITE + "," + UPDATED_LIBELLE_ACTIVITE);

        // Get all the secteurActiviteList where libelleActivite equals to UPDATED_LIBELLE_ACTIVITE
        defaultSecteurActiviteShouldNotBeFound("libelleActivite.in=" + UPDATED_LIBELLE_ACTIVITE);
    }

    @Test
    @Transactional
    public void getAllSecteurActivitesByLibelleActiviteIsNullOrNotNull() throws Exception {
        // Initialize the database
        secteurActiviteRepository.saveAndFlush(secteurActivite);

        // Get all the secteurActiviteList where libelleActivite is not null
        defaultSecteurActiviteShouldBeFound("libelleActivite.specified=true");

        // Get all the secteurActiviteList where libelleActivite is null
        defaultSecteurActiviteShouldNotBeFound("libelleActivite.specified=false");
    }
                @Test
    @Transactional
    public void getAllSecteurActivitesByLibelleActiviteContainsSomething() throws Exception {
        // Initialize the database
        secteurActiviteRepository.saveAndFlush(secteurActivite);

        // Get all the secteurActiviteList where libelleActivite contains DEFAULT_LIBELLE_ACTIVITE
        defaultSecteurActiviteShouldBeFound("libelleActivite.contains=" + DEFAULT_LIBELLE_ACTIVITE);

        // Get all the secteurActiviteList where libelleActivite contains UPDATED_LIBELLE_ACTIVITE
        defaultSecteurActiviteShouldNotBeFound("libelleActivite.contains=" + UPDATED_LIBELLE_ACTIVITE);
    }

    @Test
    @Transactional
    public void getAllSecteurActivitesByLibelleActiviteNotContainsSomething() throws Exception {
        // Initialize the database
        secteurActiviteRepository.saveAndFlush(secteurActivite);

        // Get all the secteurActiviteList where libelleActivite does not contain DEFAULT_LIBELLE_ACTIVITE
        defaultSecteurActiviteShouldNotBeFound("libelleActivite.doesNotContain=" + DEFAULT_LIBELLE_ACTIVITE);

        // Get all the secteurActiviteList where libelleActivite does not contain UPDATED_LIBELLE_ACTIVITE
        defaultSecteurActiviteShouldBeFound("libelleActivite.doesNotContain=" + UPDATED_LIBELLE_ACTIVITE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSecteurActiviteShouldBeFound(String filter) throws Exception {
        restSecteurActiviteMockMvc.perform(get("/api/secteur-activites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(secteurActivite.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelleActivite").value(hasItem(DEFAULT_LIBELLE_ACTIVITE)));

        // Check, that the count call also returns 1
        restSecteurActiviteMockMvc.perform(get("/api/secteur-activites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSecteurActiviteShouldNotBeFound(String filter) throws Exception {
        restSecteurActiviteMockMvc.perform(get("/api/secteur-activites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSecteurActiviteMockMvc.perform(get("/api/secteur-activites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSecteurActivite() throws Exception {
        // Get the secteurActivite
        restSecteurActiviteMockMvc.perform(get("/api/secteur-activites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSecteurActivite() throws Exception {
        // Initialize the database
        secteurActiviteService.save(secteurActivite);

        int databaseSizeBeforeUpdate = secteurActiviteRepository.findAll().size();

        // Update the secteurActivite
        SecteurActivite updatedSecteurActivite = secteurActiviteRepository.findById(secteurActivite.getId()).get();
        // Disconnect from session so that the updates on updatedSecteurActivite are not directly saved in db
        em.detach(updatedSecteurActivite);
        updatedSecteurActivite
            .libelleActivite(UPDATED_LIBELLE_ACTIVITE);

        restSecteurActiviteMockMvc.perform(put("/api/secteur-activites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSecteurActivite)))
            .andExpect(status().isOk());

        // Validate the SecteurActivite in the database
        List<SecteurActivite> secteurActiviteList = secteurActiviteRepository.findAll();
        assertThat(secteurActiviteList).hasSize(databaseSizeBeforeUpdate);
        SecteurActivite testSecteurActivite = secteurActiviteList.get(secteurActiviteList.size() - 1);
        assertThat(testSecteurActivite.getLibelleActivite()).isEqualTo(UPDATED_LIBELLE_ACTIVITE);
    }

    @Test
    @Transactional
    public void updateNonExistingSecteurActivite() throws Exception {
        int databaseSizeBeforeUpdate = secteurActiviteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSecteurActiviteMockMvc.perform(put("/api/secteur-activites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(secteurActivite)))
            .andExpect(status().isBadRequest());

        // Validate the SecteurActivite in the database
        List<SecteurActivite> secteurActiviteList = secteurActiviteRepository.findAll();
        assertThat(secteurActiviteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSecteurActivite() throws Exception {
        // Initialize the database
        secteurActiviteService.save(secteurActivite);

        int databaseSizeBeforeDelete = secteurActiviteRepository.findAll().size();

        // Delete the secteurActivite
        restSecteurActiviteMockMvc.perform(delete("/api/secteur-activites/{id}", secteurActivite.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SecteurActivite> secteurActiviteList = secteurActiviteRepository.findAll();
        assertThat(secteurActiviteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
