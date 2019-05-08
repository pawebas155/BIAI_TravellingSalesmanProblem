package pl.polsl.model;

import java.util.ArrayList;
import java.util.List;

public class Population {
    List<Individual> generation = new ArrayList<>();
    Integer generationSize;

    void Population(Integer generationSize, TSPGraph graph) {
        this.generationSize = generationSize;
        Individual individual;
        for (int i = 0; i < generationSize; i++) {
            individual = new Individual(graph);
            individual.createRandomRoute();
            generation.add(individual);
        }
    }

    
}
