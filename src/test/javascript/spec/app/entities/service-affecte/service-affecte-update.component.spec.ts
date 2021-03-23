import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GpecmanagerTestModule } from '../../../test.module';
import { ServiceAffecteUpdateComponent } from 'app/entities/service-affecte/service-affecte-update.component';
import { ServiceAffecteService } from 'app/entities/service-affecte/service-affecte.service';
import { ServiceAffecte } from 'app/shared/model/service-affecte.model';

describe('Component Tests', () => {
  describe('ServiceAffecte Management Update Component', () => {
    let comp: ServiceAffecteUpdateComponent;
    let fixture: ComponentFixture<ServiceAffecteUpdateComponent>;
    let service: ServiceAffecteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GpecmanagerTestModule],
        declarations: [ServiceAffecteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ServiceAffecteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceAffecteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceAffecteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceAffecte(123);
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
        const entity = new ServiceAffecte();
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
