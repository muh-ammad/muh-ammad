package com.themalomars.gpecmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Specialite.
 */
@Entity
@Table(name = "specialite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Specialite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle_specialite", nullable = false)
    private String libelleSpecialite;

    @ManyToOne
    @JsonIgnoreProperties(value = "specialites", allowSetters = true)
    private Fonction fonction;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleSpecialite() {
        return libelleSpecialite;
    }

    public Specialite libelleSpecialite(String libelleSpecialite) {
        this.libelleSpecialite = libelleSpecialite;
        return this;
    }

    public void setLibelleSpecialite(String libelleSpecialite) {
        this.libelleSpecialite = libelleSpecialite;
    }

    public Fonction getFonction() {
        return fonction;
    }

    public Specialite fonction(Fonction fonction) {
        this.fonction = fonction;
        return this;
    }

    public void setFonction(Fonction fonction) {
        this.fonction = fonction;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Specialite)) {
            return false;
        }
        return id != null && id.equals(((Specialite) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Specialite{" +
            "id=" + getId() +
            ", libelleSpecialite='" + getLibelleSpecialite() + "'" +
            "}";
    }
}
