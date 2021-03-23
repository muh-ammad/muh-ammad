import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { OperationExterieurService } from 'app/entities/operation-exterieur/operation-exterieur.service';
import { IOperationExterieur, OperationExterieur } from 'app/shared/model/operation-exterieur.model';

describe('Service Tests', () => {
  describe('OperationExterieur Service', () => {
    let injector: TestBed;
    let service: OperationExterieurService;
    let httpMock: HttpTestingController;
    let elemDefault: IOperationExterieur;
    let expectedResult: IOperationExterieur | IOperationExterieur[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(OperationExterieurService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new OperationExterieur(0, 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            anneeOpex: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a OperationExterieur', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            anneeOpex: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            anneeOpex: currentDate,
          },
          returnedFromService
        );

        service.create(new OperationExterieur()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a OperationExterieur', () => {
        const returnedFromService = Object.assign(
          {
            lieuOpex: 'BBBBBB',
            anneeOpex: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            anneeOpex: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of OperationExterieur', () => {
        const returnedFromService = Object.assign(
          {
            lieuOpex: 'BBBBBB',
            anneeOpex: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            anneeOpex: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a OperationExterieur', () => {
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
