package pl.polsl.model;

public class Algorithm {

    TSPGraph graph;
    private Population population;

    public Algorithm(int populationSize, String fileName){
        graph = new TSPGraph(fileName);
        population = new Population(populationSize, graph);
    }

    public void evaluation(){
        population.calculateFitnessForAllIndividuals();
        population.sortIndividualsReversed();
        population.calculateProbability();
    }

    public Individual selection(){
        return population.getRandomIndividual();
    }

    public Individual crossover(){
        return null;
    }

    public void Evolve(){

    }
}
