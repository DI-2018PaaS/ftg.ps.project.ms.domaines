package ftg.ps.project.ms.domaines.web.rest;

import com.codahale.metrics.annotation.Timed;
import ftg.ps.project.ms.domaines.domain.Domaine;
import ftg.ps.project.ms.domaines.repository.DomaineRepository;
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
 * REST controller for managing Domaine.
 */
@RestController
@RequestMapping("/api")
public class DomaineResource {

    private final Logger log = LoggerFactory.getLogger(DomaineResource.class);

    private static final String ENTITY_NAME = "domaine";

    private final DomaineRepository domaineRepository;

    public DomaineResource(DomaineRepository domaineRepository) {
        this.domaineRepository = domaineRepository;
    }

    /**
     * POST  /domaines : Create a new domaine.
     *
     * @param domaine the domaine to create
     * @return the ResponseEntity with status 201 (Created) and with body the new domaine, or with status 400 (Bad Request) if the domaine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/domaines")
    @Timed
    public ResponseEntity<Domaine> createDomaine(@RequestBody Domaine domaine) throws URISyntaxException {
        log.debug("REST request to save Domaine : {}", domaine);
        if (domaine.getId() != null) {
            throw new BadRequestAlertException("A new domaine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Domaine result = domaineRepository.save(domaine);
        return ResponseEntity.created(new URI("/api/domaines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /domaines : Updates an existing domaine.
     *
     * @param domaine the domaine to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated domaine,
     * or with status 400 (Bad Request) if the domaine is not valid,
     * or with status 500 (Internal Server Error) if the domaine couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/domaines")
    @Timed
    public ResponseEntity<Domaine> updateDomaine(@RequestBody Domaine domaine) throws URISyntaxException {
        log.debug("REST request to update Domaine : {}", domaine);
        if (domaine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Domaine result = domaineRepository.save(domaine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, domaine.getId().toString()))
            .body(result);
    }

    /**
     * GET  /domaines : get all the domaines.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of domaines in body
     */
    @GetMapping("/domaines")
    @Timed
    public List<Domaine> getAllDomaines() {
        log.debug("REST request to get all Domaines");
        return domaineRepository.findAll();
    }

    /**
     * GET  /domaines/:id : get the "id" domaine.
     *
     * @param id the id of the domaine to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the domaine, or with status 404 (Not Found)
     */
    @GetMapping("/domaines/{id}")
    @Timed
    public ResponseEntity<Domaine> getDomaine(@PathVariable Long id) {
        log.debug("REST request to get Domaine : {}", id);
        Optional<Domaine> domaine = domaineRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(domaine);
    }

    /**
     * DELETE  /domaines/:id : delete the "id" domaine.
     *
     * @param id the id of the domaine to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/domaines/{id}")
    @Timed
    public ResponseEntity<Void> deleteDomaine(@PathVariable Long id) {
        log.debug("REST request to delete Domaine : {}", id);

        domaineRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
