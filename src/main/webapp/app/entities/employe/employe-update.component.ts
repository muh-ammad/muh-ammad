import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IEmploye, Employe } from 'app/shared/model/employe.model';
import { EmployeService } from './employe.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IContrat } from 'app/shared/model/contrat.model';
import { ContratService } from 'app/entities/contrat/contrat.service';
import { IServiceAffecte } from 'app/shared/model/service-affecte.model';
import { ServiceAffecteService } from 'app/entities/service-affecte/service-affecte.service';
import { IServiceFrequente } from 'app/shared/model/service-frequente.model';
import { ServiceFrequenteService } from 'app/entities/service-frequente/service-frequente.service';

type SelectableEntity = IContrat | IServiceAffecte | IServiceFrequente;

type SelectableManyToManyEntity = IServiceAffecte | IServiceFrequente;

@Component({
  selector: 'jhi-employe-update',
  templateUrl: './employe-update.component.html',
})
export class EmployeUpdateComponent implements OnInit {
  isSaving = false;
  contrats: IContrat[] = [];
  serviceaffectes: IServiceAffecte[] = [];
  servicefrequentes: IServiceFrequente[] = [];

  editForm = this.fb.group({
    id: [],
    matricule: [null, [Validators.required]],
    prenoms: [null, [Validators.required]],
    nom: [null, [Validators.required]],
    intituleEmploye: [],
    dateNaissance: [null, [Validators.required]],
    lieuNaissance: [null, [Validators.required]],
    numeroTelephone: [null, [Validators.required]],
    adresse: [],
    photo: [],
    photoContentType: [],
    email: [],
    dateEmbauchement: [],
    dateRetraite: [],
    age: [],
    numeroCni: [],
    numeroIpres: [],
    numeroCss: [],
    groupeSanguin: [],
    situationMatrimonial: [],
    sexe: [],
    disponibilite: [],
    contrat: [],
    services: [],
    serviceFrequentes: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected employeService: EmployeService,
    protected contratService: ContratService,
    protected serviceAffecteService: ServiceAffecteService,
    protected serviceFrequenteService: ServiceFrequenteService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employe }) => {
      if (!employe.id) {
        const today = moment().startOf('day');
        employe.dateNaissance = today;
        employe.lieuNaissance = today;
        employe.dateEmbauchement = today;
        employe.dateRetraite = today;
      }

      this.updateForm(employe);

      this.contratService
        .query({ 'employeId.specified': 'false' })
        .pipe(
          map((res: HttpResponse<IContrat[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IContrat[]) => {
          if (!employe.contrat || !employe.contrat.id) {
            this.contrats = resBody;
          } else {
            this.contratService
              .find(employe.contrat.id)
              .pipe(
                map((subRes: HttpResponse<IContrat>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IContrat[]) => (this.contrats = concatRes));
          }
        });

      this.serviceAffecteService.query().subscribe((res: HttpResponse<IServiceAffecte[]>) => (this.serviceaffectes = res.body || []));

      this.serviceFrequenteService.query().subscribe((res: HttpResponse<IServiceFrequente[]>) => (this.servicefrequentes = res.body || []));
    });
  }

  updateForm(employe: IEmploye): void {
    this.editForm.patchValue({
      id: employe.id,
      matricule: employe.matricule,
      prenoms: employe.prenoms,
      nom: employe.nom,
      intituleEmploye: employe.intituleEmploye,
      dateNaissance: employe.dateNaissance ? employe.dateNaissance.format(DATE_TIME_FORMAT) : null,
      lieuNaissance: employe.lieuNaissance ? employe.lieuNaissance.format(DATE_TIME_FORMAT) : null,
      numeroTelephone: employe.numeroTelephone,
      adresse: employe.adresse,
      photo: employe.photo,
      photoContentType: employe.photoContentType,
      email: employe.email,
      dateEmbauchement: employe.dateEmbauchement ? employe.dateEmbauchement.format(DATE_TIME_FORMAT) : null,
      dateRetraite: employe.dateRetraite ? employe.dateRetraite.format(DATE_TIME_FORMAT) : null,
      age: employe.age,
      numeroCni: employe.numeroCni,
      numeroIpres: employe.numeroIpres,
      numeroCss: employe.numeroCss,
      groupeSanguin: employe.groupeSanguin,
      situationMatrimonial: employe.situationMatrimonial,
      sexe: employe.sexe,
      disponibilite: employe.disponibilite,
      contrat: employe.contrat,
      services: employe.services,
      serviceFrequentes: employe.serviceFrequentes,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('gpecmanagerApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employe = this.createFromForm();
    if (employe.id !== undefined) {
      this.subscribeToSaveResponse(this.employeService.update(employe));
    } else {
      this.subscribeToSaveResponse(this.employeService.create(employe));
    }
  }

  private createFromForm(): IEmploye {
    return {
      ...new Employe(),
      id: this.editForm.get(['id'])!.value,
      matricule: this.editForm.get(['matricule'])!.value,
      prenoms: this.editForm.get(['prenoms'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      intituleEmploye: this.editForm.get(['intituleEmploye'])!.value,
      dateNaissance: this.editForm.get(['dateNaissance'])!.value
        ? moment(this.editForm.get(['dateNaissance'])!.value, DATE_TIME_FORMAT)
        : undefined,
      lieuNaissance: this.editForm.get(['lieuNaissance'])!.value
        ? moment(this.editForm.get(['lieuNaissance'])!.value, DATE_TIME_FORMAT)
        : undefined,
      numeroTelephone: this.editForm.get(['numeroTelephone'])!.value,
      adresse: this.editForm.get(['adresse'])!.value,
      photoContentType: this.editForm.get(['photoContentType'])!.value,
      photo: this.editForm.get(['photo'])!.value,
      email: this.editForm.get(['email'])!.value,
      dateEmbauchement: this.editForm.get(['dateEmbauchement'])!.value
        ? moment(this.editForm.get(['dateEmbauchement'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dateRetraite: this.editForm.get(['dateRetraite'])!.value
        ? moment(this.editForm.get(['dateRetraite'])!.value, DATE_TIME_FORMAT)
        : undefined,
      age: this.editForm.get(['age'])!.value,
      numeroCni: this.editForm.get(['numeroCni'])!.value,
      numeroIpres: this.editForm.get(['numeroIpres'])!.value,
      numeroCss: this.editForm.get(['numeroCss'])!.value,
      groupeSanguin: this.editForm.get(['groupeSanguin'])!.value,
      situationMatrimonial: this.editForm.get(['situationMatrimonial'])!.value,
      sexe: this.editForm.get(['sexe'])!.value,
      disponibilite: this.editForm.get(['disponibilite'])!.value,
      contrat: this.editForm.get(['contrat'])!.value,
      services: this.editForm.get(['services'])!.value,
      serviceFrequentes: this.editForm.get(['serviceFrequentes'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmploye>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
