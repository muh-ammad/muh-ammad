import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceAffecte } from 'app/shared/model/service-affecte.model';

@Component({
  selector: 'jhi-service-affecte-detail',
  templateUrl: './service-affecte-detail.component.html',
})
export class ServiceAffecteDetailComponent implements OnInit {
  serviceAffecte: IServiceAffecte | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceAffecte }) => (this.serviceAffecte = serviceAffecte));
  }

  previousState(): void {
    window.history.back();
  }
}
