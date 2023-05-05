import {Component, OnInit} from '@angular/core';
import * as jQuery from 'jquery';
import * as _ from 'lodash';
import * as $ from 'backbone';
import * as joint from 'jointjs';
import {givens, myFactory, result} from "./myFactory";
import {BackServiceService} from "./back-service.service";
import {compilePipeFromMetadata} from "@angular/compiler";

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
    myResult: any;

    inputNode: any;
    outputNode: any;



    constructor(private myService: BackServiceService) {
    }
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
        this.factory.printGraph();
    }

    createNewLink(): any {
        let gotLink = this.factory.getNewLink(this.source, this.destination, this.transferFunction);
        this.graph.addCell(gotLink);
        this.factory.printGraph();
    }
    // temp variables
    forwardPathPair: any;
    loopPair: any;
    nonTouchingLoops: any;
    bigDelta: any;
    delta_i: any;
    output_over_input:any;
    myGivens: any;
    // end temp variables

    prepareForResults() {
        // this.startEndNodesIndicator = true;
        let myGivens: givens = {
            listOfLists: this.factory.getGraph(),
            startNode: this.inputNode,
            endNode: this.outputNode
        };
        this.myGivens = myGivens;
    }
    getResult(): any {
        // send graph to calculate.
        // console.log(this.startEndNodesIndicator)
        console.log(this.myGivens)
        this.myService.request(this.myGivens).subscribe((responseObj: result) => {
            this.myResult = responseObj;
            // forward path works....
            this.forwardPathPair = responseObj.forwardPaths.map((val, idx) => [val, responseObj.forwardPaths[idx]]);

            // loop works
            this.loopPair = responseObj.loops.map((val, idx) => [val, responseObj.loopsGains[idx]]);

            // non touching
            this.nonTouchingLoops = responseObj.nonTouchingLoops;

            // bigDelta
            this.bigDelta = responseObj.bigDelta;

            // delta_i
            this.delta_i = responseObj.delta_i;

            // final value
            this.output_over_input = responseObj.output_over_input;

            console.log(responseObj.forwardPaths);
            console.log(responseObj.forwardPathGains);
            console.log(responseObj.loops);
            console.log(responseObj.loopsGains);
            console.log(responseObj.nonTouchingLoops);
            console.log(responseObj.bigDelta);
            console.log(responseObj.delta_i);
            console.log(responseObj.output_over_input);

        });

    }

}
