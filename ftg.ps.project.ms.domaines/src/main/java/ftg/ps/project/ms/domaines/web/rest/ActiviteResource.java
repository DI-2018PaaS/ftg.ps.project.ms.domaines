package ftg.ps.project.ms.domaines.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.domaines.domain.Activite;
import ftg.ps.project.ms.domaines.repository.ActiviteRepository;
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
 * REST controller for managing Activite.
 */
@RestController
@RequestMapping("/api")
public class ActiviteResource {

    private final Logger log = LoggerFactory.getLogger(ActiviteResource.class);

    private static final String ENTITY_NAME = "activite";

    private final ActiviteRepository activiteRepository;

    public ActiviteResource(ActiviteRepository activiteRepository) {
        this.activiteRepository = activiteRepository;
    }

    /**
     * POST  /activites : Create a new activite.
     *
     * @param activite the activite to create
     * @return the ResponseEntity with status 201 (Created) and with body the new activite, or with status 400 (Bad Request) if the activite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/activites")
    @Timed
    public ResponseEntity<Activite> createActivite(@RequestBody Activite activite) throws URISyntaxException {
        log.debug("REST request to save Activite : {}", activite);
        if (activite.getId() != null) {
            throw new BadRequestAlertException("A new activite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Activite result = activiteRepository.save(activite);
        return ResponseEntity.created(new URI("/api/activites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /activites : Updates an existing activite.
     *
     * @param activite the activite to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated activite,
     * or with status 400 (Bad Request) if the activite is not valid,
     * or with status 500 (Internal Server Error) if the activite couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/activites")
    @Timed
    public ResponseEntity<Activite> updateActivite(@RequestBody Activite activite) throws URISyntaxException {
        log.debug("REST request to update Activite : {}", activite);
        if (activite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Activite result = activiteRepository.save(activite);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, activite.getId().toString()))
            .body(result);
    }

    /**
     * GET  /activites : get all the activites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of activites in body
     */
    @GetMapping("/activites")
    @Timed
    public List<Activite> getAllActivites() {
        log.debug("REST request to get all Activites");
        return activiteRepository.findAll();
    }

    /**
     * GET  /activites/:id : get the "id" activite.
     *
     * @param id the id of the activite to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the activite, or with status 404 (Not Found)
     */
    @GetMapping("/activites/{id}")
    @Timed
    public ResponseEntity<Activite> getActivite(@PathVariable Long id) {
        log.debug("REST request to get Activite : {}", id);
        Optional<Activite> activite = activiteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(activite);
    }

    /**
     * DELETE  /activites/:id : delete the "id" activite.
     *
     * @param id the id of the activite to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/activites/{id}")
    @Timed
    public ResponseEntity<Void> deleteActivite(@PathVariable Long id) {
        log.debug("REST request to delete Activite : {}", id);

        activiteRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
