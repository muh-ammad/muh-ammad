package com.themalomars.gpecmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Fonction.
 */
@Entity
@Table(name = "fonction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Fonction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle_fonction", nullable = false)
    private String libelleFonction;

    @OneToMany(mappedBy = "fonction")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Specialite> specialites = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "fonctions", allowSetters = true)
    private Employe employe;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelleFonction() {
        return libelleFonction;
    }

    public Fonction libelleFonction(String libelleFonction) {
        this.libelleFonction = libelleFonction;
        return this;
    }

    public void setLibelleFonction(String libelleFonction) {
        this.libelleFonction = libelleFonction;
    }

    public Set<Specialite> getSpecialites() {
        return specialites;
    }

    public Fonction specialites(Set<Specialite> specialites) {
        this.specialites = specialites;
        return this;
    }

    public Fonction addSpecialite(Specialite specialite) {
        this.specialites.add(specialite);
        specialite.setFonction(this);
        return this;
    }

    public Fonction removeSpecialite(Specialite specialite) {
        this.specialites.remove(specialite);
        specialite.setFonction(null);
        return this;
    }

    public void setSpecialites(Set<Specialite> specialites) {
        this.specialites = specialites;
    }

    public Employe getEmploye() {
        return employe;
    }

    public Fonction employe(Employe employe) {
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
        if (!(o instanceof Fonction)) {
            return false;
        }
        return id != null && id.equals(((Fonction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fonction{" +
            "id=" + getId() +
            ", libelleFonction='" + getLibelleFonction() + "'" +
            "}";
    }
}
