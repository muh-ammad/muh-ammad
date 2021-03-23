import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GpecmanagerSharedModule } from 'app/shared/shared.module';
import { OperationExterieurComponent } from './operation-exterieur.component';
import { OperationExterieurDetailComponent } from './operation-exterieur-detail.component';
import { OperationExterieurUpdateComponent } from './operation-exterieur-update.component';
import { OperationExterieurDeleteDialogComponent } from './operation-exterieur-delete-dialog.component';
import { operationExterieurRoute } from './operation-exterieur.route';

@NgModule({
  imports: [GpecmanagerSharedModule, RouterModule.forChild(operationExterieurRoute)],
  declarations: [
    OperationExterieurComponent,
    OperationExterieurDetailComponent,
    OperationExterieurUpdateComponent,
    OperationExterieurDeleteDialogComponent,
  ],
  entryComponents: [OperationExterieurDeleteDialogComponent],
})
export class GpecmanagerOperationExterieurModule {}
