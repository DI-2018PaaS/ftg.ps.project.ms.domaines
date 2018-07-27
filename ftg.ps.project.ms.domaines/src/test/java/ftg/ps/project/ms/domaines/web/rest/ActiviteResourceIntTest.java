package ftg.ps.project.ms.domaines.web.rest;

import ftg.ps.project.ms.domaines.McsDomainesApp;

import ftg.ps.project.ms.domaines.config.SecurityBeanOverrideConfiguration;

import ftg.ps.project.ms.domaines.domain.Activite;
import ftg.ps.project.ms.domaines.repository.ActiviteRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static ftg.ps.project.ms.domaines.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ActiviteResource REST controller.
 *
 * @see ActiviteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, McsDomainesApp.class})
public class ActiviteResourceIntTest {

    private static final Long DEFAULT_ACTIVITE_ID = 1L;
    private static final Long UPDATED_ACTIVITE_ID = 2L;

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_LAST_MODIF = 1L;
    private static final Long UPDATED_USER_LAST_MODIF = 2L;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_LAST_MODIF = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_LAST_MODIF = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ActiviteRepository activiteRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restActiviteMockMvc;

    private Activite activite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ActiviteResource activiteResource = new ActiviteResource(activiteRepository);
        this.restActiviteMockMvc = MockMvcBuilders.standaloneSetup(activiteResource)
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
    public static Activite createEntity(EntityManager em) {
        Activite activite = new Activite()
            .activiteId(DEFAULT_ACTIVITE_ID)
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .categorie(DEFAULT_CATEGORIE)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .status(DEFAULT_STATUS)
            .userCreated(DEFAULT_USER_CREATED)
            .userLastModif(DEFAULT_USER_LAST_MODIF)
            .createdDate(DEFAULT_CREATED_DATE)
            .dateLastModif(DEFAULT_DATE_LAST_MODIF);
        return activite;
    }

    @Before
    public void initTest() {
        activite = createEntity(em);
    }

    @Test
    @Transactional
    public void createActivite() throws Exception {
        int databaseSizeBeforeCreate = activiteRepository.findAll().size();

        // Create the Activite
        restActiviteMockMvc.perform(post("/api/activites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activite)))
            .andExpect(status().isCreated());

        // Validate the Activite in the database
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeCreate + 1);
        Activite testActivite = activiteList.get(activiteList.size() - 1);
        assertThat(testActivite.getActiviteId()).isEqualTo(DEFAULT_ACTIVITE_ID);
        assertThat(testActivite.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testActivite.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testActivite.getCategorie()).isEqualTo(DEFAULT_CATEGORIE);
        assertThat(testActivite.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testActivite.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testActivite.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testActivite.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testActivite.getUserLastModif()).isEqualTo(DEFAULT_USER_LAST_MODIF);
        assertThat(testActivite.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testActivite.getDateLastModif()).isEqualTo(DEFAULT_DATE_LAST_MODIF);
    }

    @Test
    @Transactional
    public void createActiviteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = activiteRepository.findAll().size();

        // Create the Activite with an existing ID
        activite.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restActiviteMockMvc.perform(post("/api/activites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activite)))
            .andExpect(status().isBadRequest());

        // Validate the Activite in the database
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllActivites() throws Exception {
        // Initialize the database
        activiteRepository.saveAndFlush(activite);

        // Get all the activiteList
        restActiviteMockMvc.perform(get("/api/activites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(activite.getId().intValue())))
            .andExpect(jsonPath("$.[*].activiteId").value(hasItem(DEFAULT_ACTIVITE_ID.intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE.toString())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userLastModif").value(hasItem(DEFAULT_USER_LAST_MODIF.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].dateLastModif").value(hasItem(DEFAULT_DATE_LAST_MODIF.toString())));
    }
    

    @Test
    @Transactional
    public void getActivite() throws Exception {
        // Initialize the database
        activiteRepository.saveAndFlush(activite);

        // Get the activite
        restActiviteMockMvc.perform(get("/api/activites/{id}", activite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(activite.getId().intValue()))
            .andExpect(jsonPath("$.activiteId").value(DEFAULT_ACTIVITE_ID.intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE.toString()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userLastModif").value(DEFAULT_USER_LAST_MODIF.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.dateLastModif").value(DEFAULT_DATE_LAST_MODIF.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingActivite() throws Exception {
        // Get the activite
        restActiviteMockMvc.perform(get("/api/activites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateActivite() throws Exception {
        // Initialize the database
        activiteRepository.saveAndFlush(activite);

        int databaseSizeBeforeUpdate = activiteRepository.findAll().size();

        // Update the activite
        Activite updatedActivite = activiteRepository.findById(activite.getId()).get();
        // Disconnect from session so that the updates on updatedActivite are not directly saved in db
        em.detach(updatedActivite);
        updatedActivite
            .activiteId(UPDATED_ACTIVITE_ID)
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .categorie(UPDATED_CATEGORIE)
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .status(UPDATED_STATUS)
            .userCreated(UPDATED_USER_CREATED)
            .userLastModif(UPDATED_USER_LAST_MODIF)
            .createdDate(UPDATED_CREATED_DATE)
            .dateLastModif(UPDATED_DATE_LAST_MODIF);

        restActiviteMockMvc.perform(put("/api/activites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedActivite)))
            .andExpect(status().isOk());

        // Validate the Activite in the database
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeUpdate);
        Activite testActivite = activiteList.get(activiteList.size() - 1);
        assertThat(testActivite.getActiviteId()).isEqualTo(UPDATED_ACTIVITE_ID);
        assertThat(testActivite.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testActivite.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testActivite.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testActivite.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testActivite.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testActivite.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testActivite.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testActivite.getUserLastModif()).isEqualTo(UPDATED_USER_LAST_MODIF);
        assertThat(testActivite.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testActivite.getDateLastModif()).isEqualTo(UPDATED_DATE_LAST_MODIF);
    }

    @Test
    @Transactional
    public void updateNonExistingActivite() throws Exception {
        int databaseSizeBeforeUpdate = activiteRepository.findAll().size();

        // Create the Activite

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restActiviteMockMvc.perform(put("/api/activites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(activite)))
            .andExpect(status().isBadRequest());

        // Validate the Activite in the database
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteActivite() throws Exception {
        // Initialize the database
        activiteRepository.saveAndFlush(activite);

        int databaseSizeBeforeDelete = activiteRepository.findAll().size();

        // Get the activite
        restActiviteMockMvc.perform(delete("/api/activites/{id}", activite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Activite> activiteList = activiteRepository.findAll();
        assertThat(activiteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Activite.class);
        Activite activite1 = new Activite();
        activite1.setId(1L);
        Activite activite2 = new Activite();
        activite2.setId(activite1.getId());
        assertThat(activite1).isEqualTo(activite2);
        activite2.setId(2L);
        assertThat(activite1).isNotEqualTo(activite2);
        activite1.setId(null);
        assertThat(activite1).isNotEqualTo(activite2);
    }
}
