import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'jhi-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {
  @Input() leftMenuHidden: boolean;
  constructor() { this.leftMenuHidden = false }

  ngOnInit(): void {
  }

}
