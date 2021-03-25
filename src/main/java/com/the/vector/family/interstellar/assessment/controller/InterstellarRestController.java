/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.the.vector.family.interstellar.assessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.the.vector.family.interstellar.assessment.dijkstra.DijkstraAlgorithm;
import com.the.vector.family.interstellar.assessment.entity.Planet;
import com.the.vector.family.interstellar.assessment.entity.Result;
import com.the.vector.family.interstellar.assessment.repository.ResultRepository;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Vee
 * Restful service to compute the shortest path
 */
@Controller
//@ResponseBody
public class InterstellarRestController {
    
    @Autowired
    @Lazy
    private DijkstraAlgorithm dijkstraAlgorithm;
    
    @Autowired
    private ResultRepository resultRepository;
    /**
     * @param model
     * @param source
     * @param destination
     * @return returns the shortest path provided the source and destination variables
     * @Usage http://localhost:8100/shortestpath?source=A&destination=L'
     */
    @RequestMapping(path = "shortestpath", method = RequestMethod.GET)
    public String getShortestPath(Model model, @RequestParam("source") String source, @RequestParam("destination") String destination){
        String shortestPath = dijkstraAlgorithm.findShortestPath(source, destination);
        model.addAttribute("shortestPath", "The shortest path is: "+shortestPath);
        
        resultRepository.save(new Result(shortestPath, new Planet(source), new Planet(destination)));
        List<Result> results = resultRepository.findAll();
        model.addAttribute("results", results);
        
        return "index";
    }
    
    /**
     * @param model
     * @param source
     * @param destination
     * @return returns the shortest path provided the source and destination variables
     * @Usage http://localhost:8100/shortestpath/A,L'
     */
    @RequestMapping(path = "/shortestpath/{source},{destination}", method = RequestMethod.GET)
    public String test(Model model, @PathVariable("source") String source, @PathVariable("destination") String destination){
        model.addAttribute("test", dijkstraAlgorithm.findShortestPath(source, destination));
        return "test";
    }
}
