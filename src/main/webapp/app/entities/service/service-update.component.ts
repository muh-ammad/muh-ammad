import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IService, Service } from 'app/shared/model/service.model';
import { ServiceService } from './service.service';
import { ISecteurActivite } from 'app/shared/model/secteur-activite.model';
import { SecteurActiviteService } from 'app/entities/secteur-activite/secteur-activite.service';

@Component({
  selector: 'jhi-service-update',
  templateUrl: './service-update.component.html',
})
export class ServiceUpdateComponent implements OnInit {
  isSaving = false;
  secteuractivites: ISecteurActivite[] = [];

  editForm = this.fb.group({
    id: [],
    codeService: [null, [Validators.required]],
    libelleService: [null, [Validators.required]],
    secteurActivite: [],
  });

  constructor(
    protected serviceService: ServiceService,
    protected secteurActiviteService: SecteurActiviteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ service }) => {
      this.updateForm(service);

      this.secteurActiviteService
        .query({ filter: 'service-is-null' })
        .pipe(
          map((res: HttpResponse<ISecteurActivite[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: ISecteurActivite[]) => {
          if (!service.secteurActivite || !service.secteurActivite.id) {
            this.secteuractivites = resBody;
          } else {
            this.secteurActiviteService
              .find(service.secteurActivite.id)
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

  updateForm(service: IService): void {
    this.editForm.patchValue({
      id: service.id,
      codeService: service.codeService,
      libelleService: service.libelleService,
      secteurActivite: service.secteurActivite,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const service = this.createFromForm();
    if (service.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceService.update(service));
    } else {
      this.subscribeToSaveResponse(this.serviceService.create(service));
    }
  }

  private createFromForm(): IService {
    return {
      ...new Service(),
      id: this.editForm.get(['id'])!.value,
      codeService: this.editForm.get(['codeService'])!.value,
      libelleService: this.editForm.get(['libelleService'])!.value,
      secteurActivite: this.editForm.get(['secteurActivite'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IService>>): void {
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
