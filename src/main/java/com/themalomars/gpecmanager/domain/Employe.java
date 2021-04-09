package com.themalomars.gpecmanager.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.themalomars.gpecmanager.domain.enumeration.GroupeSanguin;

import com.themalomars.gpecmanager.domain.enumeration.Matrimonial;

import com.themalomars.gpecmanager.domain.enumeration.Sexe;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.themalomars.gpecmanager.domain.enumeration.Disponibilite;

/**
 * A Employe.
 */
@Entity
@Table(name = "employe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "matricule", nullable = false)
    private String matricule;

    @NotNull
    @Column(name = "prenoms", nullable = false)
    private String prenoms;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "intitule_employe")
    private String intituleEmploye;

    @NotNull
    @Column(name = "date_naissance", nullable = false)
    private Instant dateNaissance;

    @NotNull
    @Column(name = "lieu_naissance", nullable = false)
    private String lieuNaissance;

    @NotNull
    @Column(name = "numero_telephone", nullable = false)
    private String numeroTelephone;

    @Column(name = "adresse")
    private String adresse;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Column(name = "email")
    private String email;

    @Column(name = "date_embauchement")
    private Instant dateEmbauchement;

    @Column(name = "date_retraite")
    private Instant dateRetraite;

    @Column(name = "age")
    private Long age;

    @Column(name = "numero_cni")
    private String numeroCni;

    @Column(name = "numero_ipres")
    private String numeroIpres;

    @Column(name = "numero_css")
    private String numeroCss;

    @Enumerated(EnumType.STRING)
    @Column(name = "groupe_sanguin")
    private GroupeSanguin groupeSanguin;

    @Enumerated(EnumType.STRING)
    @Column(name = "situation_matrimonial")
    private Matrimonial situationMatrimonial;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe")
    private Sexe sexe;

    @Enumerated(EnumType.STRING)
    @Column(name = "disponibilite")
    private Disponibilite disponibilite;
    
    @JsonIgnore
    @OneToOne
    @JoinColumn(unique = true)
    private Contrat contrat;

    @OneToMany(mappedBy = "employe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Diplome> diplomes = new HashSet<>();

    @OneToMany(mappedBy = "employe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Fonction> fonctions = new HashSet<>();

    @OneToMany(mappedBy = "employe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MembreFamille> membreFamilles = new HashSet<>();

    @OneToMany(mappedBy = "employe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Distinction> distinctions = new HashSet<>();

    @OneToMany(mappedBy = "employe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<OperationExterieur> operationExterieurs = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "employe_service", joinColumns = @JoinColumn(name = "employe_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    private Set<ServiceAffecte> services = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "employe_service_frequente", joinColumns = @JoinColumn(name = "employe_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "service_frequente_id", referencedColumnName = "id"))
    private Set<ServiceFrequente> serviceFrequentes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public Employe matricule(String matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public Employe prenoms(String prenoms) {
        this.prenoms = prenoms;
        return this;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getNom() {
        return nom;
    }

    public Employe nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIntituleEmploye() {
        return intituleEmploye;
    }

    public Employe intituleEmploye(String intituleEmploye) {
        this.intituleEmploye = intituleEmploye;
        return this;
    }

    public void setIntituleEmploye(String intituleEmploye) {
        this.intituleEmploye = intituleEmploye;
    }

    public Instant getDateNaissance() {
        return dateNaissance;
    }

    public Employe dateNaissance(Instant dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(Instant dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public Employe lieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
        return this;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public Employe numeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
        return this;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public Employe adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public Employe photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public Employe photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getEmail() {
        return email;
    }

    public Employe email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getDateEmbauchement() {
        return dateEmbauchement;
    }

    public Employe dateEmbauchement(Instant dateEmbauchement) {
        this.dateEmbauchement = dateEmbauchement;
        return this;
    }

    public void setDateEmbauchement(Instant dateEmbauchement) {
        this.dateEmbauchement = dateEmbauchement;
    }

    public Instant getDateRetraite() {
        return dateRetraite;
    }

    public Employe dateRetraite(Instant dateRetraite) {
        this.dateRetraite = dateRetraite;
        return this;
    }

    public void setDateRetraite(Instant dateRetraite) {
        this.dateRetraite = dateRetraite;
    }

    public Long getAge() {
        return age;
    }

    public Employe age(Long age) {
        this.age = age;
        return this;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getNumeroCni() {
        return numeroCni;
    }

    public Employe numeroCni(String numeroCni) {
        this.numeroCni = numeroCni;
        return this;
    }

    public void setNumeroCni(String numeroCni) {
        this.numeroCni = numeroCni;
    }

    public String getNumeroIpres() {
        return numeroIpres;
    }

    public Employe numeroIpres(String numeroIpres) {
        this.numeroIpres = numeroIpres;
        return this;
    }

    public void setNumeroIpres(String numeroIpres) {
        this.numeroIpres = numeroIpres;
    }

    public String getNumeroCss() {
        return numeroCss;
    }

    public Employe numeroCss(String numeroCss) {
        this.numeroCss = numeroCss;
        return this;
    }

    public void setNumeroCss(String numeroCss) {
        this.numeroCss = numeroCss;
    }

    public GroupeSanguin getGroupeSanguin() {
        return groupeSanguin;
    }

    public Employe groupeSanguin(GroupeSanguin groupeSanguin) {
        this.groupeSanguin = groupeSanguin;
        return this;
    }

    public void setGroupeSanguin(GroupeSanguin groupeSanguin) {
        this.groupeSanguin = groupeSanguin;
    }

    public Matrimonial getSituationMatrimonial() {
        return situationMatrimonial;
    }

    public Employe situationMatrimonial(Matrimonial situationMatrimonial) {
        this.situationMatrimonial = situationMatrimonial;
        return this;
    }

    public void setSituationMatrimonial(Matrimonial situationMatrimonial) {
        this.situationMatrimonial = situationMatrimonial;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public Employe sexe(Sexe sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Disponibilite getDisponibilite() {
        return disponibilite;
    }

    public Employe disponibilite(Disponibilite disponibilite) {
        this.disponibilite = disponibilite;
        return this;
    }

    public void setDisponibilite(Disponibilite disponibilite) {
        this.disponibilite = disponibilite;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public Employe contrat(Contrat contrat) {
        this.contrat = contrat;
        return this;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }

    public Set<Diplome> getDiplomes() {
        return diplomes;
    }

    public Employe diplomes(Set<Diplome> diplomes) {
        this.diplomes = diplomes;
        return this;
    }

    public Employe addDiplome(Diplome diplome) {
        this.diplomes.add(diplome);
        diplome.setEmploye(this);
        return this;
    }

    public Employe removeDiplome(Diplome diplome) {
        this.diplomes.remove(diplome);
        diplome.setEmploye(null);
        return this;
    }

    public void setDiplomes(Set<Diplome> diplomes) {
        this.diplomes = diplomes;
    }

    public Set<Fonction> getFonctions() {
        return fonctions;
    }

    public Employe fonctions(Set<Fonction> fonctions) {
        this.fonctions = fonctions;
        return this;
    }

    public Employe addFonction(Fonction fonction) {
        this.fonctions.add(fonction);
        fonction.setEmploye(this);
        return this;
    }

    public Employe removeFonction(Fonction fonction) {
        this.fonctions.remove(fonction);
        fonction.setEmploye(null);
        return this;
    }

    public void setFonctions(Set<Fonction> fonctions) {
        this.fonctions = fonctions;
    }

    public Set<MembreFamille> getMembreFamilles() {
        return membreFamilles;
    }

    public Employe membreFamilles(Set<MembreFamille> membreFamilles) {
        this.membreFamilles = membreFamilles;
        return this;
    }

    public Employe addMembreFamille(MembreFamille membreFamille) {
        this.membreFamilles.add(membreFamille);
        membreFamille.setEmploye(this);
        return this;
    }

    public Employe removeMembreFamille(MembreFamille membreFamille) {
        this.membreFamilles.remove(membreFamille);
        membreFamille.setEmploye(null);
        return this;
    }

    public void setMembreFamilles(Set<MembreFamille> membreFamilles) {
        this.membreFamilles = membreFamilles;
    }

    public Set<Distinction> getDistinctions() {
        return distinctions;
    }

    public Employe distinctions(Set<Distinction> distinctions) {
        this.distinctions = distinctions;
        return this;
    }

    public Employe addDistinction(Distinction distinction) {
        this.distinctions.add(distinction);
        distinction.setEmploye(this);
        return this;
    }

    public Employe removeDistinction(Distinction distinction) {
        this.distinctions.remove(distinction);
        distinction.setEmploye(null);
        return this;
    }

    public void setDistinctions(Set<Distinction> distinctions) {
        this.distinctions = distinctions;
    }

    public Set<OperationExterieur> getOperationExterieurs() {
        return operationExterieurs;
    }

    public Employe operationExterieurs(Set<OperationExterieur> operationExterieurs) {
        this.operationExterieurs = operationExterieurs;
        return this;
    }

    public Employe addOperationExterieur(OperationExterieur operationExterieur) {
        this.operationExterieurs.add(operationExterieur);
        operationExterieur.setEmploye(this);
        return this;
    }

    public Employe removeOperationExterieur(OperationExterieur operationExterieur) {
        this.operationExterieurs.remove(operationExterieur);
        operationExterieur.setEmploye(null);
        return this;
    }

    public void setOperationExterieurs(Set<OperationExterieur> operationExterieurs) {
        this.operationExterieurs = operationExterieurs;
    }

    public Set<ServiceAffecte> getServices() {
        return services;
    }

    public Employe services(Set<ServiceAffecte> serviceAffectes) {
        this.services = serviceAffectes;
        return this;
    }

    public Employe addService(ServiceAffecte serviceAffecte) {
        this.services.add(serviceAffecte);
        serviceAffecte.getEmployes().add(this);
        return this;
    }

    public Employe removeService(ServiceAffecte serviceAffecte) {
        this.services.remove(serviceAffecte);
        serviceAffecte.getEmployes().remove(this);
        return this;
    }

    public void setServices(Set<ServiceAffecte> serviceAffectes) {
        this.services = serviceAffectes;
    }

    public Set<ServiceFrequente> getServiceFrequentes() {
        return serviceFrequentes;
    }

    public Employe serviceFrequentes(Set<ServiceFrequente> serviceFrequentes) {
        this.serviceFrequentes = serviceFrequentes;
        return this;
    }

    public Employe addServiceFrequente(ServiceFrequente serviceFrequente) {
        this.serviceFrequentes.add(serviceFrequente);
        serviceFrequente.getEmployes().add(this);
        return this;
    }

    public Employe removeServiceFrequente(ServiceFrequente serviceFrequente) {
        this.serviceFrequentes.remove(serviceFrequente);
        serviceFrequente.getEmployes().remove(this);
        return this;
    }

    public void setServiceFrequentes(Set<ServiceFrequente> serviceFrequentes) {
        this.serviceFrequentes = serviceFrequentes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employe)) {
            return false;
        }
        return id != null && id.equals(((Employe) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employe{" + "id=" + getId() + ", matricule='" + getMatricule() + "'" + ", prenoms='" + getPrenoms()
                + "'" + ", nom='" + getNom() + "'" + ", intituleEmploye='" + getIntituleEmploye() + "'"
                + ", dateNaissance='" + getDateNaissance() + "'" + ", lieuNaissance='" + getLieuNaissance() + "'"
                + ", numeroTelephone='" + getNumeroTelephone() + "'" + ", adresse='" + getAdresse() + "'" + ", photo='"
                + getPhoto() + "'" + ", photoContentType='" + getPhotoContentType() + "'" + ", email='" + getEmail()
                + "'" + ", dateEmbauchement='" + getDateEmbauchement() + "'" + ", dateRetraite='" + getDateRetraite()
                + "'" + ", age=" + getAge() + ", numeroCni='" + getNumeroCni() + "'" + ", numeroIpres='"
                + getNumeroIpres() + "'" + ", numeroCss='" + getNumeroCss() + "'" + ", groupeSanguin='"
                + getGroupeSanguin() + "'" + ", situationMatrimonial='" + getSituationMatrimonial() + "'" + ", sexe='"
                + getSexe() + "'" + ", disponibilite='" + getDisponibilite() + "'" + "}";
    }
}
