import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './dashboard.component';
import { WidgetaComponent } from './widgeta/widgeta.component';
import { WidgetbComponent } from './widgetb/widgetb.component';
import { WidgetcComponent } from './widgetc/widgetc.component';
import { WidgetdComponent } from './widgetd/widgetd.component';
import { HighchartsChartModule } from 'highcharts-angular';



@NgModule({
  declarations: [DashboardComponent, WidgetaComponent, WidgetbComponent, WidgetcComponent, WidgetdComponent],
  imports: [
    CommonModule,
    HighchartsChartModule
  ]
})
export class DashboardModule { }
