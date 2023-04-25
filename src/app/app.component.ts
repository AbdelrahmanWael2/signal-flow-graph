import {Component, OnInit} from '@angular/core';
import * as jQuery from 'jquery';
import * as _ from 'lodash';
import * as $ from 'backbone';
import * as joint from 'jointjs';
import {myFactory} from "./myFactory";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
    title = 'programming';
    factory: any;
    graph: any;
    source: any;
    destination: any;
    transferFunction: any;

    ngOnInit(): void {

        this.graph = new joint.dia.Graph;
        let paper = new joint.dia.Paper({
            el: jQuery("#paper"),
            width: 1400,
            height: 750,
            model: this.graph,
            gridSize: 1,

        });
        paper.el.style.zIndex = '1';

        this.factory = new myFactory();
    }

    createNewNode(): any{
        let gotNode = this.factory.getNewNode();
        this.graph.addCell(gotNode);
    }

    createNewLink(): any {
        let gotLink = this.factory.getNewLink(this.source, this.destination, this.transferFunction);
        this.graph.addCell(gotLink);
    }
}


