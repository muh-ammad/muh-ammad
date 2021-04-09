import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GpecmanagerSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { DashboardModule } from 'app/shared/statistics/dashboard/dashboard.module';

@NgModule({
  imports: [GpecmanagerSharedModule, DashboardModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent],
})
export class GpecmanagerHomeModule {}
