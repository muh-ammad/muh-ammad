import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISecteurActivite } from 'app/shared/model/secteur-activite.model';

@Component({
  selector: 'jhi-secteur-activite-detail',
  templateUrl: './secteur-activite-detail.component.html',
})
export class SecteurActiviteDetailComponent implements OnInit {
  secteurActivite: ISecteurActivite | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ secteurActivite }) => (this.secteurActivite = secteurActivite));
  }

  previousState(): void {
    window.history.back();
  }
}
