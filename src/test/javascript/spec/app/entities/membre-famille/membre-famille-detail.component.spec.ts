import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GpecmanagerTestModule } from '../../../test.module';
import { MembreFamilleDetailComponent } from 'app/entities/membre-famille/membre-famille-detail.component';
import { MembreFamille } from 'app/shared/model/membre-famille.model';

describe('Component Tests', () => {
  describe('MembreFamille Management Detail Component', () => {
    let comp: MembreFamilleDetailComponent;
    let fixture: ComponentFixture<MembreFamilleDetailComponent>;
    const route = ({ data: of({ membreFamille: new MembreFamille(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GpecmanagerTestModule],
        declarations: [MembreFamilleDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MembreFamilleDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MembreFamilleDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load membreFamille on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.membreFamille).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
