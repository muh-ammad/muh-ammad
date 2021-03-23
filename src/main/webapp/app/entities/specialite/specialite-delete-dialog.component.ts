import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpecialite } from 'app/shared/model/specialite.model';
import { SpecialiteService } from './specialite.service';

@Component({
  templateUrl: './specialite-delete-dialog.component.html',
})
export class SpecialiteDeleteDialogComponent {
  specialite?: ISpecialite;

  constructor(
    protected specialiteService: SpecialiteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.specialiteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('specialiteListModification');
      this.activeModal.close();
    });
  }
}
