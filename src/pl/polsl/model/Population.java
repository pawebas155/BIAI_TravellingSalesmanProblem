package pl.polsl.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class Population {

    private List<Individual> generation = new ArrayList<>();
    private Integer generationSize;

    public Population(Integer generationSize, TSPGraph graph) {

        this.generationSize = generationSize;

        Individual.setGraph(graph);

        Individual individual;
        for (int i = 0; i < generationSize; i++) {

            individual = new Individual(graph);
            individual.createRandomRoute();
            generation.add(individual);
        }
    }

    public List<Individual> getGeneration() {
        return generation;
    }

    public void setGeneration(List<Individual> generation) {
        this.generation = generation;
    }

    private Comparator<Individual> compareByProbability = (Individual o1, Individual o2) -> o1.getProbability().compareTo(o2.getProbability());

    public void sortIndividualsReversed(){
        Collections.sort(generation, compareByProbability.reversed());
    }

    public void calculateFitnessForAllIndividuals(){
        for(Individual x : generation){
            x.calculateFitness();
        }
    }

    public void calculateProbability(){


        double sumFitness = 0.0;
        for(Individual x : generation){
            sumFitness += x.getFitness();
        }

        Double sumOfProbability = 0.0;
        for(Individual x : generation){
            x.setProbability( sumOfProbability + (x.getFitness() / sumFitness));
            sumOfProbability += (x.getFitness() / sumFitness);
        }
        generation.get(generation.size() - 1).setProbability(1.0);
    }

    // this function can return null if something went wrong
    public Individual getRandomIndividualByProbability(){

        Double randomNumber = Math.random();
        if(generation.get(0).getProbability()>randomNumber){
            return generation.get(0);
        }

        for(int i = 1; i < generationSize; i++){
            if(generation.get(i - 1).getProbability() < randomNumber && randomNumber < generation.get(i).getProbability()){
                return generation.get(i);
            }
        }
//        for(Individual x : generation){
//            if(x.getProbability() > randomNumber){
//                return x;
//            }
//        }
        return null;
    }

    public Individual getIndividualById(int id){
        return generation.get(id);
    }

    public int getGenerationSize(){
        return generation.size();
    }

    public Individual getBestIndividual(){
        Individual best = generation.get(0);
        for(Individual tmp : generation){
            if(tmp.getFitness()>best.getFitness()){
                best = tmp;
            }
        }
        return best;
    }
}
