import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceFrequente } from 'app/shared/model/service-frequente.model';
import { ServiceFrequenteService } from './service-frequente.service';

@Component({
  templateUrl: './service-frequente-delete-dialog.component.html',
})
export class ServiceFrequenteDeleteDialogComponent {
  serviceFrequente?: IServiceFrequente;

  constructor(
    protected serviceFrequenteService: ServiceFrequenteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.serviceFrequenteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('serviceFrequenteListModification');
      this.activeModal.close();
    });
  }
}
