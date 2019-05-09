package pl.polsl.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Individual {
    List<Integer> route = new ArrayList<>();
    Double routeLength;
    private Double fitness;
    private Double probability;
    TSPGraph graph;

    public List<Integer> getSolution() {
        return route;
    }

    public void setGraph(TSPGraph graph) {
        this.graph = graph;
    }

    public Individual(TSPGraph graph) {
        this.graph = graph;
    }

    public void createRandomRoute() {
        route.clear();
        for (int i = 0; i < graph.getNumberOfCities(); i++) {
            route.add(i);
        }
        Collections.shuffle(route);
        route.add(route.get(0));
    }

    public Double calculateRouteLength() {
        routeLength = 0.0;
        for (int i = 1; i < route.size(); i++) {
            routeLength += graph.getDistanceBetweenCities(route.get(i), route.get(i - 1));
        }
        return routeLength;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Double calculateFitness(){

        return 0.0;
    }
}
