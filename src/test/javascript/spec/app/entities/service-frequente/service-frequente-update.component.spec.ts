import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GpecmanagerTestModule } from '../../../test.module';
import { ServiceFrequenteUpdateComponent } from 'app/entities/service-frequente/service-frequente-update.component';
import { ServiceFrequenteService } from 'app/entities/service-frequente/service-frequente.service';
import { ServiceFrequente } from 'app/shared/model/service-frequente.model';

describe('Component Tests', () => {
  describe('ServiceFrequente Management Update Component', () => {
    let comp: ServiceFrequenteUpdateComponent;
    let fixture: ComponentFixture<ServiceFrequenteUpdateComponent>;
    let service: ServiceFrequenteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GpecmanagerTestModule],
        declarations: [ServiceFrequenteUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ServiceFrequenteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ServiceFrequenteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceFrequenteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ServiceFrequente(123);
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
        const entity = new ServiceFrequente();
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
