package com.themalomars.gpecmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A OperationExterieur.
 */
@Entity
@Table(name = "operation_exterieur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OperationExterieur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "lieu_opex", nullable = false)
    private String lieuOpex;

    @NotNull
    @Column(name = "annee_opex", nullable = false)
    private Instant anneeOpex;

    @ManyToOne
    @JsonIgnoreProperties(value = "operationExterieurs", allowSetters = true)
    private Employe employe;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLieuOpex() {
        return lieuOpex;
    }

    public OperationExterieur lieuOpex(String lieuOpex) {
        this.lieuOpex = lieuOpex;
        return this;
    }

    public void setLieuOpex(String lieuOpex) {
        this.lieuOpex = lieuOpex;
    }

    public Instant getAnneeOpex() {
        return anneeOpex;
    }

    public OperationExterieur anneeOpex(Instant anneeOpex) {
        this.anneeOpex = anneeOpex;
        return this;
    }

    public void setAnneeOpex(Instant anneeOpex) {
        this.anneeOpex = anneeOpex;
    }

    public Employe getEmploye() {
        return employe;
    }

    public OperationExterieur employe(Employe employe) {
        this.employe = employe;
        return this;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OperationExterieur)) {
            return false;
        }
        return id != null && id.equals(((OperationExterieur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperationExterieur{" +
            "id=" + getId() +
            ", lieuOpex='" + getLieuOpex() + "'" +
            ", anneeOpex='" + getAnneeOpex() + "'" +
            "}";
    }
}
