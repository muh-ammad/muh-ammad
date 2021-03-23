package com.themalomars.gpecmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Diplome.
 */
@Entity
@Table(name = "diplome")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Diplome implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle_diplome", nullable = false)
    private String libelleDiplome;

    @Column(name = "annee_diplome")
    private Instant anneeDiplome;

    @ManyToOne
    @JsonIgnoreProperties(value = "diplomes", allowSetters = true)
    private Employe employe;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleDiplome() {
        return libelleDiplome;
    }

    public Diplome libelleDiplome(String libelleDiplome) {
        this.libelleDiplome = libelleDiplome;
        return this;
    }

    public void setLibelleDiplome(String libelleDiplome) {
        this.libelleDiplome = libelleDiplome;
    }

    public Instant getAnneeDiplome() {
        return anneeDiplome;
    }

    public Diplome anneeDiplome(Instant anneeDiplome) {
        this.anneeDiplome = anneeDiplome;
        return this;
    }

    public void setAnneeDiplome(Instant anneeDiplome) {
        this.anneeDiplome = anneeDiplome;
    }

    public Employe getEmploye() {
        return employe;
    }

    public Diplome employe(Employe employe) {
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
        if (!(o instanceof Diplome)) {
            return false;
        }
        return id != null && id.equals(((Diplome) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Diplome{" +
            "id=" + getId() +
            ", libelleDiplome='" + getLibelleDiplome() + "'" +
            ", anneeDiplome='" + getAnneeDiplome() + "'" +
            "}";
    }
}
