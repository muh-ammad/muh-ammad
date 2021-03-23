import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GpecmanagerSharedModule } from 'app/shared/shared.module';
import { DistinctionComponent } from './distinction.component';
import { DistinctionDetailComponent } from './distinction-detail.component';
import { DistinctionUpdateComponent } from './distinction-update.component';
import { DistinctionDeleteDialogComponent } from './distinction-delete-dialog.component';
import { distinctionRoute } from './distinction.route';

@NgModule({
  imports: [GpecmanagerSharedModule, RouterModule.forChild(distinctionRoute)],
  declarations: [DistinctionComponent, DistinctionDetailComponent, DistinctionUpdateComponent, DistinctionDeleteDialogComponent],
  entryComponents: [DistinctionDeleteDialogComponent],
})
export class GpecmanagerDistinctionModule {}
