package com.themalomars.gpecmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Distinction.
 */
@Entity
@Table(name = "distinction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Distinction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle_distinction", nullable = false)
    private String libelleDistinction;

    @ManyToOne
    @JsonIgnoreProperties(value = "distinctions", allowSetters = true)
    private Employe employe;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleDistinction() {
        return libelleDistinction;
    }

    public Distinction libelleDistinction(String libelleDistinction) {
        this.libelleDistinction = libelleDistinction;
        return this;
    }

    public void setLibelleDistinction(String libelleDistinction) {
        this.libelleDistinction = libelleDistinction;
    }

    public Employe getEmploye() {
        return employe;
    }

    public Distinction employe(Employe employe) {
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
        if (!(o instanceof Distinction)) {
            return false;
        }
        return id != null && id.equals(((Distinction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Distinction{" +
            "id=" + getId() +
            ", libelleDistinction='" + getLibelleDistinction() + "'" +
            "}";
    }
}
