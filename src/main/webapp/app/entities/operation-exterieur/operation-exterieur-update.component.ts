import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IOperationExterieur, OperationExterieur } from 'app/shared/model/operation-exterieur.model';
import { OperationExterieurService } from './operation-exterieur.service';
import { IEmploye } from 'app/shared/model/employe.model';
import { EmployeService } from 'app/entities/employe/employe.service';

@Component({
  selector: 'jhi-operation-exterieur-update',
  templateUrl: './operation-exterieur-update.component.html',
})
export class OperationExterieurUpdateComponent implements OnInit {
  isSaving = false;
  employes: IEmploye[] = [];

  editForm = this.fb.group({
    id: [],
    lieuOpex: [null, [Validators.required]],
    anneeOpex: [null, [Validators.required]],
    employe: [],
  });

  constructor(
    protected operationExterieurService: OperationExterieurService,
    protected employeService: EmployeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ operationExterieur }) => {
      if (!operationExterieur.id) {
        const today = moment().startOf('day');
        operationExterieur.anneeOpex = today;
      }

      this.updateForm(operationExterieur);

      this.employeService.query().subscribe((res: HttpResponse<IEmploye[]>) => (this.employes = res.body || []));
    });
  }

  updateForm(operationExterieur: IOperationExterieur): void {
    this.editForm.patchValue({
      id: operationExterieur.id,
      lieuOpex: operationExterieur.lieuOpex,
      anneeOpex: operationExterieur.anneeOpex ? operationExterieur.anneeOpex.format(DATE_TIME_FORMAT) : null,
      employe: operationExterieur.employe,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const operationExterieur = this.createFromForm();
    if (operationExterieur.id !== undefined) {
      this.subscribeToSaveResponse(this.operationExterieurService.update(operationExterieur));
    } else {
      this.subscribeToSaveResponse(this.operationExterieurService.create(operationExterieur));
    }
  }

  private createFromForm(): IOperationExterieur {
    return {
      ...new OperationExterieur(),
      id: this.editForm.get(['id'])!.value,
      lieuOpex: this.editForm.get(['lieuOpex'])!.value,
      anneeOpex: this.editForm.get(['anneeOpex'])!.value ? moment(this.editForm.get(['anneeOpex'])!.value, DATE_TIME_FORMAT) : undefined,
      employe: this.editForm.get(['employe'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperationExterieur>>): void {
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
