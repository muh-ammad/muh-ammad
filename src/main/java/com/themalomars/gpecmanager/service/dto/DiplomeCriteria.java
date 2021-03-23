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
 * Criteria class for the {@link com.themalomars.gpecmanager.domain.Diplome} entity. This class is used
 * in {@link com.themalomars.gpecmanager.web.rest.DiplomeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /diplomes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DiplomeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter libelleDiplome;

    private InstantFilter anneeDiplome;

    private LongFilter employeId;

    public DiplomeCriteria() {
    }

    public DiplomeCriteria(DiplomeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.libelleDiplome = other.libelleDiplome == null ? null : other.libelleDiplome.copy();
        this.anneeDiplome = other.anneeDiplome == null ? null : other.anneeDiplome.copy();
        this.employeId = other.employeId == null ? null : other.employeId.copy();
    }

    @Override
    public DiplomeCriteria copy() {
        return new DiplomeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getLibelleDiplome() {
        return libelleDiplome;
    }

    public void setLibelleDiplome(StringFilter libelleDiplome) {
        this.libelleDiplome = libelleDiplome;
    }

    public InstantFilter getAnneeDiplome() {
        return anneeDiplome;
    }

    public void setAnneeDiplome(InstantFilter anneeDiplome) {
        this.anneeDiplome = anneeDiplome;
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
        final DiplomeCriteria that = (DiplomeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(libelleDiplome, that.libelleDiplome) &&
            Objects.equals(anneeDiplome, that.anneeDiplome) &&
            Objects.equals(employeId, that.employeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        libelleDiplome,
        anneeDiplome,
        employeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DiplomeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (libelleDiplome != null ? "libelleDiplome=" + libelleDiplome + ", " : "") +
                (anneeDiplome != null ? "anneeDiplome=" + anneeDiplome + ", " : "") +
                (employeId != null ? "employeId=" + employeId + ", " : "") +
            "}";
    }

}
