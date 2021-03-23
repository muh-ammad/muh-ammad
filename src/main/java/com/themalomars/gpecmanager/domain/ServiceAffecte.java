package com.themalomars.gpecmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ServiceAffecte.
 */
@Entity
@Table(name = "service_affecte")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceAffecte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code_service", nullable = false)
    private Long codeService;

    @NotNull
    @Column(name = "libelle_service", nullable = false)
    private String libelleService;

    @OneToOne
    @JoinColumn(unique = true)
    private SecteurActivite secteurActivite;

    @ManyToMany(mappedBy = "services")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Employe> employes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCodeService() {
        return codeService;
    }

    public ServiceAffecte codeService(Long codeService) {
        this.codeService = codeService;
        return this;
    }

    public void setCodeService(Long codeService) {
        this.codeService = codeService;
    }

    public String getLibelleService() {
        return libelleService;
    }

    public ServiceAffecte libelleService(String libelleService) {
        this.libelleService = libelleService;
        return this;
    }

    public void setLibelleService(String libelleService) {
        this.libelleService = libelleService;
    }

    public SecteurActivite getSecteurActivite() {
        return secteurActivite;
    }

    public ServiceAffecte secteurActivite(SecteurActivite secteurActivite) {
        this.secteurActivite = secteurActivite;
        return this;
    }

    public void setSecteurActivite(SecteurActivite secteurActivite) {
        this.secteurActivite = secteurActivite;
    }

    public Set<Employe> getEmployes() {
        return employes;
    }

    public ServiceAffecte employes(Set<Employe> employes) {
        this.employes = employes;
        return this;
    }

    public ServiceAffecte addEmploye(Employe employe) {
        this.employes.add(employe);
        employe.getServices().add(this);
        return this;
    }

    public ServiceAffecte removeEmploye(Employe employe) {
        this.employes.remove(employe);
        employe.getServices().remove(this);
        return this;
    }

    public void setEmployes(Set<Employe> employes) {
        this.employes = employes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceAffecte)) {
            return false;
        }
        return id != null && id.equals(((ServiceAffecte) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceAffecte{" +
            "id=" + getId() +
            ", codeService=" + getCodeService() +
            ", libelleService='" + getLibelleService() + "'" +
            "}";
    }
}
