package pl.polsl.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class Population {

    private List<Individual> generation = new ArrayList<>();
    private Integer generationSize;

    public void Population(Integer generationSize, TSPGraph graph) {

        this.generationSize = generationSize;

        Individual individual;
        for (int i = 0; i < generationSize; i++) {
            individual = new Individual(graph);
            individual.createRandomRoute();
            generation.add(individual);
        }
    }

    private Comparator<Individual> compareByProbability= (Individual o1, Individual o2) -> o1.getProbability().compareTo(o2.getProbability());

    public void sortIndividuals(){
        Collections.sort(generation, compareByProbability.reversed());
    }
}
