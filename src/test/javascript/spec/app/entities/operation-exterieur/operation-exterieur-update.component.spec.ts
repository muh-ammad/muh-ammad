import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GpecmanagerTestModule } from '../../../test.module';
import { OperationExterieurUpdateComponent } from 'app/entities/operation-exterieur/operation-exterieur-update.component';
import { OperationExterieurService } from 'app/entities/operation-exterieur/operation-exterieur.service';
import { OperationExterieur } from 'app/shared/model/operation-exterieur.model';

describe('Component Tests', () => {
  describe('OperationExterieur Management Update Component', () => {
    let comp: OperationExterieurUpdateComponent;
    let fixture: ComponentFixture<OperationExterieurUpdateComponent>;
    let service: OperationExterieurService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GpecmanagerTestModule],
        declarations: [OperationExterieurUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OperationExterieurUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OperationExterieurUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OperationExterieurService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OperationExterieur(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new OperationExterieur();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
