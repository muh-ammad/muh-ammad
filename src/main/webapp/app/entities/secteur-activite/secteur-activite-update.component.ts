import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISecteurActivite, SecteurActivite } from 'app/shared/model/secteur-activite.model';
import { SecteurActiviteService } from './secteur-activite.service';

@Component({
  selector: 'jhi-secteur-activite-update',
  templateUrl: './secteur-activite-update.component.html',
})
export class SecteurActiviteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    libelleActivite: [null, [Validators.required]],
  });

  constructor(
    protected secteurActiviteService: SecteurActiviteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ secteurActivite }) => {
      this.updateForm(secteurActivite);
    });
  }

  updateForm(secteurActivite: ISecteurActivite): void {
    this.editForm.patchValue({
      id: secteurActivite.id,
      libelleActivite: secteurActivite.libelleActivite,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const secteurActivite = this.createFromForm();
    if (secteurActivite.id !== undefined) {
      this.subscribeToSaveResponse(this.secteurActiviteService.update(secteurActivite));
    } else {
      this.subscribeToSaveResponse(this.secteurActiviteService.create(secteurActivite));
    }
  }

  private createFromForm(): ISecteurActivite {
    return {
      ...new SecteurActivite(),
      id: this.editForm.get(['id'])!.value,
      libelleActivite: this.editForm.get(['libelleActivite'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecteurActivite>>): void {
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
