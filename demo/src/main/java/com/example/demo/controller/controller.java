package com.example.demo.controller;

import com.example.demo.Givens;
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
    public result receiveListOfLists(@RequestBody Givens myGivens) {

        connectorObj = new connector(myGivens);
        return connectorObj.getResult();
    }

}
