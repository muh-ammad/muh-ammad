import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GpecmanagerTestModule } from '../../../test.module';
import { OpexUpdateComponent } from 'app/entities/opex/opex-update.component';
import { OpexService } from 'app/entities/opex/opex.service';
import { Opex } from 'app/shared/model/opex.model';

describe('Component Tests', () => {
  describe('Opex Management Update Component', () => {
    let comp: OpexUpdateComponent;
    let fixture: ComponentFixture<OpexUpdateComponent>;
    let service: OpexService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GpecmanagerTestModule],
        declarations: [OpexUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(OpexUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OpexUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OpexService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Opex(123);
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
        const entity = new Opex();
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
