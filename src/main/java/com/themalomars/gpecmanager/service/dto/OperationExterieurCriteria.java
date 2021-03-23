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
 * Criteria class for the {@link com.themalomars.gpecmanager.domain.OperationExterieur} entity. This class is used
 * in {@link com.themalomars.gpecmanager.web.rest.OperationExterieurResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /operation-exterieurs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OperationExterieurCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter lieuOpex;

    private InstantFilter anneeOpex;

    private LongFilter employeId;

    public OperationExterieurCriteria() {
    }

    public OperationExterieurCriteria(OperationExterieurCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.lieuOpex = other.lieuOpex == null ? null : other.lieuOpex.copy();
        this.anneeOpex = other.anneeOpex == null ? null : other.anneeOpex.copy();
        this.employeId = other.employeId == null ? null : other.employeId.copy();
    }

    @Override
    public OperationExterieurCriteria copy() {
        return new OperationExterieurCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLieuOpex() {
        return lieuOpex;
    }

    public void setLieuOpex(StringFilter lieuOpex) {
        this.lieuOpex = lieuOpex;
    }

    public InstantFilter getAnneeOpex() {
        return anneeOpex;
    }

    public void setAnneeOpex(InstantFilter anneeOpex) {
        this.anneeOpex = anneeOpex;
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
        final OperationExterieurCriteria that = (OperationExterieurCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(lieuOpex, that.lieuOpex) &&
            Objects.equals(anneeOpex, that.anneeOpex) &&
            Objects.equals(employeId, that.employeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        lieuOpex,
        anneeOpex,
        employeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OperationExterieurCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (lieuOpex != null ? "lieuOpex=" + lieuOpex + ", " : "") +
                (anneeOpex != null ? "anneeOpex=" + anneeOpex + ", " : "") +
                (employeId != null ? "employeId=" + employeId + ", " : "") +
            "}";
    }

}
