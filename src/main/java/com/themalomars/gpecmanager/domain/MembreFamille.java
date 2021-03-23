package com.themalomars.gpecmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A MembreFamille.
 */
@Entity
@Table(name = "membre_famille")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MembreFamille implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "prenoms", nullable = false)
    private String prenoms;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "date_naissance", nullable = false)
    private Instant dateNaissance;

    @NotNull
    @Column(name = "lieu_naissance", nullable = false)
    private String lieuNaissance;

    @Column(name = "epoux")
    private Boolean epoux;

    @Column(name = "epouse")
    private Boolean epouse;

    @Column(name = "enfant")
    private Boolean enfant;

    @ManyToOne
    @JsonIgnoreProperties(value = "membreFamilles", allowSetters = true)
    private Employe employe;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public MembreFamille prenoms(String prenoms) {
        this.prenoms = prenoms;
        return this;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getNom() {
        return nom;
    }

    public MembreFamille nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Instant getDateNaissance() {
        return dateNaissance;
    }

    public MembreFamille dateNaissance(Instant dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(Instant dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public MembreFamille lieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
        return this;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public Boolean isEpoux() {
        return epoux;
    }

    public MembreFamille epoux(Boolean epoux) {
        this.epoux = epoux;
        return this;
    }

    public void setEpoux(Boolean epoux) {
        this.epoux = epoux;
    }

    public Boolean isEpouse() {
        return epouse;
    }

    public MembreFamille epouse(Boolean epouse) {
        this.epouse = epouse;
        return this;
    }

    public void setEpouse(Boolean epouse) {
        this.epouse = epouse;
    }

    public Boolean isEnfant() {
        return enfant;
    }

    public MembreFamille enfant(Boolean enfant) {
        this.enfant = enfant;
        return this;
    }

    public void setEnfant(Boolean enfant) {
        this.enfant = enfant;
    }

    public Employe getEmploye() {
        return employe;
    }

    public MembreFamille employe(Employe employe) {
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
        if (!(o instanceof MembreFamille)) {
            return false;
        }
        return id != null && id.equals(((MembreFamille) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MembreFamille{" +
            "id=" + getId() +
            ", prenoms='" + getPrenoms() + "'" +
            ", nom='" + getNom() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", lieuNaissance='" + getLieuNaissance() + "'" +
            ", epoux='" + isEpoux() + "'" +
            ", epouse='" + isEpouse() + "'" +
            ", enfant='" + isEnfant() + "'" +
            "}";
    }
}
