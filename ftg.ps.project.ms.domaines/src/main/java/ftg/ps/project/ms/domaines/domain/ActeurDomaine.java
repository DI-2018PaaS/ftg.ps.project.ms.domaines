package ftg.ps.project.ms.domaines.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ActeurDomaine.
 */
@Entity
@Table(name = "acteur_domaine")
public class ActeurDomaine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_acteur")
    private Long idActeur;

    @OneToMany(mappedBy = "acteurDomaine")
    private Set<Domaine> domaineActeurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdActeur() {
        return idActeur;
    }

    public ActeurDomaine idActeur(Long idActeur) {
        this.idActeur = idActeur;
        return this;
    }

    public void setIdActeur(Long idActeur) {
        this.idActeur = idActeur;
    }

    public Set<Domaine> getDomaineActeurs() {
        return domaineActeurs;
    }

    public ActeurDomaine domaineActeurs(Set<Domaine> domaines) {
        this.domaineActeurs = domaines;
        return this;
    }

    public ActeurDomaine addDomaineActeur(Domaine domaine) {
        this.domaineActeurs.add(domaine);
        domaine.setActeurDomaine(this);
        return this;
    }

    public ActeurDomaine removeDomaineActeur(Domaine domaine) {
        this.domaineActeurs.remove(domaine);
        domaine.setActeurDomaine(null);
        return this;
    }

    public void setDomaineActeurs(Set<Domaine> domaines) {
        this.domaineActeurs = domaines;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActeurDomaine acteurDomaine = (ActeurDomaine) o;
        if (acteurDomaine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), acteurDomaine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActeurDomaine{" +
            "id=" + getId() +
            ", idActeur=" + getIdActeur() +
            "}";
    }
}
