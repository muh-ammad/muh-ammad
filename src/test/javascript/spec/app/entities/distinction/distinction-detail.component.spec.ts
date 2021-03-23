import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GpecmanagerTestModule } from '../../../test.module';
import { DistinctionDetailComponent } from 'app/entities/distinction/distinction-detail.component';
import { Distinction } from 'app/shared/model/distinction.model';

describe('Component Tests', () => {
  describe('Distinction Management Detail Component', () => {
    let comp: DistinctionDetailComponent;
    let fixture: ComponentFixture<DistinctionDetailComponent>;
    const route = ({ data: of({ distinction: new Distinction(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GpecmanagerTestModule],
        declarations: [DistinctionDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DistinctionDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DistinctionDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load distinction on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.distinction).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
