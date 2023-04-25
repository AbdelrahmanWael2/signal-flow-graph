import java.lang.reflect.Array;
import java.util.ArrayList;

public class Signal_flow {
    static class triple<X, Y, Z> {
        public X x; // next node
        public Y y; // gain
        public Z z; // float number of gain
    }

    ArrayList<String> forward_paths;
    ArrayList<Float> forward_paths_gains;

    private int final_node;

    Signal_flow() {
        forward_paths = new ArrayList<>();
        forward_paths_gains = new ArrayList<>();
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

    private void find_forward_paths(ArrayList<ArrayList<triple<Integer, String, Float>>> graph, boolean[] visited, int node,
            String gain, float gain_real_num) {

        String g_string = gain;
        float g_real = gain_real_num;
        int last_visited = 0;
        visited[node] = true;       
        if (node == final_node) {
            this.forward_paths.add(g_string);
            this.forward_paths_gains.add(g_real);
        }
        for (int i = 0; i < graph.get(node).size(); i++) {
            int next_node = graph.get(node).get(i).x;
            if ( !visited[next_node] ) {
                triple<Integer, String, Float> neibour = graph.get(node).get(i);
                boolean[] new_visited = new boolean[graph.size()];
                this.arr_copy(visited, new_visited);
                this.find_forward_paths(graph, new_visited, neibour.x, gain + " " + neibour.y, gain_real_num * neibour.z);
            }

        }

    }

    public void start_finding_forward_paths(ArrayList<ArrayList<triple<Integer, String, Float>>> graph) {
        boolean[] visited = new boolean[graph.size()];
        set_false(visited);
        visited[0] = true;
        // the first node must be node 0.
        this.final_node = graph.size() - 1;

        this.find_forward_paths(graph, visited, 0, "", (float) 1);
        
    }

    void print_test(){
        System.out.printf("NUmber of forward paths : %d \n Forward paths : \n",forward_paths.size());
        for (String string : forward_paths) {
            System.out.println(string);
        }
        for (float f : forward_paths_gains) {
            System.out.println(f);
        }
    }
    public static void main(String[] args) {
        ArrayList<ArrayList<triple<Integer, String, Float>>> graph = new ArrayList<>();

        ArrayList< triple<Integer, String, Float> > node_0 = new ArrayList<>();
        ArrayList< triple<Integer, String, Float> > node_1 = new ArrayList<>();
        ArrayList< triple<Integer, String, Float> > node_2 = new ArrayList<>();
        ArrayList< triple<Integer, String, Float> > node_3 = new ArrayList<>();

        triple<Integer, String, Float> t1 = new Signal_flow.triple<>();
        triple<Integer, String, Float> t2 = new Signal_flow.triple<>();
        triple<Integer, String, Float> t3 = new Signal_flow.triple<>();
        triple<Integer, String, Float> t4 = new Signal_flow.triple<>();
        triple<Integer, String, Float> t5 = new Signal_flow.triple<>();


        //t1
        t1.x = 1;
        t1.y = "G1";
        t1.z = (float) 2.1;
        //t2
        t2.x = 2;
        t2.y = "G2";
        t2.z = (float) 8.3;
        //t3
        t3.x = 3;
        t3.y = "G4";
        t3.z = (float) 3.5;
        //t4
        t4.x = 2;
        t4.y = "G3";
        t4.z = (float) 6.7;
        // t5
        t5.x = 3;
        t5.y = "G5";
        t5.z = (float) 4.5;

        node_0.add(t1);
        node_0.add(t2);

        node_1.add(t3);        
        node_1.add(t4);

        node_2.add(t5);

        graph.add(node_0);
        graph.add(node_1);
        graph.add(node_2);
        graph.add(node_3);

        Signal_flow s = new Signal_flow();
        s.start_finding_forward_paths(graph);
        s.print_test();

    }
}
