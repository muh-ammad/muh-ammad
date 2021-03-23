import { ISpecialite } from 'app/shared/model/specialite.model';
import { IEmploye } from 'app/shared/model/employe.model';

export interface IFonction {
  id?: number;
  libelleFonction?: string;
  specialites?: ISpecialite[];
  employe?: IEmploye;
}

export class Fonction implements IFonction {
  constructor(public id?: number, public libelleFonction?: string, public specialites?: ISpecialite[], public employe?: IEmploye) {}
}
