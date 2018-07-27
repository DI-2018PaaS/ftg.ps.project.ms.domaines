package ftg.ps.project.ms.domaines.web.rest;

import ftg.ps.project.ms.domaines.McsDomainesApp;

import ftg.ps.project.ms.domaines.config.SecurityBeanOverrideConfiguration;

import ftg.ps.project.ms.domaines.domain.Domaine;
import ftg.ps.project.ms.domaines.repository.DomaineRepository;
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
 * Test class for the DomaineResource REST controller.
 *
 * @see DomaineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SecurityBeanOverrideConfiguration.class, McsDomainesApp.class})
public class DomaineResourceIntTest {

    private static final Long DEFAULT_DOMAINE_ID = 1L;
    private static final Long UPDATED_DOMAINE_ID = 2L;

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Long DEFAULT_USER_CREATED = 1L;
    private static final Long UPDATED_USER_CREATED = 2L;

    private static final Long DEFAULT_USER_LAST_MODIF = 1L;
    private static final Long UPDATED_USER_LAST_MODIF = 2L;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATEAST_MODIF = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEAST_MODIF = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DomaineRepository domaineRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDomaineMockMvc;

    private Domaine domaine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DomaineResource domaineResource = new DomaineResource(domaineRepository);
        this.restDomaineMockMvc = MockMvcBuilders.standaloneSetup(domaineResource)
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
    public static Domaine createEntity(EntityManager em) {
        Domaine domaine = new Domaine()
            .domaineId(DEFAULT_DOMAINE_ID)
            .nom(DEFAULT_NOM)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .userCreated(DEFAULT_USER_CREATED)
            .userLastModif(DEFAULT_USER_LAST_MODIF)
            .createdDate(DEFAULT_CREATED_DATE)
            .dateastModif(DEFAULT_DATEAST_MODIF);
        return domaine;
    }

    @Before
    public void initTest() {
        domaine = createEntity(em);
    }

    @Test
    @Transactional
    public void createDomaine() throws Exception {
        int databaseSizeBeforeCreate = domaineRepository.findAll().size();

        // Create the Domaine
        restDomaineMockMvc.perform(post("/api/domaines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domaine)))
            .andExpect(status().isCreated());

        // Validate the Domaine in the database
        List<Domaine> domaineList = domaineRepository.findAll();
        assertThat(domaineList).hasSize(databaseSizeBeforeCreate + 1);
        Domaine testDomaine = domaineList.get(domaineList.size() - 1);
        assertThat(testDomaine.getDomaineId()).isEqualTo(DEFAULT_DOMAINE_ID);
        assertThat(testDomaine.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testDomaine.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDomaine.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDomaine.getUserCreated()).isEqualTo(DEFAULT_USER_CREATED);
        assertThat(testDomaine.getUserLastModif()).isEqualTo(DEFAULT_USER_LAST_MODIF);
        assertThat(testDomaine.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDomaine.getDateastModif()).isEqualTo(DEFAULT_DATEAST_MODIF);
    }

    @Test
    @Transactional
    public void createDomaineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = domaineRepository.findAll().size();

        // Create the Domaine with an existing ID
        domaine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDomaineMockMvc.perform(post("/api/domaines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domaine)))
            .andExpect(status().isBadRequest());

        // Validate the Domaine in the database
        List<Domaine> domaineList = domaineRepository.findAll();
        assertThat(domaineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDomaines() throws Exception {
        // Initialize the database
        domaineRepository.saveAndFlush(domaine);

        // Get all the domaineList
        restDomaineMockMvc.perform(get("/api/domaines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(domaine.getId().intValue())))
            .andExpect(jsonPath("$.[*].domaineId").value(hasItem(DEFAULT_DOMAINE_ID.intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].userCreated").value(hasItem(DEFAULT_USER_CREATED.intValue())))
            .andExpect(jsonPath("$.[*].userLastModif").value(hasItem(DEFAULT_USER_LAST_MODIF.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].dateastModif").value(hasItem(DEFAULT_DATEAST_MODIF.toString())));
    }
    

    @Test
    @Transactional
    public void getDomaine() throws Exception {
        // Initialize the database
        domaineRepository.saveAndFlush(domaine);

        // Get the domaine
        restDomaineMockMvc.perform(get("/api/domaines/{id}", domaine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(domaine.getId().intValue()))
            .andExpect(jsonPath("$.domaineId").value(DEFAULT_DOMAINE_ID.intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.userCreated").value(DEFAULT_USER_CREATED.intValue()))
            .andExpect(jsonPath("$.userLastModif").value(DEFAULT_USER_LAST_MODIF.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.dateastModif").value(DEFAULT_DATEAST_MODIF.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingDomaine() throws Exception {
        // Get the domaine
        restDomaineMockMvc.perform(get("/api/domaines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDomaine() throws Exception {
        // Initialize the database
        domaineRepository.saveAndFlush(domaine);

        int databaseSizeBeforeUpdate = domaineRepository.findAll().size();

        // Update the domaine
        Domaine updatedDomaine = domaineRepository.findById(domaine.getId()).get();
        // Disconnect from session so that the updates on updatedDomaine are not directly saved in db
        em.detach(updatedDomaine);
        updatedDomaine
            .domaineId(UPDATED_DOMAINE_ID)
            .nom(UPDATED_NOM)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .userCreated(UPDATED_USER_CREATED)
            .userLastModif(UPDATED_USER_LAST_MODIF)
            .createdDate(UPDATED_CREATED_DATE)
            .dateastModif(UPDATED_DATEAST_MODIF);

        restDomaineMockMvc.perform(put("/api/domaines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDomaine)))
            .andExpect(status().isOk());

        // Validate the Domaine in the database
        List<Domaine> domaineList = domaineRepository.findAll();
        assertThat(domaineList).hasSize(databaseSizeBeforeUpdate);
        Domaine testDomaine = domaineList.get(domaineList.size() - 1);
        assertThat(testDomaine.getDomaineId()).isEqualTo(UPDATED_DOMAINE_ID);
        assertThat(testDomaine.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testDomaine.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDomaine.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDomaine.getUserCreated()).isEqualTo(UPDATED_USER_CREATED);
        assertThat(testDomaine.getUserLastModif()).isEqualTo(UPDATED_USER_LAST_MODIF);
        assertThat(testDomaine.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDomaine.getDateastModif()).isEqualTo(UPDATED_DATEAST_MODIF);
    }

    @Test
    @Transactional
    public void updateNonExistingDomaine() throws Exception {
        int databaseSizeBeforeUpdate = domaineRepository.findAll().size();

        // Create the Domaine

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDomaineMockMvc.perform(put("/api/domaines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(domaine)))
            .andExpect(status().isBadRequest());

        // Validate the Domaine in the database
        List<Domaine> domaineList = domaineRepository.findAll();
        assertThat(domaineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDomaine() throws Exception {
        // Initialize the database
        domaineRepository.saveAndFlush(domaine);

        int databaseSizeBeforeDelete = domaineRepository.findAll().size();

        // Get the domaine
        restDomaineMockMvc.perform(delete("/api/domaines/{id}", domaine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Domaine> domaineList = domaineRepository.findAll();
        assertThat(domaineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Domaine.class);
        Domaine domaine1 = new Domaine();
        domaine1.setId(1L);
        Domaine domaine2 = new Domaine();
        domaine2.setId(domaine1.getId());
        assertThat(domaine1).isEqualTo(domaine2);
        domaine2.setId(2L);
        assertThat(domaine1).isNotEqualTo(domaine2);
        domaine1.setId(null);
        assertThat(domaine1).isNotEqualTo(domaine2);
    }
}
