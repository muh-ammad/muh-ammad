import { IEmploye } from 'app/shared/model/employe.model';

export interface IDistinction {
  id?: number;
  libelleDistinction?: string;
  employe?: IEmploye;
}

export class Distinction implements IDistinction {
  constructor(public id?: number, public libelleDistinction?: string, public employe?: IEmploye) {}
}
