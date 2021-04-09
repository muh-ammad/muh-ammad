import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IContrat, Contrat } from 'app/shared/model/contrat.model';
import { ContratService } from './contrat.service';
import { IEmploye } from 'app/shared/model/employe.model';
import { EmployeService } from 'app/entities/employe/employe.service';

@Component({
  selector: 'jhi-contrat-update',
  templateUrl: './contrat-update.component.html',
})
export class ContratUpdateComponent implements OnInit {
  isSaving = false;
  employes: IEmploye[] = [];

  editForm = this.fb.group({
    id: [],
    numeroContrat: [null, [Validators.required]],
    libelleContrat: [null, [Validators.required]],
    dateDebut: [null, [Validators.required]],
    dateFin: [null, [Validators.required]],
    niveauEtude: [null, [Validators.required]],
    typeContrat: [null, [Validators.required]],
    employe: [],
  });

  constructor(
    protected contratService: ContratService,
    protected employeService: EmployeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contrat }) => {
      if (!contrat.id) {
        const today = moment().startOf('day');
        contrat.dateDebut = today;
        contrat.dateFin = today;
      }

      this.updateForm(contrat);

      this.employeService
        .query({ 'contratId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IEmploye[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IEmploye[]) => {
          if (!contrat.employe || !contrat.employe.id) {
            this.employes = resBody;
          } else {
            this.employeService
              .find(contrat.employe.id)
              .pipe(
                map((subRes: HttpResponse<IEmploye>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IEmploye[]) => (this.employes = concatRes));
          }
        });
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
      employe: contrat.employe,
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
      employe: this.editForm.get(['employe'])!.value,
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

  trackById(index: number, item: IEmploye): any {
    return item.id;
  }
}
