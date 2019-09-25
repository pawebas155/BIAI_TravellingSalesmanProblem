package pl.polsl.model;

import java.util.*;

public class Algorithm {

    private int percentOfMutation; // from 0 to 100

    TSPGraph graph;
    private Population population;

    public void setPercentOfMutation(int percentOfMutation) {
        this.percentOfMutation = percentOfMutation;
    }

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
        Individual child = new Individual(graph);
        List<Integer> firstRoute = firstParent.getRoute();
        List<Integer> secondRoute = secondParent.getRoute();
        List<Integer> resultRoute = new ArrayList<>();
        List<Integer> citiesToVisit = new ArrayList<>();
        int numberOfCities = graph.getNumberOfCities();
        HashMap<Integer, HashSet<Integer>> neighbourList = new HashMap<>();
        Random random = new Random();
        Integer currentCity;

        for (int i = 0; i < numberOfCities; i++) {
            HashSet<Integer> neighbours = new HashSet<>();
            neighbours.addAll(getNeighbours(firstRoute, i));
            neighbours.addAll(getNeighbours(secondRoute, secondRoute.indexOf(firstRoute.get(i))));
            neighbourList.put(firstRoute.get(i), neighbours);
            citiesToVisit.add(i);
        }

        if (random.nextBoolean()) {
            currentCity = firstRoute.get(0);

        } else {
            currentCity = secondRoute.get(0);
        }
        resultRoute.add(currentCity);
        deleteCityFromNeighbourList(neighbourList, currentCity);
        citiesToVisit.remove(currentCity);

        while (resultRoute.size() != numberOfCities) {


            if (neighbourList.get(currentCity).size() != 0) {
                ArrayList<Integer> possibleCities = new ArrayList<>(neighbourList.get(currentCity));
                currentCity = possibleCities.get(0);

                for (int city : possibleCities) {
                    if (neighbourList.get(city).size() < neighbourList.get(currentCity).size()) {
                        currentCity = city;
                    }
                }
            } else {
                currentCity = citiesToVisit.get(random.nextInt(citiesToVisit.size()));
            }
            resultRoute.add(currentCity);
            deleteCityFromNeighbourList(neighbourList, currentCity);
            citiesToVisit.remove(currentCity);
        }

        child.setRoute(resultRoute);

        return child;
    }

    public void mutation() {
        Random random = new Random();
        Individual bestIndividual = population.getBestIndividual();

        for (Individual x : population.getGeneration()) {
            int n = random.nextInt(100);
            if (n < percentOfMutation && !x.calculateRouteLength().equals(bestIndividual.calculateRouteLength())) {
                x.swapTwoRandomCities();
            }
        }
    }


    public void evolve() {
        List<Individual> newGeneration = new ArrayList<>();
        evaluation();
        Individual best = population.getBestIndividual();
        newGeneration.add(best);
        while (newGeneration.size() < population.getGenerationSize() / 2) {
            Individual firstParent = selection();
            Individual secondParent = selection();
            while (firstParent == secondParent) {
                secondParent = selection();
            }

            Individual child = pmxCrossoverOperator(firstParent, secondParent);
//            Individual child = edgeCrossover(firstParent, secondParent);
            newGeneration.add(child);
        }

        while (newGeneration.size() < population.getGenerationSize()) {
            newGeneration.add(new Individual(selection()));
        }

        population.setGeneration(newGeneration);
        population.calculateFitnessForAllIndividuals();
        population.calculateProbability();
        evaluation();
        mutation();


    }

    private HashSet<Integer> getNeighbours(List<Integer> route, int index) {
        HashSet<Integer> result = new HashSet<>();

        if (index != route.size() - 1 && index != 0) {
            result.add(route.get(index - 1));
            result.add(route.get(index + 1));
            return result;
        }

        if (index == 0) {
            result.add(route.get(route.size() - 1));
            result.add(route.get(index + 1));
            return result;
        }

        result.add(route.get(index - 1));
        result.add(route.get(0));
        return result;
    }

    private int getNumberOfNeighbours(HashMap<Integer, HashSet<Integer>> neighbourList, int i) {
        return neighbourList.get(i).size();
    }

    private void deleteCityFromNeighbourList(HashMap<Integer, HashSet<Integer>> neighbourList, Integer cityNumber) {
        for (Map.Entry<Integer, HashSet<Integer>> entry : neighbourList.entrySet()) {

            HashSet<Integer> connections = entry.getValue();
            connections.remove(cityNumber);

        }
    }


}
