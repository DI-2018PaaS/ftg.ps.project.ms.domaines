package ftg.ps.project.ms.domaines.web.rest;

import ftg.ps.project.ms.domaines.McsDomainesApp;

import ftg.ps.project.ms.domaines.config.SecurityBeanOverrideConfiguration;

import ftg.ps.project.ms.domaines.domain.ActeurDomaine;
import ftg.ps.project.ms.domaines.repository.ActeurDomaineRepository;
import ftg.ps.project.ms.domaines.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static ftg.ps.project.ms.domaines.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ActeurDomaineResource REST controller.
 *
 * @see ActeurDomaineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, McsDomainesApp.class})
public class ActeurDomaineResourceIntTest {

    private static final Long DEFAULT_ID_ACTEUR = 1L;
    private static final Long UPDATED_ID_ACTEUR = 2L;

    @Autowired
    private ActeurDomaineRepository acteurDomaineRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActeurDomaineMockMvc;

    private ActeurDomaine acteurDomaine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActeurDomaineResource acteurDomaineResource = new ActeurDomaineResource(acteurDomaineRepository);
        this.restActeurDomaineMockMvc = MockMvcBuilders.standaloneSetup(acteurDomaineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ActeurDomaine createEntity(EntityManager em) {
        ActeurDomaine acteurDomaine = new ActeurDomaine()
            .idActeur(DEFAULT_ID_ACTEUR);
        return acteurDomaine;
    }

    @Before
    public void initTest() {
        acteurDomaine = createEntity(em);
    }

    @Test
    @Transactional
    public void createActeurDomaine() throws Exception {
        int databaseSizeBeforeCreate = acteurDomaineRepository.findAll().size();

        // Create the ActeurDomaine
        restActeurDomaineMockMvc.perform(post("/api/acteur-domaines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteurDomaine)))
            .andExpect(status().isCreated());

        // Validate the ActeurDomaine in the database
        List<ActeurDomaine> acteurDomaineList = acteurDomaineRepository.findAll();
        assertThat(acteurDomaineList).hasSize(databaseSizeBeforeCreate + 1);
        ActeurDomaine testActeurDomaine = acteurDomaineList.get(acteurDomaineList.size() - 1);
        assertThat(testActeurDomaine.getIdActeur()).isEqualTo(DEFAULT_ID_ACTEUR);
    }

    @Test
    @Transactional
    public void createActeurDomaineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acteurDomaineRepository.findAll().size();

        // Create the ActeurDomaine with an existing ID
        acteurDomaine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActeurDomaineMockMvc.perform(post("/api/acteur-domaines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteurDomaine)))
            .andExpect(status().isBadRequest());

        // Validate the ActeurDomaine in the database
        List<ActeurDomaine> acteurDomaineList = acteurDomaineRepository.findAll();
        assertThat(acteurDomaineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActeurDomaines() throws Exception {
        // Initialize the database
        acteurDomaineRepository.saveAndFlush(acteurDomaine);

        // Get all the acteurDomaineList
        restActeurDomaineMockMvc.perform(get("/api/acteur-domaines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acteurDomaine.getId().intValue())))
            .andExpect(jsonPath("$.[*].idActeur").value(hasItem(DEFAULT_ID_ACTEUR.intValue())));
    }
    

    @Test
    @Transactional
    public void getActeurDomaine() throws Exception {
        // Initialize the database
        acteurDomaineRepository.saveAndFlush(acteurDomaine);

        // Get the acteurDomaine
        restActeurDomaineMockMvc.perform(get("/api/acteur-domaines/{id}", acteurDomaine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(acteurDomaine.getId().intValue()))
            .andExpect(jsonPath("$.idActeur").value(DEFAULT_ID_ACTEUR.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingActeurDomaine() throws Exception {
        // Get the acteurDomaine
        restActeurDomaineMockMvc.perform(get("/api/acteur-domaines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActeurDomaine() throws Exception {
        // Initialize the database
        acteurDomaineRepository.saveAndFlush(acteurDomaine);

        int databaseSizeBeforeUpdate = acteurDomaineRepository.findAll().size();

        // Update the acteurDomaine
        ActeurDomaine updatedActeurDomaine = acteurDomaineRepository.findById(acteurDomaine.getId()).get();
        // Disconnect from session so that the updates on updatedActeurDomaine are not directly saved in db
        em.detach(updatedActeurDomaine);
        updatedActeurDomaine
            .idActeur(UPDATED_ID_ACTEUR);

        restActeurDomaineMockMvc.perform(put("/api/acteur-domaines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActeurDomaine)))
            .andExpect(status().isOk());

        // Validate the ActeurDomaine in the database
        List<ActeurDomaine> acteurDomaineList = acteurDomaineRepository.findAll();
        assertThat(acteurDomaineList).hasSize(databaseSizeBeforeUpdate);
        ActeurDomaine testActeurDomaine = acteurDomaineList.get(acteurDomaineList.size() - 1);
        assertThat(testActeurDomaine.getIdActeur()).isEqualTo(UPDATED_ID_ACTEUR);
    }

    @Test
    @Transactional
    public void updateNonExistingActeurDomaine() throws Exception {
        int databaseSizeBeforeUpdate = acteurDomaineRepository.findAll().size();

        // Create the ActeurDomaine

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActeurDomaineMockMvc.perform(put("/api/acteur-domaines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acteurDomaine)))
            .andExpect(status().isBadRequest());

        // Validate the ActeurDomaine in the database
        List<ActeurDomaine> acteurDomaineList = acteurDomaineRepository.findAll();
        assertThat(acteurDomaineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActeurDomaine() throws Exception {
        // Initialize the database
        acteurDomaineRepository.saveAndFlush(acteurDomaine);

        int databaseSizeBeforeDelete = acteurDomaineRepository.findAll().size();

        // Get the acteurDomaine
        restActeurDomaineMockMvc.perform(delete("/api/acteur-domaines/{id}", acteurDomaine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ActeurDomaine> acteurDomaineList = acteurDomaineRepository.findAll();
        assertThat(acteurDomaineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActeurDomaine.class);
        ActeurDomaine acteurDomaine1 = new ActeurDomaine();
        acteurDomaine1.setId(1L);
        ActeurDomaine acteurDomaine2 = new ActeurDomaine();
        acteurDomaine2.setId(acteurDomaine1.getId());
        assertThat(acteurDomaine1).isEqualTo(acteurDomaine2);
        acteurDomaine2.setId(2L);
        assertThat(acteurDomaine1).isNotEqualTo(acteurDomaine2);
        acteurDomaine1.setId(null);
        assertThat(acteurDomaine1).isNotEqualTo(acteurDomaine2);
    }
}
