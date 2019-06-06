package pl.polsl.model;

import java.util.*;

public class Algorithm {

    private int percentOfMutation; // from 0 to 100

    TSPGraph graph;
    private Population population;

    public Algorithm(int populationSize, String fileName, int percOfMutation) {
        graph = new TSPGraph(fileName);
        population = new Population(populationSize, graph);
        percentOfMutation = percOfMutation;
    }

    public Algorithm(TSPGraph graph, Population population, int percOfMutation) {
        this.graph = graph;
        this.population = population;
        this.percentOfMutation = percOfMutation;
    }

    public void evaluation() {
        population.calculateFitnessForAllIndividuals();
        population.sortIndividualsReversed();
        population.calculateProbability();
    }

    public Individual selection() {
        return population.getRandomIndividualByProbability();
    }

    public Individual pmxCrossoverOperator(Individual firstParent, Individual secondParent) {
        //FOR TESTING
//        Integer[] route1 = {3,0,1,2};
//        firstParent.setRoute(Arrays.asList(route1));
//        Integer[] route2 = {2,3,0,1};
//        secondParent.setRoute(Arrays.asList(route2));
//
//        for(int i = 0; i<graph.getNumberOfCities();i++){
//            System.out.print(firstParent.getSolution().get(i) + " ");
//        }
//        System.out.println();
//        for(int i = 0; i<graph.getNumberOfCities();i++){
//            System.out.print(secondParent.getSolution().get(i) + " ");
//        }


        Individual child = new Individual(graph);
        List<Integer> newRoute = new ArrayList<>();
        Integer startCity = (int) (Math.random() * graph.getNumberOfCities());
        Integer endCity = (int) (Math.random() * graph.getNumberOfCities());

        while (startCity == endCity) {
            startCity = (int) (Math.random() * graph.getNumberOfCities());
            endCity = (int) (Math.random() * graph.getNumberOfCities());
        }

        if (startCity > endCity) {
            Integer tmp = startCity;
            startCity = endCity;
            endCity = tmp;
        }

        for (int i = 0; i < graph.getNumberOfCities(); i++) {
            newRoute.add(-1);
        }

        for (int i = startCity; i < endCity + 1; i++) {
            newRoute.set(i, firstParent.getSolution().get(i));
        }

        System.out.println("Start City:" + startCity);
        System.out.println(("End City:" + endCity));

        //Check which cities between startCity and endCity in parent2 are not yet in newRoute
        List<Integer> missingCities = new ArrayList<>();

        for (int i = startCity; i < endCity + 1; i++) {
            if (!newRoute.contains(secondParent.getSolution().get(i))) {
                missingCities.add(secondParent.getSolution().get(i));
            }
        }


        for (int i = 0; i < missingCities.size(); i++) {
            int positionOfCityInSecondParent = secondParent.getSolution().indexOf(missingCities.get(i));
            int cityBlockingPosition = firstParent.getSolution().get(positionOfCityInSecondParent);
            int position = secondParent.getSolution().indexOf(cityBlockingPosition);

            while (newRoute.get(position) != -1) {
                cityBlockingPosition = firstParent.getSolution().get(position);
                position = secondParent.getSolution().indexOf(cityBlockingPosition);
            }
            newRoute.set(position, missingCities.get(i));

        }


        //Rest of cities
        for (int i = 0; i < secondParent.getSolution().size(); i++) {
            if (!newRoute.contains(secondParent.getSolution().get(i))) {
                newRoute.set(i, secondParent.getSolution().get(i));
            }
        }

        child.setRoute(newRoute);
        return child;
    }

    public Individual edgeCrossover(Individual firstParent, Individual secondParent) {
        Integer currentNode, nextNode;
        Individual child = new Individual(graph);
        List<List<Integer>> neighbourList = new ArrayList<List<Integer>>();

        for (int i = 0; i < graph.getNumberOfCities(); i++) {
            neighbourList.add(new ArrayList<Integer>());
        }


        return child;

    }

    public void mutation() {

        int generationSize = population.getGenerationSize();
        Random random = new Random();
        Set mutatedElement = new HashSet();
        //Individual individual;

        for (int i = 0; i < ((percentOfMutation * generationSize) / (100)); i++) {
            int n = random.nextInt(generationSize);
            if (mutatedElement.add(n) == true) {
                population.getIndividualById(n).swapTwoRandomCities();
                //individual = population.getIndividualById(n);
                //individual.swapTwoRandomCities();
            } else {
                i--;
            }
        }
        mutatedElement.clear();
    }


    public void evolve() {
        List<Individual> newGeneration = new ArrayList<>();
        evaluation();

        while (newGeneration.size() < population.getGenerationSize()) {
            Individual firstParent = selection();
            Individual secondParent = selection();
            while (firstParent == secondParent) {
                secondParent = selection();
            }

            Individual child = pmxCrossoverOperator(firstParent, secondParent);
            newGeneration.add(child);
        }

        mutation();

        population.setGeneration(newGeneration);
        System.out.println(population.getBestIndividual().getFitness());

    }
}
