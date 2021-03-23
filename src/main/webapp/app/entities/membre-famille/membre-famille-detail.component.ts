import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMembreFamille } from 'app/shared/model/membre-famille.model';

@Component({
  selector: 'jhi-membre-famille-detail',
  templateUrl: './membre-famille-detail.component.html',
})
export class MembreFamilleDetailComponent implements OnInit {
  membreFamille: IMembreFamille | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ membreFamille }) => (this.membreFamille = membreFamille));
  }

  previousState(): void {
    window.history.back();
  }
}
