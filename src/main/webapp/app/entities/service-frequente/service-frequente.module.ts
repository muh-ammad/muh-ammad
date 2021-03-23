import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GpecmanagerSharedModule } from 'app/shared/shared.module';
import { ServiceFrequenteComponent } from './service-frequente.component';
import { ServiceFrequenteDetailComponent } from './service-frequente-detail.component';
import { ServiceFrequenteUpdateComponent } from './service-frequente-update.component';
import { ServiceFrequenteDeleteDialogComponent } from './service-frequente-delete-dialog.component';
import { serviceFrequenteRoute } from './service-frequente.route';

@NgModule({
  imports: [GpecmanagerSharedModule, RouterModule.forChild(serviceFrequenteRoute)],
  declarations: [
    ServiceFrequenteComponent,
    ServiceFrequenteDetailComponent,
    ServiceFrequenteUpdateComponent,
    ServiceFrequenteDeleteDialogComponent,
  ],
  entryComponents: [ServiceFrequenteDeleteDialogComponent],
})
export class GpecmanagerServiceFrequenteModule {}
