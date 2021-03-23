package com.themalomars.gpecmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.themalomars.gpecmanager.domain.enumeration.GroupeSanguin;
import com.themalomars.gpecmanager.domain.enumeration.Matrimonial;
import com.themalomars.gpecmanager.domain.enumeration.Sexe;
import com.themalomars.gpecmanager.domain.enumeration.Disponibilite;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.themalomars.gpecmanager.domain.Employe} entity. This class is used
 * in {@link com.themalomars.gpecmanager.web.rest.EmployeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeCriteria implements Serializable, Criteria {
    /**
     * Class for filtering GroupeSanguin
     */
    public static class GroupeSanguinFilter extends Filter<GroupeSanguin> {

        public GroupeSanguinFilter() {
        }

        public GroupeSanguinFilter(GroupeSanguinFilter filter) {
            super(filter);
        }

        @Override
        public GroupeSanguinFilter copy() {
            return new GroupeSanguinFilter(this);
        }

    }
    /**
     * Class for filtering Matrimonial
     */
    public static class MatrimonialFilter extends Filter<Matrimonial> {

        public MatrimonialFilter() {
        }

        public MatrimonialFilter(MatrimonialFilter filter) {
            super(filter);
        }

        @Override
        public MatrimonialFilter copy() {
            return new MatrimonialFilter(this);
        }

    }
    /**
     * Class for filtering Sexe
     */
    public static class SexeFilter extends Filter<Sexe> {

        public SexeFilter() {
        }

        public SexeFilter(SexeFilter filter) {
            super(filter);
        }

        @Override
        public SexeFilter copy() {
            return new SexeFilter(this);
        }

    }
    /**
     * Class for filtering Disponibilite
     */
    public static class DisponibiliteFilter extends Filter<Disponibilite> {

        public DisponibiliteFilter() {
        }

        public DisponibiliteFilter(DisponibiliteFilter filter) {
            super(filter);
        }

        @Override
        public DisponibiliteFilter copy() {
            return new DisponibiliteFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter matricule;

    private StringFilter prenoms;

    private StringFilter nom;

    private StringFilter intituleEmploye;

    private InstantFilter dateNaissance;

    private InstantFilter lieuNaissance;

    private StringFilter numeroTelephone;

    private StringFilter adresse;

    private StringFilter email;

    private InstantFilter dateEmbauchement;

    private InstantFilter dateRetraite;

    private LongFilter age;

    private StringFilter numeroCni;

    private StringFilter numeroIpres;

    private StringFilter numeroCss;

    private GroupeSanguinFilter groupeSanguin;

    private MatrimonialFilter situationMatrimonial;

    private SexeFilter sexe;

    private DisponibiliteFilter disponibilite;

    private LongFilter contratId;

    private LongFilter diplomeId;

    private LongFilter fonctionId;

    private LongFilter membreFamilleId;

    private LongFilter distinctionId;

    private LongFilter operationExterieurId;

    private LongFilter serviceId;

    private LongFilter serviceFrequenteId;

    public EmployeCriteria() {
    }

    public EmployeCriteria(EmployeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.matricule = other.matricule == null ? null : other.matricule.copy();
        this.prenoms = other.prenoms == null ? null : other.prenoms.copy();
        this.nom = other.nom == null ? null : other.nom.copy();
        this.intituleEmploye = other.intituleEmploye == null ? null : other.intituleEmploye.copy();
        this.dateNaissance = other.dateNaissance == null ? null : other.dateNaissance.copy();
        this.lieuNaissance = other.lieuNaissance == null ? null : other.lieuNaissance.copy();
        this.numeroTelephone = other.numeroTelephone == null ? null : other.numeroTelephone.copy();
        this.adresse = other.adresse == null ? null : other.adresse.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.dateEmbauchement = other.dateEmbauchement == null ? null : other.dateEmbauchement.copy();
        this.dateRetraite = other.dateRetraite == null ? null : other.dateRetraite.copy();
        this.age = other.age == null ? null : other.age.copy();
        this.numeroCni = other.numeroCni == null ? null : other.numeroCni.copy();
        this.numeroIpres = other.numeroIpres == null ? null : other.numeroIpres.copy();
        this.numeroCss = other.numeroCss == null ? null : other.numeroCss.copy();
        this.groupeSanguin = other.groupeSanguin == null ? null : other.groupeSanguin.copy();
        this.situationMatrimonial = other.situationMatrimonial == null ? null : other.situationMatrimonial.copy();
        this.sexe = other.sexe == null ? null : other.sexe.copy();
        this.disponibilite = other.disponibilite == null ? null : other.disponibilite.copy();
        this.contratId = other.contratId == null ? null : other.contratId.copy();
        this.diplomeId = other.diplomeId == null ? null : other.diplomeId.copy();
        this.fonctionId = other.fonctionId == null ? null : other.fonctionId.copy();
        this.membreFamilleId = other.membreFamilleId == null ? null : other.membreFamilleId.copy();
        this.distinctionId = other.distinctionId == null ? null : other.distinctionId.copy();
        this.operationExterieurId = other.operationExterieurId == null ? null : other.operationExterieurId.copy();
        this.serviceId = other.serviceId == null ? null : other.serviceId.copy();
        this.serviceFrequenteId = other.serviceFrequenteId == null ? null : other.serviceFrequenteId.copy();
    }

    @Override
    public EmployeCriteria copy() {
        return new EmployeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMatricule() {
        return matricule;
    }

    public void setMatricule(StringFilter matricule) {
        this.matricule = matricule;
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

    public StringFilter getIntituleEmploye() {
        return intituleEmploye;
    }

    public void setIntituleEmploye(StringFilter intituleEmploye) {
        this.intituleEmploye = intituleEmploye;
    }

    public InstantFilter getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(InstantFilter dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public InstantFilter getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(InstantFilter lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public StringFilter getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(StringFilter numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public StringFilter getAdresse() {
        return adresse;
    }

    public void setAdresse(StringFilter adresse) {
        this.adresse = adresse;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public InstantFilter getDateEmbauchement() {
        return dateEmbauchement;
    }

    public void setDateEmbauchement(InstantFilter dateEmbauchement) {
        this.dateEmbauchement = dateEmbauchement;
    }

    public InstantFilter getDateRetraite() {
        return dateRetraite;
    }

    public void setDateRetraite(InstantFilter dateRetraite) {
        this.dateRetraite = dateRetraite;
    }

    public LongFilter getAge() {
        return age;
    }

    public void setAge(LongFilter age) {
        this.age = age;
    }

    public StringFilter getNumeroCni() {
        return numeroCni;
    }

    public void setNumeroCni(StringFilter numeroCni) {
        this.numeroCni = numeroCni;
    }

    public StringFilter getNumeroIpres() {
        return numeroIpres;
    }

    public void setNumeroIpres(StringFilter numeroIpres) {
        this.numeroIpres = numeroIpres;
    }

    public StringFilter getNumeroCss() {
        return numeroCss;
    }

    public void setNumeroCss(StringFilter numeroCss) {
        this.numeroCss = numeroCss;
    }

    public GroupeSanguinFilter getGroupeSanguin() {
        return groupeSanguin;
    }

    public void setGroupeSanguin(GroupeSanguinFilter groupeSanguin) {
        this.groupeSanguin = groupeSanguin;
    }

    public MatrimonialFilter getSituationMatrimonial() {
        return situationMatrimonial;
    }

    public void setSituationMatrimonial(MatrimonialFilter situationMatrimonial) {
        this.situationMatrimonial = situationMatrimonial;
    }

    public SexeFilter getSexe() {
        return sexe;
    }

    public void setSexe(SexeFilter sexe) {
        this.sexe = sexe;
    }

    public DisponibiliteFilter getDisponibilite() {
        return disponibilite;
    }

    public void setDisponibilite(DisponibiliteFilter disponibilite) {
        this.disponibilite = disponibilite;
    }

    public LongFilter getContratId() {
        return contratId;
    }

    public void setContratId(LongFilter contratId) {
        this.contratId = contratId;
    }

    public LongFilter getDiplomeId() {
        return diplomeId;
    }

    public void setDiplomeId(LongFilter diplomeId) {
        this.diplomeId = diplomeId;
    }

    public LongFilter getFonctionId() {
        return fonctionId;
    }

    public void setFonctionId(LongFilter fonctionId) {
        this.fonctionId = fonctionId;
    }

    public LongFilter getMembreFamilleId() {
        return membreFamilleId;
    }

    public void setMembreFamilleId(LongFilter membreFamilleId) {
        this.membreFamilleId = membreFamilleId;
    }

    public LongFilter getDistinctionId() {
        return distinctionId;
    }

    public void setDistinctionId(LongFilter distinctionId) {
        this.distinctionId = distinctionId;
    }

    public LongFilter getOperationExterieurId() {
        return operationExterieurId;
    }

    public void setOperationExterieurId(LongFilter operationExterieurId) {
        this.operationExterieurId = operationExterieurId;
    }

    public LongFilter getServiceId() {
        return serviceId;
    }

    public void setServiceId(LongFilter serviceId) {
        this.serviceId = serviceId;
    }

    public LongFilter getServiceFrequenteId() {
        return serviceFrequenteId;
    }

    public void setServiceFrequenteId(LongFilter serviceFrequenteId) {
        this.serviceFrequenteId = serviceFrequenteId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeCriteria that = (EmployeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(matricule, that.matricule) &&
            Objects.equals(prenoms, that.prenoms) &&
            Objects.equals(nom, that.nom) &&
            Objects.equals(intituleEmploye, that.intituleEmploye) &&
            Objects.equals(dateNaissance, that.dateNaissance) &&
            Objects.equals(lieuNaissance, that.lieuNaissance) &&
            Objects.equals(numeroTelephone, that.numeroTelephone) &&
            Objects.equals(adresse, that.adresse) &&
            Objects.equals(email, that.email) &&
            Objects.equals(dateEmbauchement, that.dateEmbauchement) &&
            Objects.equals(dateRetraite, that.dateRetraite) &&
            Objects.equals(age, that.age) &&
            Objects.equals(numeroCni, that.numeroCni) &&
            Objects.equals(numeroIpres, that.numeroIpres) &&
            Objects.equals(numeroCss, that.numeroCss) &&
            Objects.equals(groupeSanguin, that.groupeSanguin) &&
            Objects.equals(situationMatrimonial, that.situationMatrimonial) &&
            Objects.equals(sexe, that.sexe) &&
            Objects.equals(disponibilite, that.disponibilite) &&
            Objects.equals(contratId, that.contratId) &&
            Objects.equals(diplomeId, that.diplomeId) &&
            Objects.equals(fonctionId, that.fonctionId) &&
            Objects.equals(membreFamilleId, that.membreFamilleId) &&
            Objects.equals(distinctionId, that.distinctionId) &&
            Objects.equals(operationExterieurId, that.operationExterieurId) &&
            Objects.equals(serviceId, that.serviceId) &&
            Objects.equals(serviceFrequenteId, that.serviceFrequenteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        matricule,
        prenoms,
        nom,
        intituleEmploye,
        dateNaissance,
        lieuNaissance,
        numeroTelephone,
        adresse,
        email,
        dateEmbauchement,
        dateRetraite,
        age,
        numeroCni,
        numeroIpres,
        numeroCss,
        groupeSanguin,
        situationMatrimonial,
        sexe,
        disponibilite,
        contratId,
        diplomeId,
        fonctionId,
        membreFamilleId,
        distinctionId,
        operationExterieurId,
        serviceId,
        serviceFrequenteId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (matricule != null ? "matricule=" + matricule + ", " : "") +
                (prenoms != null ? "prenoms=" + prenoms + ", " : "") +
                (nom != null ? "nom=" + nom + ", " : "") +
                (intituleEmploye != null ? "intituleEmploye=" + intituleEmploye + ", " : "") +
                (dateNaissance != null ? "dateNaissance=" + dateNaissance + ", " : "") +
                (lieuNaissance != null ? "lieuNaissance=" + lieuNaissance + ", " : "") +
                (numeroTelephone != null ? "numeroTelephone=" + numeroTelephone + ", " : "") +
                (adresse != null ? "adresse=" + adresse + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (dateEmbauchement != null ? "dateEmbauchement=" + dateEmbauchement + ", " : "") +
                (dateRetraite != null ? "dateRetraite=" + dateRetraite + ", " : "") +
                (age != null ? "age=" + age + ", " : "") +
                (numeroCni != null ? "numeroCni=" + numeroCni + ", " : "") +
                (numeroIpres != null ? "numeroIpres=" + numeroIpres + ", " : "") +
                (numeroCss != null ? "numeroCss=" + numeroCss + ", " : "") +
                (groupeSanguin != null ? "groupeSanguin=" + groupeSanguin + ", " : "") +
                (situationMatrimonial != null ? "situationMatrimonial=" + situationMatrimonial + ", " : "") +
                (sexe != null ? "sexe=" + sexe + ", " : "") +
                (disponibilite != null ? "disponibilite=" + disponibilite + ", " : "") +
                (contratId != null ? "contratId=" + contratId + ", " : "") +
                (diplomeId != null ? "diplomeId=" + diplomeId + ", " : "") +
                (fonctionId != null ? "fonctionId=" + fonctionId + ", " : "") +
                (membreFamilleId != null ? "membreFamilleId=" + membreFamilleId + ", " : "") +
                (distinctionId != null ? "distinctionId=" + distinctionId + ", " : "") +
                (operationExterieurId != null ? "operationExterieurId=" + operationExterieurId + ", " : "") +
                (serviceId != null ? "serviceId=" + serviceId + ", " : "") +
                (serviceFrequenteId != null ? "serviceFrequenteId=" + serviceFrequenteId + ", " : "") +
            "}";
    }

}
