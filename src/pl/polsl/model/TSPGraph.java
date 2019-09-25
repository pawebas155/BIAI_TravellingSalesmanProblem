package pl.polsl.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TSPGraph {


    private int numberOfCities;
    private Double[][] distanceArray;

    public int getNumberOfCities() {
        return numberOfCities;
    }

    public Double getDistanceBetweenCities(int first, int second){
        return distanceArray[first][second];
    }

    public void initializeFromFile(String fileName) {

        ArrayList<String> input = loadFile(fileName);

        numberOfCities = extractNumberOfCitiesFromInput(input.get(input.size() - 2));
        distanceArray = new Double[numberOfCities][numberOfCities];
        Double[][] coordinatesArray = extractCoordinates(input);
        parseCoordinatesArrayToDistanceArray(coordinatesArray);
    }

    public TSPGraph(String fileName) {
        initializeFromFile(fileName);
    }


    /**
     * Method that loads raw TSP file
     *
     * @param fileName name of file
     * @return ArrayList<String> in which each element represent a single line from file
     */
    private ArrayList<String> loadFile(String fileName) {
        ArrayList<String> input = new ArrayList<>();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                input.add(scanner.nextLine());
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return input;
    }

    /**
     * Method that extracts coordinates from source file
     *
     * @param input ArrayList<String> filled using loadFile method
     * @return Double[][] with coordinates of each city
     */
    private Double[][] extractCoordinates(ArrayList<String> input) {
        Double[][] coordinatesArray = new Double[numberOfCities][2];
        boolean coordinatesSection = false;
        int coordinatesSectionLineNumber = 0;

        for (int lineNumber = 0; lineNumber < input.size() - 1; lineNumber++) {
            if (coordinatesSection) {
                String[] splitLine = input.get(lineNumber).split(" ");
                coordinatesArray[lineNumber - coordinatesSectionLineNumber - 1][0] =
                        Double.parseDouble(splitLine[1]);
                coordinatesArray[lineNumber - coordinatesSectionLineNumber - 1][1] =
                        Double.parseDouble(splitLine[2]);
            }

            if (!coordinatesSection && input.get(lineNumber).equals("NODE_COORD_SECTION")) {
                coordinatesSection = true;
                coordinatesSectionLineNumber = lineNumber;
            }
        }

        return coordinatesArray;
    }

    /**
     * Calculates a distance between each two cities
     *
     * @param coordinatesArray coordinates of cities
     */
    private void parseCoordinatesArrayToDistanceArray(Double[][] coordinatesArray) {
        for (int firstCity = 0; firstCity < numberOfCities; firstCity++) {
            distanceArray[firstCity][firstCity] = 0.0;

            for (int secondCity = firstCity + 1; secondCity < numberOfCities; secondCity++) {
                Double xDifference;
                Double yDifference;

                xDifference = coordinatesArray[firstCity][0] - coordinatesArray[secondCity][0];
                yDifference = coordinatesArray[firstCity][1] - coordinatesArray[secondCity][1];
                distanceArray[firstCity][secondCity] = Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
                distanceArray[secondCity][firstCity] = distanceArray[firstCity][secondCity];
            }
        }
    }

    /**
     * @param input second last line of input file
     * @return number of cities in given TSP dataset
     */
    private int extractNumberOfCitiesFromInput(String input) {
        int i = 0;
        while (i < input.length() && !Character.isDigit(input.charAt(i))) i++;
        int j = i;
        while (j < input.length() && Character.isDigit(input.charAt(j))) j++;
        return Integer.parseInt(input.substring(i, j));
    }
}
