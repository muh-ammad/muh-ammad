import { Route } from '@angular/router';
import { DashboardComponent } from 'app/shared/statistics/dashboard/dashboard.component';

import { HomeComponent } from './home.component';

export const HOME_ROUTE: Route = {
  path: '',
  component: HomeComponent,
  data: {
    authorities: [],
    pageTitle: 'home.title',
  },
  children:[{
    path:'',
    component:DashboardComponent
  }]
};
