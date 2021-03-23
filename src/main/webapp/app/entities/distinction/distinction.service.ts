import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDistinction } from 'app/shared/model/distinction.model';

type EntityResponseType = HttpResponse<IDistinction>;
type EntityArrayResponseType = HttpResponse<IDistinction[]>;

@Injectable({ providedIn: 'root' })
export class DistinctionService {
  public resourceUrl = SERVER_API_URL + 'api/distinctions';

  constructor(protected http: HttpClient) {}

  create(distinction: IDistinction): Observable<EntityResponseType> {
    return this.http.post<IDistinction>(this.resourceUrl, distinction, { observe: 'response' });
  }

  update(distinction: IDistinction): Observable<EntityResponseType> {
    return this.http.put<IDistinction>(this.resourceUrl, distinction, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDistinction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDistinction[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
