import * as joint from "jointjs";

export class myFactory {
    private static lastID: number  = 0;

    getNewNode(): any{
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
        return circle;
    }

    getNewLink(source: any, destination: any, transferFunction: any): any{
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
        return link;
    }
}
