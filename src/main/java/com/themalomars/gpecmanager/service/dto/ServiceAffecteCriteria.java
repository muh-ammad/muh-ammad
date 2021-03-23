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
 * Criteria class for the {@link com.themalomars.gpecmanager.domain.ServiceAffecte} entity. This class is used
 * in {@link com.themalomars.gpecmanager.web.rest.ServiceAffecteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-affectes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceAffecteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter codeService;

    private StringFilter libelleService;

    private LongFilter secteurActiviteId;

    private LongFilter employeId;

    public ServiceAffecteCriteria() {
    }

    public ServiceAffecteCriteria(ServiceAffecteCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codeService = other.codeService == null ? null : other.codeService.copy();
        this.libelleService = other.libelleService == null ? null : other.libelleService.copy();
        this.secteurActiviteId = other.secteurActiviteId == null ? null : other.secteurActiviteId.copy();
        this.employeId = other.employeId == null ? null : other.employeId.copy();
    }

    @Override
    public ServiceAffecteCriteria copy() {
        return new ServiceAffecteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getCodeService() {
        return codeService;
    }

    public void setCodeService(LongFilter codeService) {
        this.codeService = codeService;
    }

    public StringFilter getLibelleService() {
        return libelleService;
    }

    public void setLibelleService(StringFilter libelleService) {
        this.libelleService = libelleService;
    }

    public LongFilter getSecteurActiviteId() {
        return secteurActiviteId;
    }

    public void setSecteurActiviteId(LongFilter secteurActiviteId) {
        this.secteurActiviteId = secteurActiviteId;
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
        final ServiceAffecteCriteria that = (ServiceAffecteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(codeService, that.codeService) &&
            Objects.equals(libelleService, that.libelleService) &&
            Objects.equals(secteurActiviteId, that.secteurActiviteId) &&
            Objects.equals(employeId, that.employeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        codeService,
        libelleService,
        secteurActiviteId,
        employeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServiceAffecteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (codeService != null ? "codeService=" + codeService + ", " : "") +
                (libelleService != null ? "libelleService=" + libelleService + ", " : "") +
                (secteurActiviteId != null ? "secteurActiviteId=" + secteurActiviteId + ", " : "") +
                (employeId != null ? "employeId=" + employeId + ", " : "") +
            "}";
    }

}
