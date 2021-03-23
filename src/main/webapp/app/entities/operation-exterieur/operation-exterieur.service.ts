import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOperationExterieur } from 'app/shared/model/operation-exterieur.model';

type EntityResponseType = HttpResponse<IOperationExterieur>;
type EntityArrayResponseType = HttpResponse<IOperationExterieur[]>;

@Injectable({ providedIn: 'root' })
export class OperationExterieurService {
  public resourceUrl = SERVER_API_URL + 'api/operation-exterieurs';

  constructor(protected http: HttpClient) {}

  create(operationExterieur: IOperationExterieur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(operationExterieur);
    return this.http
      .post<IOperationExterieur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(operationExterieur: IOperationExterieur): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(operationExterieur);
    return this.http
      .put<IOperationExterieur>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOperationExterieur>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOperationExterieur[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(operationExterieur: IOperationExterieur): IOperationExterieur {
    const copy: IOperationExterieur = Object.assign({}, operationExterieur, {
      anneeOpex: operationExterieur.anneeOpex && operationExterieur.anneeOpex.isValid() ? operationExterieur.anneeOpex.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.anneeOpex = res.body.anneeOpex ? moment(res.body.anneeOpex) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((operationExterieur: IOperationExterieur) => {
        operationExterieur.anneeOpex = operationExterieur.anneeOpex ? moment(operationExterieur.anneeOpex) : undefined;
      });
    }
    return res;
  }
}
