export interface ISecteurActivite {
  id?: number;
  libelleActivite?: string;
}

export class SecteurActivite implements ISecteurActivite {
  constructor(public id?: number, public libelleActivite?: string) {}
}
