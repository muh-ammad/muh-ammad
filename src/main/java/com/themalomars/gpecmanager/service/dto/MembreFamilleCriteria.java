package com.themalomars.gpecmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.themalomars.gpecmanager.domain.MembreFamille} entity. This class is used
 * in {@link com.themalomars.gpecmanager.web.rest.MembreFamilleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /membre-familles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MembreFamilleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter prenoms;

    private StringFilter nom;

    private InstantFilter dateNaissance;

    private StringFilter lieuNaissance;

    private BooleanFilter epoux;

    private BooleanFilter epouse;

    private BooleanFilter enfant;

    private LongFilter employeId;

    public MembreFamilleCriteria() {
    }

    public MembreFamilleCriteria(MembreFamilleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.prenoms = other.prenoms == null ? null : other.prenoms.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.dateNaissance = other.dateNaissance == null ? null : other.dateNaissance.copy();
        this.lieuNaissance = other.lieuNaissance == null ? null : other.lieuNaissance.copy();
        this.epoux = other.epoux == null ? null : other.epoux.copy();
        this.epouse = other.epouse == null ? null : other.epouse.copy();
        this.enfant = other.enfant == null ? null : other.enfant.copy();
        this.employeId = other.employeId == null ? null : other.employeId.copy();
    }

    @Override
    public MembreFamilleCriteria copy() {
        return new MembreFamilleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPrenoms() {
        return prenoms;
    }

    public void setPrenoms(StringFilter prenoms) {
        this.prenoms = prenoms;
    }

    public StringFilter getNom() {
        return nom;
    }

    public void setNom(StringFilter nom) {
        this.nom = nom;
    }

    public InstantFilter getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(InstantFilter dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public StringFilter getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(StringFilter lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public BooleanFilter getEpoux() {
        return epoux;
    }

    public void setEpoux(BooleanFilter epoux) {
        this.epoux = epoux;
    }

    public BooleanFilter getEpouse() {
        return epouse;
    }

    public void setEpouse(BooleanFilter epouse) {
        this.epouse = epouse;
    }

    public BooleanFilter getEnfant() {
        return enfant;
    }

    public void setEnfant(BooleanFilter enfant) {
        this.enfant = enfant;
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
        final MembreFamilleCriteria that = (MembreFamilleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(prenoms, that.prenoms) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(dateNaissance, that.dateNaissance) &&
            Objects.equals(lieuNaissance, that.lieuNaissance) &&
            Objects.equals(epoux, that.epoux) &&
            Objects.equals(epouse, that.epouse) &&
            Objects.equals(enfant, that.enfant) &&
            Objects.equals(employeId, that.employeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        prenoms,
        nom,
        dateNaissance,
        lieuNaissance,
        epoux,
        epouse,
        enfant,
        employeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MembreFamilleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (prenoms != null ? "prenoms=" + prenoms + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (dateNaissance != null ? "dateNaissance=" + dateNaissance + ", " : "") +
                (lieuNaissance != null ? "lieuNaissance=" + lieuNaissance + ", " : "") +
                (epoux != null ? "epoux=" + epoux + ", " : "") +
                (epouse != null ? "epouse=" + epouse + ", " : "") +
                (enfant != null ? "enfant=" + enfant + ", " : "") +
                (employeId != null ? "employeId=" + employeId + ", " : "") +
            "}";
    }

}
