import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IDistinction, Distinction } from 'app/shared/model/distinction.model';
import { DistinctionService } from './distinction.service';
import { IEmploye } from 'app/shared/model/employe.model';
import { EmployeService } from 'app/entities/employe/employe.service';

@Component({
  selector: 'jhi-distinction-update',
  templateUrl: './distinction-update.component.html',
})
export class DistinctionUpdateComponent implements OnInit {
  isSaving = false;
  employes: IEmploye[] = [];

  editForm = this.fb.group({
    id: [],
    libelleDistinction: [null, [Validators.required]],
    employe: [],
  });

  constructor(
    protected distinctionService: DistinctionService,
    protected employeService: EmployeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ distinction }) => {
      this.updateForm(distinction);

      this.employeService.query().subscribe((res: HttpResponse<IEmploye[]>) => (this.employes = res.body || []));
    });
  }

  updateForm(distinction: IDistinction): void {
    this.editForm.patchValue({
      id: distinction.id,
      libelleDistinction: distinction.libelleDistinction,
      employe: distinction.employe,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const distinction = this.createFromForm();
    if (distinction.id !== undefined) {
      this.subscribeToSaveResponse(this.distinctionService.update(distinction));
    } else {
      this.subscribeToSaveResponse(this.distinctionService.create(distinction));
    }
  }

  private createFromForm(): IDistinction {
    return {
      ...new Distinction(),
      id: this.editForm.get(['id'])!.value,
      libelleDistinction: this.editForm.get(['libelleDistinction'])!.value,
      employe: this.editForm.get(['employe'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDistinction>>): void {
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
