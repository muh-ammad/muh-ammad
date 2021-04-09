import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContrat, Contrat } from 'app/shared/model/contrat.model';
import { ContratService } from './contrat.service';
import { ContratComponent } from './contrat.component';
import { ContratDetailComponent } from './contrat-detail.component';
import { ContratUpdateComponent } from './contrat-update.component';

@Injectable({ providedIn: 'root' })
export class ContratResolve implements Resolve<IContrat> {
  constructor(private service: ContratService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContrat> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contrat: HttpResponse<Contrat>) => {
          if (contrat.body) {
            return of(contrat.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Contrat());
  }
}

export const contratRoute: Routes = [
  {
    path: '',
    component: ContratComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'gpecmanagerApp.contrat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContratDetailComponent,
    resolve: {
      contrat: ContratResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gpecmanagerApp.contrat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContratUpdateComponent,
    resolve: {
      contrat: ContratResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gpecmanagerApp.contrat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContratUpdateComponent,
    resolve: {
      contrat: ContratResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gpecmanagerApp.contrat.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
