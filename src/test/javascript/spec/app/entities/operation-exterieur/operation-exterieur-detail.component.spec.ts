import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GpecmanagerTestModule } from '../../../test.module';
import { OperationExterieurDetailComponent } from 'app/entities/operation-exterieur/operation-exterieur-detail.component';
import { OperationExterieur } from 'app/shared/model/operation-exterieur.model';

describe('Component Tests', () => {
  describe('OperationExterieur Management Detail Component', () => {
    let comp: OperationExterieurDetailComponent;
    let fixture: ComponentFixture<OperationExterieurDetailComponent>;
    const route = ({ data: of({ operationExterieur: new OperationExterieur(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GpecmanagerTestModule],
        declarations: [OperationExterieurDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OperationExterieurDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OperationExterieurDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load operationExterieur on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.operationExterieur).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
