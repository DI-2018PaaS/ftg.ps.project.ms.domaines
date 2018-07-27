package ftg.ps.project.ms.domaines.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Activite.
 */
@Entity
@Table(name = "activite")
public class Activite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "activite_id")
    private Long activiteId;

    @Column(name = "nom")
    private String nom;

    @Column(name = "description")
    private String description;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "user_created")
    private Long userCreated;

    @Column(name = "user_last_modif")
    private Long userLastModif;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "date_last_modif")
    private LocalDate dateLastModif;

    @ManyToOne
    @JsonIgnoreProperties("domaineActivites")
    private Domaine domaine;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActiviteId() {
        return activiteId;
    }

    public Activite activiteId(Long activiteId) {
        this.activiteId = activiteId;
        return this;
    }

    public void setActiviteId(Long activiteId) {
        this.activiteId = activiteId;
    }

    public String getNom() {
        return nom;
    }

    public Activite nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public Activite description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public Activite categorie(String categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public Activite dateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public Activite dateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Boolean isStatus() {
        return status;
    }

    public Activite status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Long getUserCreated() {
        return userCreated;
    }

    public Activite userCreated(Long userCreated) {
        this.userCreated = userCreated;
        return this;
    }

    public void setUserCreated(Long userCreated) {
        this.userCreated = userCreated;
    }

    public Long getUserLastModif() {
        return userLastModif;
    }

    public Activite userLastModif(Long userLastModif) {
        this.userLastModif = userLastModif;
        return this;
    }

    public void setUserLastModif(Long userLastModif) {
        this.userLastModif = userLastModif;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public Activite createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getDateLastModif() {
        return dateLastModif;
    }

    public Activite dateLastModif(LocalDate dateLastModif) {
        this.dateLastModif = dateLastModif;
        return this;
    }

    public void setDateLastModif(LocalDate dateLastModif) {
        this.dateLastModif = dateLastModif;
    }

    public Domaine getDomaine() {
        return domaine;
    }

    public Activite domaine(Domaine domaine) {
        this.domaine = domaine;
        return this;
    }

    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
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
        Activite activite = (Activite) o;
        if (activite.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), activite.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Activite{" +
            "id=" + getId() +
            ", activiteId=" + getActiviteId() +
            ", nom='" + getNom() + "'" +
            ", description='" + getDescription() + "'" +
            ", categorie='" + getCategorie() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", status='" + isStatus() + "'" +
            ", userCreated=" + getUserCreated() +
            ", userLastModif=" + getUserLastModif() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", dateLastModif='" + getDateLastModif() + "'" +
            "}";
    }
}
