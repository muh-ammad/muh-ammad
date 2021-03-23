import { Moment } from 'moment';
import { IContrat } from 'app/shared/model/contrat.model';
import { IDiplome } from 'app/shared/model/diplome.model';
import { IFonction } from 'app/shared/model/fonction.model';
import { IMembreFamille } from 'app/shared/model/membre-famille.model';
import { IDistinction } from 'app/shared/model/distinction.model';
import { IOperationExterieur } from 'app/shared/model/operation-exterieur.model';
import { IServiceAffecte } from 'app/shared/model/service-affecte.model';
import { IServiceFrequente } from 'app/shared/model/service-frequente.model';
import { GroupeSanguin } from 'app/shared/model/enumerations/groupe-sanguin.model';
import { Matrimonial } from 'app/shared/model/enumerations/matrimonial.model';
import { Sexe } from 'app/shared/model/enumerations/sexe.model';
import { Disponibilite } from 'app/shared/model/enumerations/disponibilite.model';

export interface IEmploye {
  id?: number;
  matricule?: string;
  prenoms?: string;
  nom?: string;
  intituleEmploye?: string;
  dateNaissance?: Moment;
  lieuNaissance?: string;
  numeroTelephone?: string;
  adresse?: string;
  photoContentType?: string;
  photo?: any;
  email?: string;
  dateEmbauchement?: Moment;
  dateRetraite?: Moment;
  age?: number;
  numeroCni?: string;
  numeroIpres?: string;
  numeroCss?: string;
  groupeSanguin?: GroupeSanguin;
  situationMatrimonial?: Matrimonial;
  sexe?: Sexe;
  disponibilite?: Disponibilite;
  contrat?: IContrat;
  diplomes?: IDiplome[];
  fonctions?: IFonction[];
  membreFamilles?: IMembreFamille[];
  distinctions?: IDistinction[];
  operationExterieurs?: IOperationExterieur[];
  services?: IServiceAffecte[];
  serviceFrequentes?: IServiceFrequente[];
}

export class Employe implements IEmploye {
  constructor(
    public id?: number,
    public matricule?: string,
    public prenoms?: string,
    public nom?: string,
    public intituleEmploye?: string,
    public dateNaissance?: Moment,
    public lieuNaissance?: string,
    public numeroTelephone?: string,
    public adresse?: string,
    public photoContentType?: string,
    public photo?: any,
    public email?: string,
    public dateEmbauchement?: Moment,
    public dateRetraite?: Moment,
    public age?: number,
    public numeroCni?: string,
    public numeroIpres?: string,
    public numeroCss?: string,
    public groupeSanguin?: GroupeSanguin,
    public situationMatrimonial?: Matrimonial,
    public sexe?: Sexe,
    public disponibilite?: Disponibilite,
    public contrat?: IContrat,
    public diplomes?: IDiplome[],
    public fonctions?: IFonction[],
    public membreFamilles?: IMembreFamille[],
    public distinctions?: IDistinction[],
    public operationExterieurs?: IOperationExterieur[],
    public services?: IServiceAffecte[],
    public serviceFrequentes?: IServiceFrequente[]
  ) {}
}
