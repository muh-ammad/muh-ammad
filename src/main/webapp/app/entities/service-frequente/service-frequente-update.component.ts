import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IServiceFrequente, ServiceFrequente } from 'app/shared/model/service-frequente.model';
import { ServiceFrequenteService } from './service-frequente.service';

@Component({
  selector: 'jhi-service-frequente-update',
  templateUrl: './service-frequente-update.component.html',
})
export class ServiceFrequenteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codeService: [null, [Validators.required]],
    libelleService: [null, [Validators.required]],
  });

  constructor(
    protected serviceFrequenteService: ServiceFrequenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceFrequente }) => {
      this.updateForm(serviceFrequente);
    });
  }

  updateForm(serviceFrequente: IServiceFrequente): void {
    this.editForm.patchValue({
      id: serviceFrequente.id,
      codeService: serviceFrequente.codeService,
      libelleService: serviceFrequente.libelleService,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const serviceFrequente = this.createFromForm();
    if (serviceFrequente.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceFrequenteService.update(serviceFrequente));
    } else {
      this.subscribeToSaveResponse(this.serviceFrequenteService.create(serviceFrequente));
    }
  }

  private createFromForm(): IServiceFrequente {
    return {
      ...new ServiceFrequente(),
      id: this.editForm.get(['id'])!.value,
      codeService: this.editForm.get(['codeService'])!.value,
      libelleService: this.editForm.get(['libelleService'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceFrequente>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
