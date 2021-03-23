import { IFonction } from 'app/shared/model/fonction.model';

export interface ISpecialite {
  id?: number;
  libelleSpecialite?: string;
  fonction?: IFonction;
}

export class Specialite implements ISpecialite {
  constructor(public id?: number, public libelleSpecialite?: string, public fonction?: IFonction) {}
}
