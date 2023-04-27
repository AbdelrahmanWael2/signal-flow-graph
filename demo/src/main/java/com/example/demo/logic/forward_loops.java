package com.example.demo.logic;

import com.example.demo.pair;
import com.example.demo.triple;

import java.util.ArrayList;

public class forward_loops {

    private ArrayList<ArrayList<triple<Integer, String, Float>>> graph;

    ArrayList<String> forward_paths;
    ArrayList<Float> forward_paths_gains;

    ArrayList<pair<String, Float>> loops;

    public ArrayList<ArrayList<triple<Integer, String, Float>>> getGraph() {
        return graph;
    }

    public void setGraph(ArrayList<ArrayList<triple<Integer, String, Float>>> graph) {
        this.graph = graph;
    }

    private int final_node;

    public forward_loops() {
        forward_paths = new ArrayList<>();
        forward_paths_gains = new ArrayList<>();
        loops = new ArrayList<>();
    }

    private void set_false(boolean[] array) {
        for (boolean b : array) {
            b = false;
        }
    }

    private void arr_copy(boolean[] src, boolean[] dest) {
        for (int i = 0; i < src.length; i++) {
            dest[i] = src[i];
        }
    }

    /*
     * @param gain it is used to store the path in terms of sequence of nodes
     */
    private void find_forward_paths( boolean[] visited, int node, String gain, float gain_real_num) {

        String g_string = gain;
        float g_real = gain_real_num;
        visited[node] = true;
        if (node == final_node) {
            this.forward_paths.add(g_string);
            this.forward_paths_gains.add(g_real);
        }
        for (int i = 0; i < graph.get(node).size(); i++) {
            int next_node = graph.get(node).get(i).destination;
            if (!visited[next_node]) {
                triple<Integer, String, Float> neibour = graph.get(node).get(i);
                boolean[] new_visited = new boolean[graph.size()];
                this.arr_copy(visited, new_visited);
                this.find_forward_paths(new_visited, neibour.destination, gain + " " + neibour.destination,
                        gain_real_num * neibour.gain);
            }

        }

    }

    public pair<ArrayList<String>, ArrayList<Float>> start_finding_forward_paths() {
        boolean[] visited = new boolean[graph.size()];
        set_false(visited);
        visited[0] = true;
        // the first node must be node 0.
        this.final_node = graph.size() - 1; // i will send it.
        this.find_forward_paths(visited, 0, "0", (float) 1);

        // return forward paths with its gain.
        pair<ArrayList<String>, ArrayList<Float>> myPair = new pair<>();
        myPair.x = forward_paths;
        myPair.y = forward_paths_gains;
        return myPair;
    }

    private void find_all_loops(boolean[] visited, int node, String gain, float gain_real_num, int target_node, boolean Is_second_time_to_visit_target) {

        visited[node] = true;
        if (node == target_node && Is_second_time_to_visit_target) {
            pair<String, Float> p = new pair<>();
            p.x = gain;
            p.y = gain_real_num;
            loops.add(p);
        }

        if (node != target_node || (node == target_node && !Is_second_time_to_visit_target)) {
            // this is useful in not to save the empty loop in the first call & not to enter
            // infinite loop in case of self loop
            // as if a neighbour is not visited or target we will visit it even if it's
            // visited before.
            Is_second_time_to_visit_target = true;
            for (int i = 0; i < graph.get(node).size(); i++) {
                int next_node = graph.get(node).get(i).destination;
                if(!visited[next_node] || next_node == target_node){
                    triple<Integer, String, Float> neibour = graph.get(node).get(i);
                    boolean[] new_visited = new boolean[graph.size()];
                    this.arr_copy(visited, new_visited);
                    this.find_all_loops(new_visited, next_node, gain + " " + next_node, gain_real_num * neibour.gain, target_node, true);
                }
            }
        }

    }

    public ArrayList<pair<String, Float>> start_finding_all_loops() {
        boolean[] visited = new boolean[graph.size()];
        set_false(visited);
        for (int i = 0; i < graph.size(); i++) {
            this.find_all_loops(visited, i, "" + i, (float) 1, i, false);
        }
        return loops;
    }


    void print_test_loops() {
        System.out.printf("NUmber of  loops : %d \n loops : \n", loops.size());
        for (pair<String, Float> l : loops) {
            System.out.printf("loop : %s \t gain : %f\n",l.x,l.y);
        }

    }

