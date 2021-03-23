import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GpecmanagerTestModule } from '../../../test.module';
import { ServiceAffecteDetailComponent } from 'app/entities/service-affecte/service-affecte-detail.component';
import { ServiceAffecte } from 'app/shared/model/service-affecte.model';

describe('Component Tests', () => {
  describe('ServiceAffecte Management Detail Component', () => {
    let comp: ServiceAffecteDetailComponent;
    let fixture: ComponentFixture<ServiceAffecteDetailComponent>;
    const route = ({ data: of({ serviceAffecte: new ServiceAffecte(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GpecmanagerTestModule],
        declarations: [ServiceAffecteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ServiceAffecteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceAffecteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load serviceAffecte on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceAffecte).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
