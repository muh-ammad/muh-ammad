import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISpecialite } from 'app/shared/model/specialite.model';

type EntityResponseType = HttpResponse<ISpecialite>;
type EntityArrayResponseType = HttpResponse<ISpecialite[]>;

@Injectable({ providedIn: 'root' })
export class SpecialiteService {
  public resourceUrl = SERVER_API_URL + 'api/specialites';

  constructor(protected http: HttpClient) {}

  create(specialite: ISpecialite): Observable<EntityResponseType> {
    return this.http.post<ISpecialite>(this.resourceUrl, specialite, { observe: 'response' });
  }

  update(specialite: ISpecialite): Observable<EntityResponseType> {
    return this.http.put<ISpecialite>(this.resourceUrl, specialite, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISpecialite>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISpecialite[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
