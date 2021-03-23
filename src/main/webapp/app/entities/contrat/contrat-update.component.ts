import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IContrat, Contrat } from 'app/shared/model/contrat.model';
import { ContratService } from './contrat.service';

@Component({
  selector: 'jhi-contrat-update',
  templateUrl: './contrat-update.component.html',
})
export class ContratUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    numeroContrat: [null, [Validators.required]],
    libelleContrat: [null, [Validators.required]],
    dateDebut: [null, [Validators.required]],
    dateFin: [null, [Validators.required]],
    niveauEtude: [null, [Validators.required]],
    typeContrat: [null, [Validators.required]],
  });

  constructor(protected contratService: ContratService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contrat }) => {
      if (!contrat.id) {
        const today = moment().startOf('day');
        contrat.dateDebut = today;
        contrat.dateFin = today;
      }

      this.updateForm(contrat);
    });
  }

  updateForm(contrat: IContrat): void {
    this.editForm.patchValue({
      id: contrat.id,
      numeroContrat: contrat.numeroContrat,
      libelleContrat: contrat.libelleContrat,
      dateDebut: contrat.dateDebut ? contrat.dateDebut.format(DATE_TIME_FORMAT) : null,
      dateFin: contrat.dateFin ? contrat.dateFin.format(DATE_TIME_FORMAT) : null,
      niveauEtude: contrat.niveauEtude,
      typeContrat: contrat.typeContrat,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contrat = this.createFromForm();
    if (contrat.id !== undefined) {
      this.subscribeToSaveResponse(this.contratService.update(contrat));
    } else {
      this.subscribeToSaveResponse(this.contratService.create(contrat));
    }
  }

  private createFromForm(): IContrat {
    return {
      ...new Contrat(),
      id: this.editForm.get(['id'])!.value,
      numeroContrat: this.editForm.get(['numeroContrat'])!.value,
      libelleContrat: this.editForm.get(['libelleContrat'])!.value,
      dateDebut: this.editForm.get(['dateDebut'])!.value ? moment(this.editForm.get(['dateDebut'])!.value, DATE_TIME_FORMAT) : undefined,
      dateFin: this.editForm.get(['dateFin'])!.value ? moment(this.editForm.get(['dateFin'])!.value, DATE_TIME_FORMAT) : undefined,
      niveauEtude: this.editForm.get(['niveauEtude'])!.value,
      typeContrat: this.editForm.get(['typeContrat'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContrat>>): void {
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
