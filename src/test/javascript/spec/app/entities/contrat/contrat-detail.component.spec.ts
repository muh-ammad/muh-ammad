import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GpecmanagerTestModule } from '../../../test.module';
import { ContratDetailComponent } from 'app/entities/contrat/contrat-detail.component';
import { Contrat } from 'app/shared/model/contrat.model';

describe('Component Tests', () => {
  describe('Contrat Management Detail Component', () => {
    let comp: ContratDetailComponent;
    let fixture: ComponentFixture<ContratDetailComponent>;
    const route = ({ data: of({ contrat: new Contrat(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GpecmanagerTestModule],
        declarations: [ContratDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ContratDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContratDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load contrat on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contrat).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
