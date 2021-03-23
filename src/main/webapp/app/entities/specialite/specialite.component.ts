import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpecialite } from 'app/shared/model/specialite.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SpecialiteService } from './specialite.service';
import { SpecialiteDeleteDialogComponent } from './specialite-delete-dialog.component';

@Component({
  selector: 'jhi-specialite',
  templateUrl: './specialite.component.html',
})
export class SpecialiteComponent implements OnInit, OnDestroy {
  specialites: ISpecialite[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected specialiteService: SpecialiteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.specialites = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.specialiteService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<ISpecialite[]>) => this.paginateSpecialites(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.specialites = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSpecialites();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISpecialite): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSpecialites(): void {
    this.eventSubscriber = this.eventManager.subscribe('specialiteListModification', () => this.reset());
  }

  delete(specialite: ISpecialite): void {
    const modalRef = this.modalService.open(SpecialiteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.specialite = specialite;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSpecialites(data: ISpecialite[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.specialites.push(data[i]);
      }
    }
  }
}
