package com.example.demo.controller;

import com.example.demo.result;
import com.example.demo.service.connector;
import com.example.demo.triple;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

@CrossOrigin
@RestController
@RequestMapping("/")
public class controller {

    private connector connectorObj;

    @PostMapping("/request")
    public result receiveListOfLists(@RequestBody ArrayList<ArrayList<triple<Integer, String, Float>>> listOfLists) {
        //                for(List<triple<Integer, String, Float> > EL: listOfLists){
//            for(triple<Integer, String, Float> e: EL){
//                System.out.print("<" + e.destination + " ");
//                System.out.print(e.gain + " ");
//                System.out.print(e.y + "> ");
//            }
//            System.out.println();
//        }
//        System.out.println(listOfLists);
        connectorObj = new connector(listOfLists);
        return connectorObj.getResult();
    }

}
