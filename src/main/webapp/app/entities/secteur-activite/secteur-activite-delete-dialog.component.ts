import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISecteurActivite } from 'app/shared/model/secteur-activite.model';
import { SecteurActiviteService } from './secteur-activite.service';

@Component({
  templateUrl: './secteur-activite-delete-dialog.component.html',
})
export class SecteurActiviteDeleteDialogComponent {
  secteurActivite?: ISecteurActivite;

  constructor(
    protected secteurActiviteService: SecteurActiviteService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.secteurActiviteService.delete(id).subscribe(() => {
      this.eventManager.broadcast('secteurActiviteListModification');
      this.activeModal.close();
    });
  }
}
