import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDistinction } from 'app/shared/model/distinction.model';
import { DistinctionService } from './distinction.service';

@Component({
  templateUrl: './distinction-delete-dialog.component.html',
})
export class DistinctionDeleteDialogComponent {
  distinction?: IDistinction;

  constructor(
    protected distinctionService: DistinctionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.distinctionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('distinctionListModification');
      this.activeModal.close();
    });
  }
}
