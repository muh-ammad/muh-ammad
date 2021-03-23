import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GpecmanagerTestModule } from '../../../test.module';
import { SecteurActiviteDetailComponent } from 'app/entities/secteur-activite/secteur-activite-detail.component';
import { SecteurActivite } from 'app/shared/model/secteur-activite.model';

describe('Component Tests', () => {
  describe('SecteurActivite Management Detail Component', () => {
    let comp: SecteurActiviteDetailComponent;
    let fixture: ComponentFixture<SecteurActiviteDetailComponent>;
    const route = ({ data: of({ secteurActivite: new SecteurActivite(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GpecmanagerTestModule],
        declarations: [SecteurActiviteDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SecteurActiviteDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SecteurActiviteDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load secteurActivite on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.secteurActivite).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
