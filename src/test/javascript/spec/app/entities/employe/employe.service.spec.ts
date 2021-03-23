import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { EmployeService } from 'app/entities/employe/employe.service';
import { IEmploye, Employe } from 'app/shared/model/employe.model';
import { GroupeSanguin } from 'app/shared/model/enumerations/groupe-sanguin.model';
import { Matrimonial } from 'app/shared/model/enumerations/matrimonial.model';
import { Sexe } from 'app/shared/model/enumerations/sexe.model';
import { Disponibilite } from 'app/shared/model/enumerations/disponibilite.model';

describe('Service Tests', () => {
  describe('Employe Service', () => {
    let injector: TestBed;
    let service: EmployeService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmploye;
    let expectedResult: IEmploye | IEmploye[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EmployeService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Employe(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        GroupeSanguin.AP,
        Matrimonial.CELIBATAIRE,
        Sexe.FEMININ,
        Disponibilite.ENCOURS
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateNaissance: currentDate.format(DATE_TIME_FORMAT),
            dateEmbauchement: currentDate.format(DATE_TIME_FORMAT),
            dateRetraite: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Employe', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dateNaissance: currentDate.format(DATE_TIME_FORMAT),
            dateEmbauchement: currentDate.format(DATE_TIME_FORMAT),
            dateRetraite: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaissance: currentDate,
            dateEmbauchement: currentDate,
            dateRetraite: currentDate,
          },
          returnedFromService
        );

        service.create(new Employe()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Employe', () => {
        const returnedFromService = Object.assign(
          {
            matricule: 'BBBBBB',
            prenoms: 'BBBBBB',
            nom: 'BBBBBB',
            intituleEmploye: 'BBBBBB',
            dateNaissance: currentDate.format(DATE_TIME_FORMAT),
            lieuNaissance: 'BBBBBB',
            numeroTelephone: 'BBBBBB',
            adresse: 'BBBBBB',
            photo: 'BBBBBB',
            email: 'BBBBBB',
            dateEmbauchement: currentDate.format(DATE_TIME_FORMAT),
            dateRetraite: currentDate.format(DATE_TIME_FORMAT),
            age: 1,
            numeroCni: 'BBBBBB',
            numeroIpres: 'BBBBBB',
            numeroCss: 'BBBBBB',
            groupeSanguin: 'BBBBBB',
            situationMatrimonial: 'BBBBBB',
            sexe: 'BBBBBB',
            disponibilite: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaissance: currentDate,
            dateEmbauchement: currentDate,
            dateRetraite: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Employe', () => {
        const returnedFromService = Object.assign(
          {
            matricule: 'BBBBBB',
            prenoms: 'BBBBBB',
            nom: 'BBBBBB',
            intituleEmploye: 'BBBBBB',
            dateNaissance: currentDate.format(DATE_TIME_FORMAT),
            lieuNaissance: 'BBBBBB',
            numeroTelephone: 'BBBBBB',
            adresse: 'BBBBBB',
            photo: 'BBBBBB',
            email: 'BBBBBB',
            dateEmbauchement: currentDate.format(DATE_TIME_FORMAT),
            dateRetraite: currentDate.format(DATE_TIME_FORMAT),
            age: 1,
            numeroCni: 'BBBBBB',
            numeroIpres: 'BBBBBB',
            numeroCss: 'BBBBBB',
            groupeSanguin: 'BBBBBB',
            situationMatrimonial: 'BBBBBB',
            sexe: 'BBBBBB',
            disponibilite: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateNaissance: currentDate,
            dateEmbauchement: currentDate,
            dateRetraite: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Employe', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
