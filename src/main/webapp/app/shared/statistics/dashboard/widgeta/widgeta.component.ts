import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { EmployeService } from 'app/entities/employe/employe.service';
import { IEmploye } from 'app/shared/model/employe.model';
import { map } from 'rxjs/operators';
import * as Highcharts from 'highcharts';

@Component({
  selector: 'jhi-widgeta',
  templateUrl: './widgeta.component.html',
  styleUrls: ['./widgeta.component.scss']
})
export class WidgetaComponent implements OnInit {

  nombreEmployeSup: any = 0;
  nombreEmployeInf: any = 0;
  employesSup: IEmploye[] = [];
  employesInf: IEmploye[] = [];
  chartOptions: {} = {};
  Highcharts = Highcharts;

  constructor(private employeService: EmployeService) { }

  ngOnInit(): void {
 // employe sup 30
    this.employeService
      .query({ 'age.greaterOrEqualThan': 30 })
      .pipe(
        map(
          (res: HttpResponse<IEmploye[]>) => {
            return res.body || [];
          }
        )
      )
      .subscribe(
        (resBody: IEmploye[]) => 
          {this.employesSup = resBody;
            this.nombreEmployeSup = this.employesSup.length;}
          );
// employe inf 30
          this.employeService
          .query({ 'age.lessThan': 30 })
          .pipe(
            map(
              (res: HttpResponse<IEmploye[]>) => {
                return res.body || [];
              }
            )
          )
          .subscribe(
            (resBody: IEmploye[]) => 
              {this.employesInf = resBody;
              this.nombreEmployeInf = this.employesInf.length;
            }
              );

      this.defineChartOptions();
  }

  

  defineChartOptions(): any {
    this.chartOptions = {
      chart: {
        type: 'line'
      },
      title: {
        text: 'Evolution personnel en age'
      },
      subtitle: {
        text: 'GPEC'
      },
      xAxis: {
        categories: ['Inf 30', 'Sup 30', ]
      },
      yAxis: {
        title: {
          text: 'Age (ans)'
        }
      },
      plotOptions: {
        line: {
          dataLabels: {
            enabled: true
          },
          enableMouseTracking: false
        }
      },
      series: [{
        name: 'Age Employe',
        data: [3, 4]
      }]
    };

    return this.chartOptions;

  }

}
