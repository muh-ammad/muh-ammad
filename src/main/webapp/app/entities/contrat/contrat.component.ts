import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContrat } from 'app/shared/model/contrat.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ContratService } from './contrat.service';
import { ContratDeleteDialogComponent } from './contrat-delete-dialog.component';

@Component({
  selector: 'jhi-contrat',
  templateUrl: './contrat.component.html',
})
export class ContratComponent implements OnInit, OnDestroy {
  contrats: IContrat[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected contratService: ContratService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.contrats = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.contratService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IContrat[]>) => this.paginateContrats(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.contrats = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInContrats();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContrat): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInContrats(): void {
    this.eventSubscriber = this.eventManager.subscribe('contratListModification', () => this.reset());
  }

  delete(contrat: IContrat): void {
    const modalRef = this.modalService.open(ContratDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contrat = contrat;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateContrats(data: IContrat[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.contrats.push(data[i]);
      }
    }
  }
}
