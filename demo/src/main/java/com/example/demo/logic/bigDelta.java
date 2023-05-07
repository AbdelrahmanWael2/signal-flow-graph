package com.example.demo.logic;

import com.example.demo.pair;
import com.example.demo.triple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import static java.lang.Integer.parseInt;

public class bigDelta {

    ArrayList<ArrayList<triple<Integer, String, Float>>> graph;
    private ArrayList<pair<String, Float>> loops;
    private ArrayList<String> nonTouchingLoops = new ArrayList<>();
    private long[] loopsByBits;
    private float ans = 1;
    private ArrayList<TreeMap<String, Boolean>> cases;
    private String expression = "1";
    public bigDelta(ArrayList<pair<String, Float>> loops, ArrayList<ArrayList<triple<Integer, String, Float>>> graph){
        this.loops = loops;
        this.graph = graph;
    }

    private void updateBits_individualLoops(){
        loopsByBits = new long[ loops.size() ];
        String[] s;
        int loopNum = 0;
        for(pair<String, Float> pair: loops){
            ans -= pair.y;

            // get nodes participate in a loop.
            System.out.println("in updateBits_individualLoops:" + pair.x);
            s = pair.x.split(" ");
            loopsByBits[loopNum] = 0;
            for(int i = 0; i < s.length; i++){
                loopsByBits[loopNum] |= (1L << parseInt(s[i]));
            }
            expression += " -L" + Integer.toString(loopNum);
            loopNum++;
        }
    }

    public void initializeCases(){
        cases = new ArrayList<>();
        for(int i = 0; i <= loops.size(); i++){
            cases.add(new TreeMap<>());
        }
    }

    public void doubleLoops(){

        for(int i = 0; i < loops.size(); i++){
            for(int j = i + 1;j < loops.size(); j++){
                char[] chars = new char[loops.size()];
                Arrays.fill(chars, '0');
                chars[i] = '1';
                chars[j] = '1';
                String str = new String(chars, 0, loops.size());
                if( (loopsByBits[i] & loopsByBits[j]) == 0 ){
                    cases.get(2).put(str, true);
                    ans += (loops.get(i).y * loops.get(j).y);
                    nonTouchingLoops.add("L" + i + "L" + j);
                    System.out.print( str + " : true ");
                    expression += " +L" + i + "L" + j;
                } else {
                    cases.get(2).put(str, false); // 0001100
                }
            }
        }
        System.out.println();
    }

    public void allCases(int currentIndex, String str, int currentOnes){
        if( currentIndex == loops.size() ){
            if( currentOnes > 2 ){
                cases.get(currentOnes).put(str, true);
            }
        } else {
            allCases(currentIndex+1, str + '0', currentOnes);
            allCases(currentIndex+1, str + '1', currentOnes + 1);
        }
    }



    public float getDelta(){
        initializeCases();
        allCases(0, "", 0);
        updateBits_individualLoops();
        doubleLoops();
        // for all levels
        for(int i = 3; i < loops.size(); i++){
            TreeMap<String, Boolean> level = cases.get(i);

            // for all loop combinations in a level
            for(String myEntry: level.keySet()){
                char[] entry = myEntry.toCharArray();

                // for combined loop in a combination.
                float TF_loops = 1;
                String loopsParticipated = "";// 10011
                for(int k = 0; k < entry.length; k++){
                    char[] child = entry.clone();
                    if( level.get(myEntry) == false )
                        break;
                    if(child[k] == '1'){
                        child[k] = '0';
                        String strChild = new String(child, 0, loops.size());
                        level.put(myEntry, level.get(myEntry) & cases.get(i-1).get(strChild));
                        TF_loops *= loops.get(k).y;
                        loopsParticipated += "L" + k;
                    }
                }
                if( level.get(myEntry) ){
                    ans += TF_loops * ((i % 2 == 0)? 1: -1);
                    expression += ((i % 2 == 0)? " +": " -");
                    expression += loopsParticipated;
                    nonTouchingLoops.add(loopsParticipated);
                    System.out.print( myEntry + " : true " );
                } else {

                }
            }
            System.out.println();
        }
        return ans;
    }

    public ArrayList<String> getNonTouchingLoops() {
        return nonTouchingLoops;
    }

    public String getExpression() {
        return expression;
    }
}


// string is immutable.
// remember initialization
