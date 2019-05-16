package pl.polsl.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Algorithm {

    private int percentOfMutation; // from 0 to 100

    TSPGraph graph;
    private Population population;

    public Algorithm(int populationSize, String fileName, int percOfMutation){
        graph = new TSPGraph(fileName);
        population = new Population(populationSize, graph);
        percentOfMutation = percOfMutation;
    }

    public void evaluation(){
        population.calculateFitnessForAllIndividuals();
        population.sortIndividualsReversed();
        population.calculateProbability();
    }

    public Individual selection(){
        return population.getRandomIndividualByProbability();
    }

    public Individual crossover(){
        return null;
    }

    public void mutation(){

        int generationSize = population.getGenerationSize();
        Random random = new Random();
        Set mutatedElement = new HashSet();
        //Individual individual;

        for(int i = 0; i < ((percentOfMutation * generationSize) / (100)); i++){
            int n = random.nextInt(generationSize);
            if(mutatedElement.add(n) == true){
                population.getIndividualById(n).swapTwoRandomCities();
                //individual = population.getIndividualById(n);
                //individual.swapTwoRandomCities();
            }
            else{
                i--;
            }
        }
        mutatedElement.clear();
    }


    public void Evolve(){

    }
}
