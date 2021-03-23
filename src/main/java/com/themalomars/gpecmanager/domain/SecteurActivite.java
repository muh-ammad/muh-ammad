package com.themalomars.gpecmanager.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A SecteurActivite.
 */
@Entity
@Table(name = "secteur_activite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SecteurActivite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle_activite", nullable = false)
    private String libelleActivite;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleActivite() {
        return libelleActivite;
    }

    public SecteurActivite libelleActivite(String libelleActivite) {
        this.libelleActivite = libelleActivite;
        return this;
    }

    public void setLibelleActivite(String libelleActivite) {
        this.libelleActivite = libelleActivite;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecteurActivite)) {
            return false;
        }
        return id != null && id.equals(((SecteurActivite) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecteurActivite{" +
            "id=" + getId() +
            ", libelleActivite='" + getLibelleActivite() + "'" +
            "}";
    }
}
