import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServiceFrequente } from 'app/shared/model/service-frequente.model';

@Component({
  selector: 'jhi-service-frequente-detail',
  templateUrl: './service-frequente-detail.component.html',
})
export class ServiceFrequenteDetailComponent implements OnInit {
  serviceFrequente: IServiceFrequente | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceFrequente }) => (this.serviceFrequente = serviceFrequente));
  }

  previousState(): void {
    window.history.back();
  }
}
