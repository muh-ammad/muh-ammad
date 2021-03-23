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

/**
 * Criteria class for the {@link com.themalomars.gpecmanager.domain.Fonction} entity. This class is used
 * in {@link com.themalomars.gpecmanager.web.rest.FonctionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fonctions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FonctionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelleFonction;

    private LongFilter specialiteId;

    private LongFilter employeId;

    public FonctionCriteria() {
    }

    public FonctionCriteria(FonctionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.libelleFonction = other.libelleFonction == null ? null : other.libelleFonction.copy();
        this.specialiteId = other.specialiteId == null ? null : other.specialiteId.copy();
        this.employeId = other.employeId == null ? null : other.employeId.copy();
    }

    @Override
    public FonctionCriteria copy() {
        return new FonctionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLibelleFonction() {
        return libelleFonction;
    }

    public void setLibelleFonction(StringFilter libelleFonction) {
        this.libelleFonction = libelleFonction;
    }

    public LongFilter getSpecialiteId() {
        return specialiteId;
    }

    public void setSpecialiteId(LongFilter specialiteId) {
        this.specialiteId = specialiteId;
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
        final FonctionCriteria that = (FonctionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelleFonction, that.libelleFonction) &&
            Objects.equals(specialiteId, that.specialiteId) &&
            Objects.equals(employeId, that.employeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelleFonction,
        specialiteId,
        employeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FonctionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelleFonction != null ? "libelleFonction=" + libelleFonction + ", " : "") +
                (specialiteId != null ? "specialiteId=" + specialiteId + ", " : "") +
                (employeId != null ? "employeId=" + employeId + ", " : "") +
            "}";
    }

}
