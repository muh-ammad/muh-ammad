package com.themalomars.gpecmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.themalomars.gpecmanager.domain.enumeration.NiveauEtude;
import com.themalomars.gpecmanager.domain.enumeration.TypeContrat;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.themalomars.gpecmanager.domain.Contrat} entity. This class is used
 * in {@link com.themalomars.gpecmanager.web.rest.ContratResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /contrats?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ContratCriteria implements Serializable, Criteria {
    /**
     * Class for filtering NiveauEtude
     */
    public static class NiveauEtudeFilter extends Filter<NiveauEtude> {

        public NiveauEtudeFilter() {
        }

        public NiveauEtudeFilter(NiveauEtudeFilter filter) {
            super(filter);
        }

        @Override
        public NiveauEtudeFilter copy() {
            return new NiveauEtudeFilter(this);
        }

    }
    /**
     * Class for filtering TypeContrat
     */
    public static class TypeContratFilter extends Filter<TypeContrat> {

        public TypeContratFilter() {
        }

        public TypeContratFilter(TypeContratFilter filter) {
            super(filter);
        }

        @Override
        public TypeContratFilter copy() {
            return new TypeContratFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter numeroContrat;

    private StringFilter libelleContrat;

    private InstantFilter dateDebut;

    private InstantFilter dateFin;

    private NiveauEtudeFilter niveauEtude;

    private TypeContratFilter typeContrat;

    private LongFilter employeId;

    public ContratCriteria() {
    }

    public ContratCriteria(ContratCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.numeroContrat = other.numeroContrat == null ? null : other.numeroContrat.copy();
        this.libelleContrat = other.libelleContrat == null ? null : other.libelleContrat.copy();
        this.dateDebut = other.dateDebut == null ? null : other.dateDebut.copy();
        this.dateFin = other.dateFin == null ? null : other.dateFin.copy();
        this.niveauEtude = other.niveauEtude == null ? null : other.niveauEtude.copy();
        this.typeContrat = other.typeContrat == null ? null : other.typeContrat.copy();
        this.employeId = other.employeId == null ? null : other.employeId.copy();
    }

    @Override
    public ContratCriteria copy() {
        return new ContratCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNumeroContrat() {
        return numeroContrat;
    }

    public void setNumeroContrat(StringFilter numeroContrat) {
        this.numeroContrat = numeroContrat;
    }

    public StringFilter getLibelleContrat() {
        return libelleContrat;
    }

    public void setLibelleContrat(StringFilter libelleContrat) {
        this.libelleContrat = libelleContrat;
    }

    public InstantFilter getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(InstantFilter dateDebut) {
        this.dateDebut = dateDebut;
    }

    public InstantFilter getDateFin() {
        return dateFin;
    }

    public void setDateFin(InstantFilter dateFin) {
        this.dateFin = dateFin;
    }

    public NiveauEtudeFilter getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(NiveauEtudeFilter niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public TypeContratFilter getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(TypeContratFilter typeContrat) {
        this.typeContrat = typeContrat;
    }

    public LongFilter getEmployeId() {
        return employeId;
    }

    public void setEmployeId(LongFilter employeId) {
        this.employeId = employeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ContratCriteria that = (ContratCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(numeroContrat, that.numeroContrat) &&
            Objects.equals(libelleContrat, that.libelleContrat) &&
            Objects.equals(dateDebut, that.dateDebut) &&
            Objects.equals(dateFin, that.dateFin) &&
            Objects.equals(niveauEtude, that.niveauEtude) &&
            Objects.equals(typeContrat, that.typeContrat) &&
            Objects.equals(employeId, that.employeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        numeroContrat,
        libelleContrat,
        dateDebut,
        dateFin,
        niveauEtude,
        typeContrat,
        employeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContratCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numeroContrat != null ? "numeroContrat=" + numeroContrat + ", " : "") +
                (libelleContrat != null ? "libelleContrat=" + libelleContrat + ", " : "") +
                (dateDebut != null ? "dateDebut=" + dateDebut + ", " : "") +
                (dateFin != null ? "dateFin=" + dateFin + ", " : "") +
                (niveauEtude != null ? "niveauEtude=" + niveauEtude + ", " : "") +
                (typeContrat != null ? "typeContrat=" + typeContrat + ", " : "") +
                (employeId != null ? "employeId=" + employeId + ", " : "") +
            "}";
    }

}
