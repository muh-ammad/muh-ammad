import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IServiceAffecte } from 'app/shared/model/service-affecte.model';

type EntityResponseType = HttpResponse<IServiceAffecte>;
type EntityArrayResponseType = HttpResponse<IServiceAffecte[]>;

@Injectable({ providedIn: 'root' })
export class ServiceAffecteService {
  public resourceUrl = SERVER_API_URL + 'api/service-affectes';

  constructor(protected http: HttpClient) {}

  create(serviceAffecte: IServiceAffecte): Observable<EntityResponseType> {
    return this.http.post<IServiceAffecte>(this.resourceUrl, serviceAffecte, { observe: 'response' });
  }

  update(serviceAffecte: IServiceAffecte): Observable<EntityResponseType> {
    return this.http.put<IServiceAffecte>(this.resourceUrl, serviceAffecte, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServiceAffecte>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceAffecte[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
