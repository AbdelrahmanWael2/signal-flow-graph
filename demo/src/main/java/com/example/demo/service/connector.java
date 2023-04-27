package com.example.demo.service;

import com.example.demo.logic.bigDelta;
import com.example.demo.logic.editedGraph;
import com.example.demo.logic.forward_loops;
import com.example.demo.pair;
import com.example.demo.result;
import com.example.demo.triple;

import java.util.ArrayList;
import java.util.List;

public class connector {
    ArrayList<ArrayList<triple<Integer, String, Float>>> graph;
    ArrayList<String> forwardPathsClass;
    ArrayList<Float> forwardPathGainsClass;
    ArrayList<pair<String, Float>> loopsClass;
    float output_over_input = 0;
    result myResult;
    public connector(ArrayList<ArrayList<triple<Integer, String, Float>>> graph){
        this.graph = graph;

        centerFunction();
    }

    private void centerFunction() {
        // collected knowledge by myresult Obj.
        myResult = new result();
        // generate forward paths and loops
        forward_loops forwardLoopObj = new forward_loops();
        forwardLoopObj.setGraph(graph);

        // forward paths
        pair<ArrayList<String>, ArrayList<Float>> tempObj = forwardLoopObj.start_finding_forward_paths();
        ArrayList<String> forwardPaths;
        myResult.forwardPaths = forwardPaths = tempObj.x;
        System.out.println( forwardPaths );
        ArrayList<Float> forwardPathGains;
        myResult.forwardPathGains = forwardPathGains = tempObj.y;

        // loops
        ArrayList<pair<String, Float>> loops;
        loops = forwardLoopObj.start_finding_all_loops();
        myResult.loops = new ArrayList<>();
        myResult.loopsGains = new ArrayList<>();
        for(pair<String, Float> myPair: loops){
            myResult.loops.add(myPair.x);
            myResult.loopsGains.add(myPair.y);
            System.out.println( myPair.x + " " + myPair.y );
        }

        // get delta of equation
        bigDelta bigDeltaObj = new bigDelta(loops);
        float delta = bigDeltaObj.getDelta();
        myResult.nonTouchingLoops = bigDeltaObj.getNonTouchingLoops();
        myResult.bigDelta = delta;

        // get delta i.
        myResult.delta_i = new ArrayList<>();
        for(int i = 0; i < forwardPaths.size(); i++){
            System.out.println( "in special num: " + i );
            // get new graph.
            editedGraph editedGraphObj = new editedGraph(graph, forwardPaths, i);
            editedGraphObj.algorithm();
            ArrayList<ArrayList<triple<Integer, String, Float>>> delta_i_graph = editedGraphObj.getEditedGraph();

            // get loops of new graph
            forward_loops forwardLoopObj_i = new forward_loops();
            forwardLoopObj_i.setGraph(delta_i_graph);
            ArrayList<pair<String, Float>> delta_i_loops = forwardLoopObj_i.start_finding_all_loops();

            // get delta i value.
            bigDelta delta_i_obj = new bigDelta(delta_i_loops);
            float delta_i = delta_i_obj.getDelta();
            myResult.delta_i.add(delta_i);

            output_over_input += forwardPathGains.get(i) * delta_i;
            // after end the variable will be sigma p_i * delta_i
        }
        output_over_input /= delta;
        myResult.output_over_input = output_over_input;
    }


    public result getResult() {
        return myResult;
    }
}
