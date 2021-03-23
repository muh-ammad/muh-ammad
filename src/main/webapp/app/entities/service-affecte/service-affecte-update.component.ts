import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IServiceAffecte, ServiceAffecte } from 'app/shared/model/service-affecte.model';
import { ServiceAffecteService } from './service-affecte.service';
import { ISecteurActivite } from 'app/shared/model/secteur-activite.model';
import { SecteurActiviteService } from 'app/entities/secteur-activite/secteur-activite.service';

@Component({
  selector: 'jhi-service-affecte-update',
  templateUrl: './service-affecte-update.component.html',
})
export class ServiceAffecteUpdateComponent implements OnInit {
  isSaving = false;
  secteuractivites: ISecteurActivite[] = [];

  editForm = this.fb.group({
    id: [],
    codeService: [null, [Validators.required]],
    libelleService: [null, [Validators.required]],
    secteurActivite: [],
  });

  constructor(
    protected serviceAffecteService: ServiceAffecteService,
    protected secteurActiviteService: SecteurActiviteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ serviceAffecte }) => {
      this.updateForm(serviceAffecte);

      this.secteurActiviteService
        .query({ 'serviceAffecteId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<ISecteurActivite[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ISecteurActivite[]) => {
          if (!serviceAffecte.secteurActivite || !serviceAffecte.secteurActivite.id) {
            this.secteuractivites = resBody;
          } else {
            this.secteurActiviteService
              .find(serviceAffecte.secteurActivite.id)
              .pipe(
                map((subRes: HttpResponse<ISecteurActivite>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ISecteurActivite[]) => (this.secteuractivites = concatRes));
          }
        });
    });
  }

  updateForm(serviceAffecte: IServiceAffecte): void {
    this.editForm.patchValue({
      id: serviceAffecte.id,
      codeService: serviceAffecte.codeService,
      libelleService: serviceAffecte.libelleService,
      secteurActivite: serviceAffecte.secteurActivite,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const serviceAffecte = this.createFromForm();
    if (serviceAffecte.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceAffecteService.update(serviceAffecte));
    } else {
      this.subscribeToSaveResponse(this.serviceAffecteService.create(serviceAffecte));
    }
  }

  private createFromForm(): IServiceAffecte {
    return {
      ...new ServiceAffecte(),
      id: this.editForm.get(['id'])!.value,
      codeService: this.editForm.get(['codeService'])!.value,
      libelleService: this.editForm.get(['libelleService'])!.value,
      secteurActivite: this.editForm.get(['secteurActivite'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceAffecte>>): void {
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

  trackById(index: number, item: ISecteurActivite): any {
    return item.id;
  }
}
