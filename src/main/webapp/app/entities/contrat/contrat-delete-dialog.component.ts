import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContrat } from 'app/shared/model/contrat.model';
import { ContratService } from './contrat.service';

@Component({
  templateUrl: './contrat-delete-dialog.component.html',
})
export class ContratDeleteDialogComponent {
  contrat?: IContrat;

  constructor(protected contratService: ContratService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contratService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contratListModification');
      this.activeModal.close();
    });
  }
}
