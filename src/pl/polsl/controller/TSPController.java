package pl.polsl.controller;

import pl.polsl.model.Algorithm;
import pl.polsl.model.Individual;
import pl.polsl.model.Population;
import pl.polsl.model.TSPGraph;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class TSPController {

    public void run(String filename) {
        TSPGraph graph = new TSPGraph(filename);
        Population population = new Population(1000, graph);
        population.calculateFitnessForAllIndividuals();
        population.calculateProbability();
        Algorithm algorithm = new Algorithm(graph, population, 5);
        for(int i = 0; i<5000;i++) {
            algorithm.evolve();
            System.out.println(population.getBestIndividual().calculateRouteLength());
        }

        System.out.println();

        for(int i = 0; i<graph.getNumberOfCities(); i++){
            System.out.println(population.getBestIndividual().getSolution().get(i)+1);
        }
    }
}
