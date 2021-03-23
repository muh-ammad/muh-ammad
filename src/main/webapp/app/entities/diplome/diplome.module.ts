import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GpecmanagerSharedModule } from 'app/shared/shared.module';
import { DiplomeComponent } from './diplome.component';
import { DiplomeDetailComponent } from './diplome-detail.component';
import { DiplomeUpdateComponent } from './diplome-update.component';
import { DiplomeDeleteDialogComponent } from './diplome-delete-dialog.component';
import { diplomeRoute } from './diplome.route';

@NgModule({
  imports: [GpecmanagerSharedModule, RouterModule.forChild(diplomeRoute)],
  declarations: [DiplomeComponent, DiplomeDetailComponent, DiplomeUpdateComponent, DiplomeDeleteDialogComponent],
  entryComponents: [DiplomeDeleteDialogComponent],
})
export class GpecmanagerDiplomeModule {}