    void print_test_fw() {
        System.out.printf("NUmber of forward paths : %d \n Forward paths : \n", forward_paths.size());
        for (String string : forward_paths) {
            System.out.println(string);
        }
        for (float f : forward_paths_gains) {
            System.out.println(f);
        }
    }
    void triple_set(triple<Integer, String, Float> t,int m, String n, float f){
        t.destination = m;
        t.y = n;
        t.gain = f;
    }
}
//import java.lang.reflect.Array;
//        import java.util.ArrayList;
//
//public class Signal_flow {
//
//
//    public static void main(String[] args) {
//        Signal_flow s = new Signal_flow();
//
//        // ArrayList<ArrayList<triple<Integer, String, Float>>> graph = new ArrayList<>();
//
//        // ArrayList<triple<Integer, String, Float>> node_0 = new ArrayList<>();
//        // ArrayList<triple<Integer, String, Float>> node_1 = new ArrayList<>();
//        // ArrayList<triple<Integer, String, Float>> node_2 = new ArrayList<>();
//        // ArrayList<triple<Integer, String, Float>> node_3 = new ArrayList<>();
//
//        // triple<Integer, String, Float> t1 = new Signal_flow.triple<>();
//        // triple<Integer, String, Float> t2 = new Signal_flow.triple<>();
//        // triple<Integer, String, Float> t3 = new Signal_flow.triple<>();
//        // triple<Integer, String, Float> t4 = new Signal_flow.triple<>();
//        // triple<Integer, String, Float> t5 = new Signal_flow.triple<>();
//
//        // // t1
//        // t1.x = 1;
//        // t1.y = "G1";
//        // t1.z = (float) 2.1;
//        // // t2
//        // t2.x = 2;
//        // t2.y = "G2";
//        // t2.z = (float) 8.3;
//        // // t3
//        // t3.x = 3;
//        // t3.y = "G4";
//        // t3.z = (float) 3.5;
//        // // t4
//        // t4.x = 2;
//        // t4.y = "G3";
//        // t4.z = (float) 6.7;
//        // // t5
//        // t5.x = 3;
//        // t5.y = "G5";
//        // t5.z = (float) 4.5;
//
//        // node_0.add(t1);
//        // node_0.add(t2);
//
//        // node_1.add(t3);
//        // node_1.add(t4);
//
//        // node_2.add(t5);
//
//        // graph.add(node_0);
//        // graph.add(node_1);
//        // graph.add(node_2);
//        // graph.add(node_3);
//
//        // loops test
//        // test 1
//        ArrayList<ArrayList<triple<Integer, String, Float>>> graph_l_1 = new ArrayList<>();
//
//        ArrayList<triple<Integer, String, Float>> node_0_l1 = new ArrayList<>();
//        ArrayList<triple<Integer, String, Float>> node_1_l1 = new ArrayList<>();
//        ArrayList<triple<Integer, String, Float>> node_2_l1 = new ArrayList<>();
//        ArrayList<triple<Integer, String, Float>> node_3_l1 = new ArrayList<>();
//
//        triple<Integer, String, Float> t1_l1 = new Signal_flow.triple<>();
//        triple<Integer, String, Float> t2_l1 = new Signal_flow.triple<>();
//        triple<Integer, String, Float> t3_l1 = new Signal_flow.triple<>();
//        triple<Integer, String, Float> t4_l1 = new Signal_flow.triple<>();
//        triple<Integer, String, Float> t5_l1 = new Signal_flow.triple<>();
//        triple<Integer, String, Float> t6_l1 = new Signal_flow.triple<>();
//
//        s.triple_set(t1_l1,1,"G1",(float) 1.1);
//        s.triple_set(t2_l1,2,"G2",(float) 2.2);
//        s.triple_set(t3_l1,0,"G3",(float) 3.3);
//        s.triple_set(t4_l1,2,"G4",(float) 4.4);
//        s.triple_set(t5_l1,1,"G5",(float) 5.5);
//        s.triple_set(t6_l1,3,"G6",(float) 6.6);
//
//        node_0_l1.add(t1_l1);
//        node_0_l1.add(t2_l1);
//
//        node_1_l1.add(t3_l1);
//        node_1_l1.add(t4_l1);
//
//        node_2_l1.add(t6_l1);
//
//        node_3_l1.add(t5_l1);
//
//
//        graph_l_1.add(node_0_l1);
//        graph_l_1.add(node_1_l1);
//        graph_l_1.add(node_2_l1);
//        graph_l_1.add(node_3_l1);
//
//        // test 2
//        ArrayList<ArrayList<triple<Integer, String, Float>>> graph_l_2 = new ArrayList<>();
//
//        ArrayList<triple<Integer, String, Float>> node_0_l2 = new ArrayList<>();
//        ArrayList<triple<Integer, String, Float>> node_1_l2 = new ArrayList<>();
//        ArrayList<triple<Integer, String, Float>> node_2_l2= new ArrayList<>();
//        ArrayList<triple<Integer, String, Float>> node_3_l2 = new ArrayList<>();
//
//        triple<Integer, String, Float> t1_l2 = new Signal_flow.triple<>();
//        triple<Integer, String, Float> t2_l2 = new Signal_flow.triple<>();
//        triple<Integer, String, Float> t3_l2 = new Signal_flow.triple<>();
//        triple<Integer, String, Float> t4_l2 = new Signal_flow.triple<>();
//        triple<Integer, String, Float> t5_l2 = new Signal_flow.triple<>();
//        triple<Integer, String, Float> t6_l2 = new Signal_flow.triple<>();
//
//        s.triple_set(t1_l2,0,"G1",(float) 1.1);
//        s.triple_set(t2_l2,1,"G2",(float) 2.2);
//        s.triple_set(t3_l2,2,"G3",(float) 3.3);
//        s.triple_set(t4_l2,3,"G4",(float) 4.4);
//        s.triple_set(t5_l2,3,"G5",(float) 5.5);
//        s.triple_set(t6_l2,0,"G6",(float) 6.6);
//
//        node_0_l2.add(t1_l2);
//        node_0_l2.add(t2_l2);
//
//        node_1_l2.add(t3_l2);
//        node_1_l2.add(t5_l2);
//
//        node_2_l2.add(t4_l2);
//        node_3_l2.add(t6_l2);
//
//
//        graph_l_2.add(node_0_l2);
//        graph_l_2.add(node_1_l2);
//        graph_l_2.add(node_2_l2);
//        graph_l_2.add(node_3_l2);
//
//        // s.start_finding_forward_paths(graph_l_1);
//        // s.print_test_fw();
//
//        s.start_finding_all_loops(graph_l_2);
//        s.print_test_loops();
//
//    }
//}
