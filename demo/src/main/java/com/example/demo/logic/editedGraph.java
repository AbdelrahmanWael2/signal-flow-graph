package com.example.demo.logic;

import com.example.demo.triple;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class editedGraph {
    private ArrayList<ArrayList<triple<Integer, String, Float>>> graph;
    private ArrayList<ArrayList<triple<Integer, String, Float>>> newGraph;
    private ArrayList<String> forward_paths;
    private int forward_path_i;

    public editedGraph(ArrayList<ArrayList<triple<Integer, String, Float>>> graph,
                       ArrayList<String> forward_paths,
                       int forward_path_i){
        this.graph = graph;
        this.forward_paths = forward_paths;
        this.forward_path_i = forward_path_i;
        this.newGraph = new ArrayList<>();
    }

    public void algorithm(){
        long participatedNodes = 0;
        String[] heldNodes = forward_paths.get(forward_path_i).split(" ");
        for(int i = 0; i < heldNodes.length; i++){
            participatedNodes |= (1L << parseInt(heldNodes[i]));
        }

        // don't add nodes in participated nodes for this forward path.
        for(int i = 0; i < graph.size(); i++){
            newGraph.add(new ArrayList<>());

            // if not participate in forward path case 1.
            if(((1 << i) & participatedNodes) == 0){
                ArrayList<triple<Integer, String, Float>> edgesOfNode = graph.get(i);
                for(int j = 0; j < edgesOfNode.size(); j++){

                    // if not participate in forward path case 2.
                    if( ((1L << edgesOfNode.get(j).destination) & participatedNodes) == 0){
                        newGraph.get(i).add(edgesOfNode.get(j).clone());
                        System.out.println( "ooooo: " + i + " " + j + " " + newGraph.get(i).get(newGraph.get(i).size()-1).destination + " " + newGraph.get(i).get(newGraph.get(i).size()-1).gain );
                    }

                }
            }
        }
    }

    public ArrayList<ArrayList<triple<Integer, String, Float>>> getEditedGraph(){
        return newGraph;
    }
}
