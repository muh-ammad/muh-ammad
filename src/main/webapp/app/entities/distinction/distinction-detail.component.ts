import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDistinction } from 'app/shared/model/distinction.model';

@Component({
  selector: 'jhi-distinction-detail',
  templateUrl: './distinction-detail.component.html',
})
export class DistinctionDetailComponent implements OnInit {
  distinction: IDistinction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ distinction }) => (this.distinction = distinction));
  }

  previousState(): void {
    window.history.back();
  }
}
