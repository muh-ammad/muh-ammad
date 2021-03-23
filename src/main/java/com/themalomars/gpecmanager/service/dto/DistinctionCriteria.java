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
 * Criteria class for the {@link com.themalomars.gpecmanager.domain.Distinction} entity. This class is used
 * in {@link com.themalomars.gpecmanager.web.rest.DistinctionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /distinctions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DistinctionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelleDistinction;

    private LongFilter employeId;

    public DistinctionCriteria() {
    }

    public DistinctionCriteria(DistinctionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.libelleDistinction = other.libelleDistinction == null ? null : other.libelleDistinction.copy();
        this.employeId = other.employeId == null ? null : other.employeId.copy();
    }

    @Override
    public DistinctionCriteria copy() {
        return new DistinctionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLibelleDistinction() {
        return libelleDistinction;
    }

    public void setLibelleDistinction(StringFilter libelleDistinction) {
        this.libelleDistinction = libelleDistinction;
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
        final DistinctionCriteria that = (DistinctionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelleDistinction, that.libelleDistinction) &&
            Objects.equals(employeId, that.employeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelleDistinction,
        employeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DistinctionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelleDistinction != null ? "libelleDistinction=" + libelleDistinction + ", " : "") +
                (employeId != null ? "employeId=" + employeId + ", " : "") +
            "}";
    }

}
