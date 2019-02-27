import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-gutter',
  templateUrl: './gutter.component.html',
  styleUrls: ['./gutter.component.scss']
})
export class GutterComponent implements OnInit {

  constructor() { }

  private trainCards = ['train1', 'train2', 'train3', 'train4', 'train1', 'train2', 'train3', 'train4'];
  private destCards = ['dest1', 'dest2', 'dest3', 'dest4', 'dest5', 'dest1', 'dest1', 'dest1'];

  ngOnInit() {
  }

}
