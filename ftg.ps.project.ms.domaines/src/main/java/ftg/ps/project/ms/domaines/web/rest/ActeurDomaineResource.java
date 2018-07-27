package ftg.ps.project.ms.domaines.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.domaines.domain.ActeurDomaine;
import ftg.ps.project.ms.domaines.repository.ActeurDomaineRepository;
import ftg.ps.project.ms.domaines.web.rest.errors.BadRequestAlertException;
import ftg.ps.project.ms.domaines.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ActeurDomaine.
 */
@RestController
@RequestMapping("/api")
public class ActeurDomaineResource {

    private final Logger log = LoggerFactory.getLogger(ActeurDomaineResource.class);

    private static final String ENTITY_NAME = "acteurDomaine";

    private final ActeurDomaineRepository acteurDomaineRepository;

    public ActeurDomaineResource(ActeurDomaineRepository acteurDomaineRepository) {
        this.acteurDomaineRepository = acteurDomaineRepository;
    }

    /**
     * POST  /acteur-domaines : Create a new acteurDomaine.
     *
     * @param acteurDomaine the acteurDomaine to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acteurDomaine, or with status 400 (Bad Request) if the acteurDomaine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/acteur-domaines")
    @Timed
    public ResponseEntity<ActeurDomaine> createActeurDomaine(@RequestBody ActeurDomaine acteurDomaine) throws URISyntaxException {
        log.debug("REST request to save ActeurDomaine : {}", acteurDomaine);
        if (acteurDomaine.getId() != null) {
            throw new BadRequestAlertException("A new acteurDomaine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActeurDomaine result = acteurDomaineRepository.save(acteurDomaine);
        return ResponseEntity.created(new URI("/api/acteur-domaines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /acteur-domaines : Updates an existing acteurDomaine.
     *
     * @param acteurDomaine the acteurDomaine to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated acteurDomaine,
     * or with status 400 (Bad Request) if the acteurDomaine is not valid,
     * or with status 500 (Internal Server Error) if the acteurDomaine couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/acteur-domaines")
    @Timed
    public ResponseEntity<ActeurDomaine> updateActeurDomaine(@RequestBody ActeurDomaine acteurDomaine) throws URISyntaxException {
        log.debug("REST request to update ActeurDomaine : {}", acteurDomaine);
        if (acteurDomaine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActeurDomaine result = acteurDomaineRepository.save(acteurDomaine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, acteurDomaine.getId().toString()))
            .body(result);
    }

    /**
     * GET  /acteur-domaines : get all the acteurDomaines.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of acteurDomaines in body
     */
    @GetMapping("/acteur-domaines")
    @Timed
    public List<ActeurDomaine> getAllActeurDomaines() {
        log.debug("REST request to get all ActeurDomaines");
        return acteurDomaineRepository.findAll();
    }

    /**
     * GET  /acteur-domaines/:id : get the "id" acteurDomaine.
     *
     * @param id the id of the acteurDomaine to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the acteurDomaine, or with status 404 (Not Found)
     */
    @GetMapping("/acteur-domaines/{id}")
    @Timed
    public ResponseEntity<ActeurDomaine> getActeurDomaine(@PathVariable Long id) {
        log.debug("REST request to get ActeurDomaine : {}", id);
        Optional<ActeurDomaine> acteurDomaine = acteurDomaineRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(acteurDomaine);
    }

    /**
     * DELETE  /acteur-domaines/:id : delete the "id" acteurDomaine.
     *
     * @param id the id of the acteurDomaine to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/acteur-domaines/{id}")
    @Timed
    public ResponseEntity<Void> deleteActeurDomaine(@PathVariable Long id) {
        log.debug("REST request to delete ActeurDomaine : {}", id);

        acteurDomaineRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
