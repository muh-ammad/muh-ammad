import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GpecmanagerSharedModule } from 'app/shared/shared.module';
import { ServiceAffecteComponent } from './service-affecte.component';
import { ServiceAffecteDetailComponent } from './service-affecte-detail.component';
import { ServiceAffecteUpdateComponent } from './service-affecte-update.component';
import { ServiceAffecteDeleteDialogComponent } from './service-affecte-delete-dialog.component';
import { serviceAffecteRoute } from './service-affecte.route';

@NgModule({
  imports: [GpecmanagerSharedModule, RouterModule.forChild(serviceAffecteRoute)],
  declarations: [
    ServiceAffecteComponent,
    ServiceAffecteDetailComponent,
    ServiceAffecteUpdateComponent,
    ServiceAffecteDeleteDialogComponent,
  ],
  entryComponents: [ServiceAffecteDeleteDialogComponent],
})
export class GpecmanagerServiceAffecteModule {}
