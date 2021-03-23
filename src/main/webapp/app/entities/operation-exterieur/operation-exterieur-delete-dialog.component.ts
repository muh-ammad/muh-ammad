import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOperationExterieur } from 'app/shared/model/operation-exterieur.model';
import { OperationExterieurService } from './operation-exterieur.service';

@Component({
  templateUrl: './operation-exterieur-delete-dialog.component.html',
})
export class OperationExterieurDeleteDialogComponent {
  operationExterieur?: IOperationExterieur;

  constructor(
    protected operationExterieurService: OperationExterieurService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.operationExterieurService.delete(id).subscribe(() => {
      this.eventManager.broadcast('operationExterieurListModification');
      this.activeModal.close();
    });
  }
}
