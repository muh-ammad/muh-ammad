import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDiplome } from 'app/shared/model/diplome.model';

type EntityResponseType = HttpResponse<IDiplome>;
type EntityArrayResponseType = HttpResponse<IDiplome[]>;

@Injectable({ providedIn: 'root' })
export class DiplomeService {
  public resourceUrl = SERVER_API_URL + 'api/diplomes';

  constructor(protected http: HttpClient) {}

  create(diplome: IDiplome): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(diplome);
    return this.http
      .post<IDiplome>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(diplome: IDiplome): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(diplome);
    return this.http
      .put<IDiplome>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDiplome>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDiplome[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(diplome: IDiplome): IDiplome {
    const copy: IDiplome = Object.assign({}, diplome, {
      anneeDiplome: diplome.anneeDiplome && diplome.anneeDiplome.isValid() ? diplome.anneeDiplome.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.anneeDiplome = res.body.anneeDiplome ? moment(res.body.anneeDiplome) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((diplome: IDiplome) => {
        diplome.anneeDiplome = diplome.anneeDiplome ? moment(diplome.anneeDiplome) : undefined;
      });
    }
    return res;
  }
}
