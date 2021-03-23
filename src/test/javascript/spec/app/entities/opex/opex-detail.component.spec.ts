import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GpecmanagerTestModule } from '../../../test.module';
import { OpexDetailComponent } from 'app/entities/opex/opex-detail.component';
import { Opex } from 'app/shared/model/opex.model';

describe('Component Tests', () => {
  describe('Opex Management Detail Component', () => {
    let comp: OpexDetailComponent;
    let fixture: ComponentFixture<OpexDetailComponent>;
    const route = ({ data: of({ opex: new Opex(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GpecmanagerTestModule],
        declarations: [OpexDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(OpexDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OpexDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load opex on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.opex).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
