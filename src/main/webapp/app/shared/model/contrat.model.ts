import { Moment } from 'moment';
import { IEmploye } from 'app/shared/model/employe.model';
import { NiveauEtude } from 'app/shared/model/enumerations/niveau-etude.model';
import { TypeContrat } from 'app/shared/model/enumerations/type-contrat.model';

export interface IContrat {
  id?: number;
  numeroContrat?: string;
  libelleContrat?: string;
  dateDebut?: Moment;
  dateFin?: Moment;
  niveauEtude?: NiveauEtude;
  typeContrat?: TypeContrat;
  employe?: IEmploye;
}

export class Contrat implements IContrat {
  constructor(
    public id?: number,
    public numeroContrat?: string,
    public libelleContrat?: string,
    public dateDebut?: Moment,
    public dateFin?: Moment,
    public niveauEtude?: NiveauEtude,
    public typeContrat?: TypeContrat,
    public employe?: IEmploye
  ) {}
}
