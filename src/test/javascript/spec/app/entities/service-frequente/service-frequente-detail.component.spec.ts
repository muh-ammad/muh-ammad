import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GpecmanagerTestModule } from '../../../test.module';
import { ServiceFrequenteDetailComponent } from 'app/entities/service-frequente/service-frequente-detail.component';
import { ServiceFrequente } from 'app/shared/model/service-frequente.model';

describe('Component Tests', () => {
  describe('ServiceFrequente Management Detail Component', () => {
    let comp: ServiceFrequenteDetailComponent;
    let fixture: ComponentFixture<ServiceFrequenteDetailComponent>;
    const route = ({ data: of({ serviceFrequente: new ServiceFrequente(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GpecmanagerTestModule],
        declarations: [ServiceFrequenteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ServiceFrequenteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceFrequenteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load serviceFrequente on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.serviceFrequente).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
