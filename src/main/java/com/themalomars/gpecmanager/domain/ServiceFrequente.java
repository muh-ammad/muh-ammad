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
 * A ServiceFrequente.
 */
@Entity
@Table(name = "service_frequente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ServiceFrequente implements Serializable {

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

    @ManyToMany(mappedBy = "serviceFrequentes")
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

    public ServiceFrequente codeService(Long codeService) {
        this.codeService = codeService;
        return this;
    }

    public void setCodeService(Long codeService) {
        this.codeService = codeService;
    }

    public String getLibelleService() {
        return libelleService;
    }

    public ServiceFrequente libelleService(String libelleService) {
        this.libelleService = libelleService;
        return this;
    }

    public void setLibelleService(String libelleService) {
        this.libelleService = libelleService;
    }

    public Set<Employe> getEmployes() {
        return employes;
    }

    public ServiceFrequente employes(Set<Employe> employes) {
        this.employes = employes;
        return this;
    }

    public ServiceFrequente addEmploye(Employe employe) {
        this.employes.add(employe);
        employe.getServiceFrequentes().add(this);
        return this;
    }

    public ServiceFrequente removeEmploye(Employe employe) {
        this.employes.remove(employe);
        employe.getServiceFrequentes().remove(this);
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
        if (!(o instanceof ServiceFrequente)) {
            return false;
        }
        return id != null && id.equals(((ServiceFrequente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceFrequente{" +
            "id=" + getId() +
            ", codeService=" + getCodeService() +
            ", libelleService='" + getLibelleService() + "'" +
            "}";
    }
}
