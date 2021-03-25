/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.the.vector.family.interstellar.assessment.dijkstra;

import com.the.vector.family.interstellar.assessment.entity.Route;
import com.the.vector.family.interstellar.assessment.repository.PlanetRepository;
import com.the.vector.family.interstellar.assessment.repository.RouteRepository;
import com.the.vector.family.interstellar.assessment.service.DataService;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 *
 * @author Vutomi Ngobeni
 */
@Service
@Lazy
public class DijkstraAlgorithmImpl implements DijkstraAlgorithm{
    
    @Autowired
    private PlanetRepository planetRepository;
    @Autowired
    private RouteRepository routesRepository;
    @Autowired
    private DataService dataService;
    
    private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph 
            = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
    
    /**
     * reading xls data and initializing the graph
     */
    @PostConstruct
    private void initWeightedGraph(){
	dataService.getDataFromFile();
        addVertex();
	addEdges();
    }
    
    /**
     * reading all the Planets(Vertex) from the database and initializing the Dijkstra's graph
     */
    public void addVertex(){
        planetRepository.findAll().stream().forEach((planet) -> {
            this.graph.addVertex(planet.getId());
        });
    }
    
    /**
     * reading all the Routes(Edges) from the database and initializing the Dijkstra's graph
     */
    private void addEdges(){
	DefaultWeightedEdge edge = null;
	for(Route route : routesRepository.findAll()) {
            String source  = route.getSource().getId();
            String destination = route.getDestination().getId();
            if(!source.equals(destination)){
		edge = this.graph.addEdge(source, destination);
            }
            
            addWeight(edge, route.getDistance());
	}
    }
    
    /**
     * adding distance(weight) to the Route(Edge)
     */
    private void addWeight(DefaultWeightedEdge edge, double weight) {
	this.graph.setEdgeWeight(edge, weight);
    }
    
    /**
     * method to find the shortest path between two nodes
     * @param source, parameter of source node
     * @param destination, parameter of destination node
     * @return, returns shortest path in the form of a string 
     */
    @Override
    public String findShortestPath(String source, String destination) {
        return DijkstraShortestPath.findPathBetween(this.graph, source, destination).toString();
    }
    
    /**
     * sets the graph reference to null so it can get garbage collected
     */
    @PreDestroy
    public void cleanup(){
        this.graph = null;
    }
}
