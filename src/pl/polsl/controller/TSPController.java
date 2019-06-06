package pl.polsl.controller;

import pl.polsl.model.Algorithm;
import pl.polsl.model.Individual;
import pl.polsl.model.Population;
import pl.polsl.model.TSPGraph;

import java.util.ArrayList;
import java.util.List;

public class TSPController {

    public void run() {
        TSPGraph graph = new TSPGraph("data/qa194.txt");
        Individual ind1 = new Individual(graph);
        Individual ind2 = new Individual(graph);
        ind1.createRandomRoute();
        ind2.createRandomRoute();
        System.out.println(ind1.getSolution().size());
        System.out.println(ind2.getSolution().size());
        System.out.println(graph.getNumberOfCities());

        Algorithm algorithm = new Algorithm(graph,null);
        Individual child = algorithm.pmxCrossover(ind1, ind2);


        for(int i = 0; i<graph.getNumberOfCities();i++){
            System.out.print(ind1.getSolution().get(i) + " ");
        }
        System.out.println();
        for(int i = 0; i<graph.getNumberOfCities();i++){
            System.out.print(ind2.getSolution().get(i) + " ");
        }
        System.out.println();
        for(int i = 0; i<graph.getNumberOfCities();i++){
            System.out.print(child.getSolution().get(i) + " ");
        }
    }
}
