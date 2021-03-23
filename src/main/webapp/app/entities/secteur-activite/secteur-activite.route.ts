import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISecteurActivite, SecteurActivite } from 'app/shared/model/secteur-activite.model';
import { SecteurActiviteService } from './secteur-activite.service';
import { SecteurActiviteComponent } from './secteur-activite.component';
import { SecteurActiviteDetailComponent } from './secteur-activite-detail.component';
import { SecteurActiviteUpdateComponent } from './secteur-activite-update.component';

@Injectable({ providedIn: 'root' })
export class SecteurActiviteResolve implements Resolve<ISecteurActivite> {
  constructor(private service: SecteurActiviteService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISecteurActivite> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((secteurActivite: HttpResponse<SecteurActivite>) => {
          if (secteurActivite.body) {
            return of(secteurActivite.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SecteurActivite());
  }
}

export const secteurActiviteRoute: Routes = [
  {
    path: '',
    component: SecteurActiviteComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gpecmanagerApp.secteurActivite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SecteurActiviteDetailComponent,
    resolve: {
      secteurActivite: SecteurActiviteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gpecmanagerApp.secteurActivite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SecteurActiviteUpdateComponent,
    resolve: {
      secteurActivite: SecteurActiviteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gpecmanagerApp.secteurActivite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SecteurActiviteUpdateComponent,
    resolve: {
      secteurActivite: SecteurActiviteResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'gpecmanagerApp.secteurActivite.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
