import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISecteurActivite } from 'app/shared/model/secteur-activite.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SecteurActiviteService } from './secteur-activite.service';
import { SecteurActiviteDeleteDialogComponent } from './secteur-activite-delete-dialog.component';

@Component({
  selector: 'jhi-secteur-activite',
  templateUrl: './secteur-activite.component.html',
})
export class SecteurActiviteComponent implements OnInit, OnDestroy {
  secteurActivites: ISecteurActivite[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected secteurActiviteService: SecteurActiviteService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.secteurActivites = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.secteurActiviteService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<ISecteurActivite[]>) => this.paginateSecteurActivites(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.secteurActivites = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSecteurActivites();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISecteurActivite): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSecteurActivites(): void {
    this.eventSubscriber = this.eventManager.subscribe('secteurActiviteListModification', () => this.reset());
  }

  delete(secteurActivite: ISecteurActivite): void {
    const modalRef = this.modalService.open(SecteurActiviteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.secteurActivite = secteurActivite;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSecteurActivites(data: ISecteurActivite[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.secteurActivites.push(data[i]);
      }
    }
  }
}
