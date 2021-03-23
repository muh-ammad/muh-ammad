import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GpecmanagerTestModule } from '../../../test.module';
import { DistinctionUpdateComponent } from 'app/entities/distinction/distinction-update.component';
import { DistinctionService } from 'app/entities/distinction/distinction.service';
import { Distinction } from 'app/shared/model/distinction.model';

describe('Component Tests', () => {
  describe('Distinction Management Update Component', () => {
    let comp: DistinctionUpdateComponent;
    let fixture: ComponentFixture<DistinctionUpdateComponent>;
    let service: DistinctionService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GpecmanagerTestModule],
        declarations: [DistinctionUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(DistinctionUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DistinctionUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DistinctionService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Distinction(123);
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
        const entity = new Distinction();
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
