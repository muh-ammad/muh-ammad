import { IEmploye } from 'app/shared/model/employe.model';

export interface IServiceFrequente {
  id?: number;
  codeService?: number;
  libelleService?: string;
  employes?: IEmploye[];
}

export class ServiceFrequente implements IServiceFrequente {
  constructor(public id?: number, public codeService?: number, public libelleService?: string, public employes?: IEmploye[]) {}
}
