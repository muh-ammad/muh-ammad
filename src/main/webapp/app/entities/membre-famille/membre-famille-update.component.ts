import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMembreFamille, MembreFamille } from 'app/shared/model/membre-famille.model';
import { MembreFamilleService } from './membre-famille.service';
import { IEmploye } from 'app/shared/model/employe.model';
import { EmployeService } from 'app/entities/employe/employe.service';

@Component({
  selector: 'jhi-membre-famille-update',
  templateUrl: './membre-famille-update.component.html',
})
export class MembreFamilleUpdateComponent implements OnInit {
  isSaving = false;
  employes: IEmploye[] = [];

  editForm = this.fb.group({
    id: [],
    prenoms: [null, [Validators.required]],
    nom: [null, [Validators.required]],
    dateNaissance: [null, [Validators.required]],
    lieuNaissance: [null, [Validators.required]],
    epoux: [],
    epouse: [],
    enfant: [],
    employe: [],
  });

  constructor(
    protected membreFamilleService: MembreFamilleService,
    protected employeService: EmployeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ membreFamille }) => {
      if (!membreFamille.id) {
        const today = moment().startOf('day');
        membreFamille.dateNaissance = today;
      }

      this.updateForm(membreFamille);

      this.employeService.query().subscribe((res: HttpResponse<IEmploye[]>) => (this.employes = res.body || []));
    });
  }

  updateForm(membreFamille: IMembreFamille): void {
    this.editForm.patchValue({
      id: membreFamille.id,
      prenoms: membreFamille.prenoms,
      nom: membreFamille.nom,
      dateNaissance: membreFamille.dateNaissance ? membreFamille.dateNaissance.format(DATE_TIME_FORMAT) : null,
      lieuNaissance: membreFamille.lieuNaissance,
      epoux: membreFamille.epoux,
      epouse: membreFamille.epouse,
      enfant: membreFamille.enfant,
      employe: membreFamille.employe,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const membreFamille = this.createFromForm();
    if (membreFamille.id !== undefined) {
      this.subscribeToSaveResponse(this.membreFamilleService.update(membreFamille));
    } else {
      this.subscribeToSaveResponse(this.membreFamilleService.create(membreFamille));
    }
  }

  private createFromForm(): IMembreFamille {
    return {
      ...new MembreFamille(),
      id: this.editForm.get(['id'])!.value,
      prenoms: this.editForm.get(['prenoms'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      dateNaissance: this.editForm.get(['dateNaissance'])!.value
        ? moment(this.editForm.get(['dateNaissance'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lieuNaissance: this.editForm.get(['lieuNaissance'])!.value,
      epoux: this.editForm.get(['epoux'])!.value,
      epouse: this.editForm.get(['epouse'])!.value,
      enfant: this.editForm.get(['enfant'])!.value,
      employe: this.editForm.get(['employe'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMembreFamille>>): void {
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
