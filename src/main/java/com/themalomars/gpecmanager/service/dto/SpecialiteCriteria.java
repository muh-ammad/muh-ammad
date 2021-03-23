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
 * Criteria class for the {@link com.themalomars.gpecmanager.domain.Specialite} entity. This class is used
 * in {@link com.themalomars.gpecmanager.web.rest.SpecialiteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /specialites?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SpecialiteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelleSpecialite;

    private LongFilter fonctionId;

    public SpecialiteCriteria() {
    }

    public SpecialiteCriteria(SpecialiteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.libelleSpecialite = other.libelleSpecialite == null ? null : other.libelleSpecialite.copy();
        this.fonctionId = other.fonctionId == null ? null : other.fonctionId.copy();
    }

    @Override
    public SpecialiteCriteria copy() {
        return new SpecialiteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLibelleSpecialite() {
        return libelleSpecialite;
    }

    public void setLibelleSpecialite(StringFilter libelleSpecialite) {
        this.libelleSpecialite = libelleSpecialite;
    }

    public LongFilter getFonctionId() {
        return fonctionId;
    }

    public void setFonctionId(LongFilter fonctionId) {
        this.fonctionId = fonctionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SpecialiteCriteria that = (SpecialiteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelleSpecialite, that.libelleSpecialite) &&
            Objects.equals(fonctionId, that.fonctionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelleSpecialite,
        fonctionId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpecialiteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelleSpecialite != null ? "libelleSpecialite=" + libelleSpecialite + ", " : "") +
                (fonctionId != null ? "fonctionId=" + fonctionId + ", " : "") +
            "}";
    }

}
