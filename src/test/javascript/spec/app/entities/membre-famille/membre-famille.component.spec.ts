import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { GpecmanagerTestModule } from '../../../test.module';
import { MembreFamilleComponent } from 'app/entities/membre-famille/membre-famille.component';
import { MembreFamilleService } from 'app/entities/membre-famille/membre-famille.service';
import { MembreFamille } from 'app/shared/model/membre-famille.model';

describe('Component Tests', () => {
  describe('MembreFamille Management Component', () => {
    let comp: MembreFamilleComponent;
    let fixture: ComponentFixture<MembreFamilleComponent>;
    let service: MembreFamilleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GpecmanagerTestModule],
        declarations: [MembreFamilleComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(MembreFamilleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MembreFamilleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MembreFamilleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MembreFamille(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.membreFamilles && comp.membreFamilles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MembreFamille(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.membreFamilles && comp.membreFamilles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
