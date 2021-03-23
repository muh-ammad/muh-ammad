import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDistinction, Distinction } from 'app/shared/model/distinction.model';
import { DistinctionService } from './distinction.service';
import { DistinctionComponent } from './distinction.component';
import { DistinctionDetailComponent } from './distinction-detail.component';
import { DistinctionUpdateComponent } from './distinction-update.component';

@Injectable({ providedIn: 'root' })
export class DistinctionResolve implements Resolve<IDistinction> {
  constructor(private service: DistinctionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDistinction> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((distinction: HttpResponse<Distinction>) => {
          if (distinction.body) {
            return of(distinction.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Distinction());
  }
}

export const distinctionRoute: Routes = [
  {
    path: '',
    component: DistinctionComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'gpecmanagerApp.distinction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: DistinctionDetailComponent,
    resolve: {
      distinction: DistinctionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gpecmanagerApp.distinction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: DistinctionUpdateComponent,
    resolve: {
      distinction: DistinctionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gpecmanagerApp.distinction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: DistinctionUpdateComponent,
    resolve: {
      distinction: DistinctionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gpecmanagerApp.distinction.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
