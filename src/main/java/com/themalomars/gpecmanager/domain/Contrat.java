package com.themalomars.gpecmanager.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.themalomars.gpecmanager.domain.enumeration.NiveauEtude;

import com.themalomars.gpecmanager.domain.enumeration.TypeContrat;

/**
 * A Contrat.
 */
@Entity
@Table(name = "contrat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contrat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero_contrat", nullable = false)
    private String numeroContrat;

    @NotNull
    @Column(name = "libelle_contrat", nullable = false)
    private String libelleContrat;

    @NotNull
    @Column(name = "date_debut", nullable = false)
    private Instant dateDebut;

    @NotNull
    @Column(name = "date_fin", nullable = false)
    private Instant dateFin;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "niveau_etude", nullable = false)
    private NiveauEtude niveauEtude;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_contrat", nullable = false)
    private TypeContrat typeContrat;

    @OneToOne
    @JoinColumn(unique = true)
    private Employe employe;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroContrat() {
        return numeroContrat;
    }

    public Contrat numeroContrat(String numeroContrat) {
        this.numeroContrat = numeroContrat;
        return this;
    }

    public void setNumeroContrat(String numeroContrat) {
        this.numeroContrat = numeroContrat;
    }

    public String getLibelleContrat() {
        return libelleContrat;
    }

    public Contrat libelleContrat(String libelleContrat) {
        this.libelleContrat = libelleContrat;
        return this;
    }

    public void setLibelleContrat(String libelleContrat) {
        this.libelleContrat = libelleContrat;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public Contrat dateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
        return this;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Instant getDateFin() {
        return dateFin;
    }

    public Contrat dateFin(Instant dateFin) {
        this.dateFin = dateFin;
        return this;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public NiveauEtude getNiveauEtude() {
        return niveauEtude;
    }

    public Contrat niveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
        return this;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public TypeContrat getTypeContrat() {
        return typeContrat;
    }

    public Contrat typeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
        return this;
    }

    public void setTypeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
    }

    public Employe getEmploye() {
        return employe;
    }

    public Contrat employe(Employe employe) {
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
        if (!(o instanceof Contrat)) {
            return false;
        }
        return id != null && id.equals(((Contrat) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contrat{" +
            "id=" + getId() +
            ", numeroContrat='" + getNumeroContrat() + "'" +
            ", libelleContrat='" + getLibelleContrat() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", niveauEtude='" + getNiveauEtude() + "'" +
            ", typeContrat='" + getTypeContrat() + "'" +
            "}";
    }
}
