import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IServiceAffecte, ServiceAffecte } from 'app/shared/model/service-affecte.model';
import { ServiceAffecteService } from './service-affecte.service';
import { ServiceAffecteComponent } from './service-affecte.component';
import { ServiceAffecteDetailComponent } from './service-affecte-detail.component';
import { ServiceAffecteUpdateComponent } from './service-affecte-update.component';

@Injectable({ providedIn: 'root' })
export class ServiceAffecteResolve implements Resolve<IServiceAffecte> {
  constructor(private service: ServiceAffecteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IServiceAffecte> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((serviceAffecte: HttpResponse<ServiceAffecte>) => {
          if (serviceAffecte.body) {
            return of(serviceAffecte.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ServiceAffecte());
  }
}

export const serviceAffecteRoute: Routes = [
  {
    path: '',
    component: ServiceAffecteComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'gpecmanagerApp.serviceAffecte.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServiceAffecteDetailComponent,
    resolve: {
      serviceAffecte: ServiceAffecteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gpecmanagerApp.serviceAffecte.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServiceAffecteUpdateComponent,
    resolve: {
      serviceAffecte: ServiceAffecteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gpecmanagerApp.serviceAffecte.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServiceAffecteUpdateComponent,
    resolve: {
      serviceAffecte: ServiceAffecteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gpecmanagerApp.serviceAffecte.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
