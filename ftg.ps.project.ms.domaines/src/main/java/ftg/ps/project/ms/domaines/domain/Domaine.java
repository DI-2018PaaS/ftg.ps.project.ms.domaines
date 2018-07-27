package ftg.ps.project.ms.domaines.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Domaine.
 */
@Entity
@Table(name = "domaine")
public class Domaine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "domaine_id")
    private Long domaineId;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "user_created")
    private Long userCreated;

    @Column(name = "user_last_modif")
    private Long userLastModif;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "dateast_modif")
    private LocalDate dateastModif;

    @OneToMany(mappedBy = "domaine")
    private Set<Activite> domaineActivites = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("domaineActeurs")
    private ActeurDomaine acteurDomaine;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDomaineId() {
        return domaineId;
    }

    public Domaine domaineId(Long domaineId) {
        this.domaineId = domaineId;
        return this;
    }

    public void setDomaineId(Long domaineId) {
        this.domaineId = domaineId;
    }

    public String getNom() {
        return nom;
    }

    public Domaine nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public Domaine description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isStatus() {
        return status;
    }

    public Domaine status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Domaine userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserLastModif() {
        return userLastModif;
    }

    public Domaine userLastModif(Long userLastModif) {
        this.userLastModif = userLastModif;
        return this;
    }

    public void setUserLastModif(Long userLastModif) {
        this.userLastModif = userLastModif;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Domaine createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getDateastModif() {
        return dateastModif;
    }

    public Domaine dateastModif(LocalDate dateastModif) {
        this.dateastModif = dateastModif;
        return this;
    }

    public void setDateastModif(LocalDate dateastModif) {
        this.dateastModif = dateastModif;
    }

    public Set<Activite> getDomaineActivites() {
        return domaineActivites;
    }

    public Domaine domaineActivites(Set<Activite> activites) {
        this.domaineActivites = activites;
        return this;
    }

    public Domaine addDomaineActivites(Activite activite) {
        this.domaineActivites.add(activite);
        activite.setDomaine(this);
        return this;
    }

    public Domaine removeDomaineActivites(Activite activite) {
        this.domaineActivites.remove(activite);
        activite.setDomaine(null);
        return this;
    }

    public void setDomaineActivites(Set<Activite> activites) {
        this.domaineActivites = activites;
    }

    public ActeurDomaine getActeurDomaine() {
        return acteurDomaine;
    }

    public Domaine acteurDomaine(ActeurDomaine acteurDomaine) {
        this.acteurDomaine = acteurDomaine;
        return this;
    }

    public void setActeurDomaine(ActeurDomaine acteurDomaine) {
        this.acteurDomaine = acteurDomaine;
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
        Domaine domaine = (Domaine) o;
        if (domaine.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), domaine.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Domaine{" +
            "id=" + getId() +
            ", domaineId=" + getDomaineId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", status='" + isStatus() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userLastModif=" + getUserLastModif() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", dateastModif='" + getDateastModif() + "'" +
            "}";
    }
}
