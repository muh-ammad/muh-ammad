import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmploye } from 'app/shared/model/employe.model';

type EntityResponseType = HttpResponse<IEmploye>;
type EntityArrayResponseType = HttpResponse<IEmploye[]>;

@Injectable({ providedIn: 'root' })
export class EmployeService {
  public resourceUrl = SERVER_API_URL + 'api/employes';

  constructor(protected http: HttpClient) {}

  create(employe: IEmploye): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employe);
    return this.http
      .post<IEmploye>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(employe: IEmploye): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employe);
    return this.http
      .put<IEmploye>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IEmploye>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IEmploye[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(employe: IEmploye): IEmploye {
    const copy: IEmploye = Object.assign({}, employe, {
      dateNaissance: employe.dateNaissance && employe.dateNaissance.isValid() ? employe.dateNaissance.toJSON() : undefined,
      dateEmbauchement: employe.dateEmbauchement && employe.dateEmbauchement.isValid() ? employe.dateEmbauchement.toJSON() : undefined,
      dateRetraite: employe.dateRetraite && employe.dateRetraite.isValid() ? employe.dateRetraite.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateNaissance = res.body.dateNaissance ? moment(res.body.dateNaissance) : undefined;
      res.body.dateEmbauchement = res.body.dateEmbauchement ? moment(res.body.dateEmbauchement) : undefined;
      res.body.dateRetraite = res.body.dateRetraite ? moment(res.body.dateRetraite) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((employe: IEmploye) => {
        employe.dateNaissance = employe.dateNaissance ? moment(employe.dateNaissance) : undefined;
        employe.dateEmbauchement = employe.dateEmbauchement ? moment(employe.dateEmbauchement) : undefined;
        employe.dateRetraite = employe.dateRetraite ? moment(employe.dateRetraite) : undefined;
      });
    }
    return res;
  }
}
