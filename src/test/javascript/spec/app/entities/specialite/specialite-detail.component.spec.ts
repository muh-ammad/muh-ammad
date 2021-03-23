import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GpecmanagerTestModule } from '../../../test.module';
import { SpecialiteDetailComponent } from 'app/entities/specialite/specialite-detail.component';
import { Specialite } from 'app/shared/model/specialite.model';

describe('Component Tests', () => {
  describe('Specialite Management Detail Component', () => {
    let comp: SpecialiteDetailComponent;
    let fixture: ComponentFixture<SpecialiteDetailComponent>;
    const route = ({ data: of({ specialite: new Specialite(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GpecmanagerTestModule],
        declarations: [SpecialiteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SpecialiteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpecialiteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load specialite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.specialite).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
