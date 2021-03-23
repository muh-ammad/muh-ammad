import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOperationExterieur } from 'app/shared/model/operation-exterieur.model';

@Component({
  selector: 'jhi-operation-exterieur-detail',
  templateUrl: './operation-exterieur-detail.component.html',
})
export class OperationExterieurDetailComponent implements OnInit {
  operationExterieur: IOperationExterieur | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operationExterieur }) => (this.operationExterieur = operationExterieur));
  }

  previousState(): void {
    window.history.back();
  }
}
