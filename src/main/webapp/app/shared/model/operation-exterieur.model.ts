import { Moment } from 'moment';
import { IEmploye } from 'app/shared/model/employe.model';

export interface IOperationExterieur {
  id?: number;
  lieuOpex?: string;
  anneeOpex?: Moment;
  employe?: IEmploye;
}

export class OperationExterieur implements IOperationExterieur {
  constructor(public id?: number, public lieuOpex?: string, public anneeOpex?: Moment, public employe?: IEmploye) {}
}
