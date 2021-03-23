import { Moment } from 'moment';
import { IEmploye } from 'app/shared/model/employe.model';

export interface IMembreFamille {
  id?: number;
  prenoms?: string;
  nom?: string;
  dateNaissance?: Moment;
  lieuNaissance?: string;
  epoux?: boolean;
  epouse?: boolean;
  enfant?: boolean;
  employe?: IEmploye;
}

export class MembreFamille implements IMembreFamille {
  constructor(
    public id?: number,
    public prenoms?: string,
    public nom?: string,
    public dateNaissance?: Moment,
    public lieuNaissance?: string,
    public epoux?: boolean,
    public epouse?: boolean,
    public enfant?: boolean,
    public employe?: IEmploye
  ) {
    this.epoux = this.epoux || false;
    this.epouse = this.epouse || false;
    this.enfant = this.enfant || false;
  }
}
