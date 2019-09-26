package pl.polsl.controller;

import pl.polsl.model.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class TSPController {

    private String input;
    private String output;
    private int populationSize;
    private int numberOfGeneration;
    private int mutationPercentageChance;
    private int surviveIndividualPercentage;

    public void run(String[] args) {

        input = args[0];
        output = args[1];
        populationSize = Integer.parseInt(args[2]);
        numberOfGeneration = Integer.parseInt(args[3]);
        mutationPercentageChance = Integer.parseInt(args[4]);
        surviveIndividualPercentage = Integer.parseInt(args[5]);

        SaveToFile saver = null;
        try{
            saver = new SaveToFile(args);

            TSPGraph graph = new TSPGraph(input);
            Population population = new Population(populationSize, graph);//todo rozmiar populacji
            population.calculateFitnessForAllIndividuals();
            population.calculateProbability();
            Algorithm algorithm = new Algorithm(graph, population, mutationPercentageChance, surviveIndividualPercentage);//todo procent mutacji
            for(int i = 0; i < numberOfGeneration; i++) {//todo ilosc iteracji
                algorithm.evolve();
                saver.addResult(population.getBestIndividual().calculateRouteLength().toString() + "\r\n");
                //System.out.println(population.getBestIndividual().calculateRouteLength());
            }

            saver.addResult("\r\n");
            System.out.println();

            for(int i = 0; i<graph.getNumberOfCities(); i++){
                saver.addResult(String.valueOf(population.getBestIndividual().getSolution().get(i) + 1) + "\r\n");
                //System.out.println(population.getBestIndividual().getSolution().get(i)+1);
            }
        }
        catch(Exception ex){
        }
        finally {
            saver.closeConnection();
        }
    }
}
