import { ISecteurActivite } from 'app/shared/model/secteur-activite.model';
import { IEmploye } from 'app/shared/model/employe.model';

export interface IServiceAffecte {
  id?: number;
  codeService?: number;
  libelleService?: string;
  secteurActivite?: ISecteurActivite;
  employes?: IEmploye[];
}

export class ServiceAffecte implements IServiceAffecte {
  constructor(
    public id?: number,
    public codeService?: number,
    public libelleService?: string,
    public secteurActivite?: ISecteurActivite,
    public employes?: IEmploye[]
  ) {}
}
