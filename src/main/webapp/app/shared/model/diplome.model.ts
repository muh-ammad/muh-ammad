import { Moment } from 'moment';
import { IEmploye } from 'app/shared/model/employe.model';

export interface IDiplome {
  id?: number;
  libelleDiplome?: string;
  anneeDiplome?: Moment;
  employe?: IEmploye;
}

export class Diplome implements IDiplome {
  constructor(public id?: number, public libelleDiplome?: string, public anneeDiplome?: Moment, public employe?: IEmploye) {}
}
