import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceAffecte } from 'app/shared/model/service-affecte.model';
import { ServiceAffecteService } from './service-affecte.service';

@Component({
  templateUrl: './service-affecte-delete-dialog.component.html',
})
export class ServiceAffecteDeleteDialogComponent {
  serviceAffecte?: IServiceAffecte;

  constructor(
    protected serviceAffecteService: ServiceAffecteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.serviceAffecteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('serviceAffecteListModification');
      this.activeModal.close();
    });
  }
}
