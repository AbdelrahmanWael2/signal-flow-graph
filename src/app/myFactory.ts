import * as joint from "jointjs";

export interface Edge{
    destination: number;
    y: string;
    gain: number;
}

export interface result{
    forwardPaths: string[];
    forwardPathGains: number[];
    loops: string[];
    loopsGains: number[];
    nonTouchingLoops: string[];
    bigDelta: number;
    delta_i: number[];
    output_over_input: number;
}
export class myFactory {
    private static lastID: number  = -1;
    private static weightedgraph: Edge[][] = [];

    public getGraph(): Edge[][] {
        return myFactory.weightedgraph;
    }

    public usedGraph(): Edge[][]{
        let wg: Edge[][] = [];
        // wg =
        // [
        //     [
        //         {destination: 1,y: "",gain: 2}
        //     ],
        //     [
        //         {destination: 2,y: "",gain: 3},
        //         {destination: 3,y: "",gain: 5},
        //         {destination: 6,y: "",gain: 7}
        //     ],
        //     [
        //         {destination: 1,y: "",gain: 47},
        //         {destination: 3,y: "",gain: 53}
        //     ],
        //     [
        //         {destination: 2,y: "",gain: 11},
        //         {destination: 4,y: "",gain: 13}
        //     ],
        //     [
        //         {destination: 3,y: "",gain: 17},
        //         {destination: 5,y: "",gain: 19}
        //     ],
        //     [
        //         {destination: 4,y: "",gain: 23},
        //         {destination: 6,y: "",gain: 29}
        //     ],
        //     [
        //         {destination: 4,y: "",gain: 31},
        //         {destination: 5,y: "",gain: 37},
        //         {destination: 6,y: "",gain: 41},
        //         {destination: 7,y: "",gain: 43}
        //     ],
        //     []
        // ]
        // wg =
        // [
        //     [
        //         {destination: 1,y: "",gain: 2} // 0
        //     ],
        //     [
        //         {destination: 2,y: "",gain: 3} // 1
        //     ],
        //     [
        //         {destination: 1,y: "",gain: 5}, // 2
        //         {destination: 3,y: "",gain: 7}
        //     ],
        //     [
        //         {destination: 4,y: "",gain: 31}// 3
        //     ],
        //     [
        //         {destination: 3,y: "",gain: 11}, // 4
        //         {destination: 7,y: "",gain: 13}
        //     ],
        //     [
        //         {destination: 6,y: "",gain: 19} // 5
        //     ],
        //     [
        //         {destination: 1,y: "",gain: 23}, // 6
        //         {destination: 5,y: "",gain: 29}
        //     ],
        //     [
        //         {destination: 6,y: "",gain: 17} // 7
        //     ]
        // ]
        return wg;
    }

    getNewNode(): any{
        // add in html.
        myFactory.lastID++;
        let newNodeId: any = myFactory.lastID;
        let circle = new joint.shapes.basic.Circle({
            position: { x: 100 + myFactory.lastID*2, y: 100 },
            size: { width: 50, height: 50 },
            attrs: {
                circle: { fill: 'blue' },
                text: {
                    text: newNodeId,
                    fill: 'white',
                    'font-family': 'Arial',
                    'font-size': 16
                }
            },
            draggable: true,
            id: newNodeId
        });
        // add in common list of list.
        let newList: Edge[] = [];
        myFactory.weightedgraph.push(newList);
        return circle;
    }

    getNewLink(source: any, destination: any, transferFunction: any): any{
        // add in html.
        let link = new joint.dia.Link({
            source: { id: source },
            target: { id: destination },
            router: { name: 'orthogonal' },
            connector: { name: 'jumpover', args: { radius: 10 } },
            attrs: {
                '.connection': { stroke: 'white', 'stroke-width': 2 },
                '.marker-target': { fill: 'white', d: 'M 10 0 L 0 5 L 10 10 z' }
            },
            labels: [
                {
                    position: 0.5, // Position of the label along the link (0 = start, 1 = end)
                    attrs: {
                        text: {
                            text: transferFunction,
                            fill: 'black',
                            'font-size': 20
                        },
                        rect: {
                            fill: 'white',
                            stroke: 'black'
                        }
                    }
                }
            ]
        });
        // add in common list of list
        let edge: Edge = {
            destination: destination,
            y: "",
            gain: transferFunction
        }
        myFactory.weightedgraph[source].push(edge);
        return link;
    };

    printGraph(): void{
        // for (let i = 1; i <= myFactory.weightedgraph.length; i++) {
        //     for (let j = 1; j <= myFactory.weightedgraph[i].length; j++) {
        //         console.log(myFactory.weightedgraph[i][j]);
        //     }
        // }
        console.log(myFactory.weightedgraph);
    }
}
