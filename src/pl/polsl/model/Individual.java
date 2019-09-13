package pl.polsl.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Individual {
    private List<Integer> route = new ArrayList<>();
    private Double routeLength;
    private Double fitness;
    private Double probability;
    private static TSPGraph graph;

    public Individual(TSPGraph graph) {
        this.graph = graph;
    }

    public Individual(Individual model){
        this.route = model.route;
    }

    public static TSPGraph getGraph() {
        return graph;
    }

    public List<Integer> getRoute() {
        return route;
    }

    public void setRoute(List<Integer> route) {this.route = route; }

    public static void setGraph(TSPGraph tspGraph) {
        graph = tspGraph;
    }

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public List<Integer> getSolution() {
        return route;
    }

    public void createRandomRoute() {
        route.clear();
        for (int i = 0; i < graph.getNumberOfCities(); i++) {
            route.add(i);
        }
        Collections.shuffle(route);
        // route.add(route.get(0));
    }

    public Double calculateRouteLength() {
        routeLength = 0.0;
        for (int i = 1; i < route.size() - 1; i++) {
            routeLength += graph.getDistanceBetweenCities(route.get(i), route.get(i - 1));
        }
        routeLength += graph.getDistanceBetweenCities(route.get(0), route.get(route.size() - 1));
        return routeLength;
    }

    public void calculateFitness() {
        fitness = 1 / calculateRouteLength();
    }

    public void swapTwoRandomCities() {
        Random random = new Random();
        int n = random.nextInt(route.size() - 2);
        n++;

        int k;
        do {
            k = random.nextInt(route.size() - 2);
            k++;
        } while (k == n);

        Integer tmp = route.get(n);
        route.set(n, route.get(k));
        route.set(k, tmp);
    }
}
