import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GpecmanagerTestModule } from '../../../test.module';
import { DiplomeDetailComponent } from 'app/entities/diplome/diplome-detail.component';
import { Diplome } from 'app/shared/model/diplome.model';

describe('Component Tests', () => {
  describe('Diplome Management Detail Component', () => {
    let comp: DiplomeDetailComponent;
    let fixture: ComponentFixture<DiplomeDetailComponent>;
    const route = ({ data: of({ diplome: new Diplome(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GpecmanagerTestModule],
        declarations: [DiplomeDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(DiplomeDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DiplomeDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load diplome on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.diplome).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
