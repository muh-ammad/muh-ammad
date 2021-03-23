import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IServiceFrequente, ServiceFrequente } from 'app/shared/model/service-frequente.model';
import { ServiceFrequenteService } from './service-frequente.service';
import { ServiceFrequenteComponent } from './service-frequente.component';
import { ServiceFrequenteDetailComponent } from './service-frequente-detail.component';
import { ServiceFrequenteUpdateComponent } from './service-frequente-update.component';

@Injectable({ providedIn: 'root' })
export class ServiceFrequenteResolve implements Resolve<IServiceFrequente> {
  constructor(private service: ServiceFrequenteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IServiceFrequente> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((serviceFrequente: HttpResponse<ServiceFrequente>) => {
          if (serviceFrequente.body) {
            return of(serviceFrequente.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ServiceFrequente());
  }
}

export const serviceFrequenteRoute: Routes = [
  {
    path: '',
    component: ServiceFrequenteComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'gpecmanagerApp.serviceFrequente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ServiceFrequenteDetailComponent,
    resolve: {
      serviceFrequente: ServiceFrequenteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gpecmanagerApp.serviceFrequente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ServiceFrequenteUpdateComponent,
    resolve: {
      serviceFrequente: ServiceFrequenteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gpecmanagerApp.serviceFrequente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ServiceFrequenteUpdateComponent,
    resolve: {
      serviceFrequente: ServiceFrequenteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gpecmanagerApp.serviceFrequente.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
