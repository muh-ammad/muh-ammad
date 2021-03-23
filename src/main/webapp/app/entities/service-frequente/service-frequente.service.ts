import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IServiceFrequente } from 'app/shared/model/service-frequente.model';

type EntityResponseType = HttpResponse<IServiceFrequente>;
type EntityArrayResponseType = HttpResponse<IServiceFrequente[]>;

@Injectable({ providedIn: 'root' })
export class ServiceFrequenteService {
  public resourceUrl = SERVER_API_URL + 'api/service-frequentes';

  constructor(protected http: HttpClient) {}

  create(serviceFrequente: IServiceFrequente): Observable<EntityResponseType> {
    return this.http.post<IServiceFrequente>(this.resourceUrl, serviceFrequente, { observe: 'response' });
  }

  update(serviceFrequente: IServiceFrequente): Observable<EntityResponseType> {
    return this.http.put<IServiceFrequente>(this.resourceUrl, serviceFrequente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServiceFrequente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServiceFrequente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
