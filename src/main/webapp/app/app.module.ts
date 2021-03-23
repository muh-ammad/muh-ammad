import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { GpecmanagerSharedModule } from 'app/shared/shared.module';
import { GpecmanagerCoreModule } from 'app/core/core.module';
import { GpecmanagerAppRoutingModule } from './app-routing.module';
import { GpecmanagerHomeModule } from './home/home.module';
import { GpecmanagerEntityModule } from './entities/entity.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import { SidebarComponent } from './layouts/sidebar/sidebar.component';

@NgModule({
  imports: [
    BrowserModule,
    GpecmanagerSharedModule,
    GpecmanagerCoreModule,
    GpecmanagerHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    GpecmanagerEntityModule,
    GpecmanagerAppRoutingModule,
    BrowserAnimationsModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent, SidebarComponent],
  bootstrap: [MainComponent],
})
export class GpecmanagerAppModule {}
