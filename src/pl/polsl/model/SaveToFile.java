package pl.polsl.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveToFile {

    private String inputFileName;
    private String outputFileName;;
    private int populationSize;
    private int numberOfGeneration;
    private int mutationPercentageChance;
    private int surviveIndividualPercentage;
    private FileWriter fileWriter;
    PrintWriter printWriter;

    public SaveToFile(String[] args) throws IOException {
        inputFileName = args[0];
        outputFileName = args[1];
        populationSize = Integer.parseInt(args[2]);
        numberOfGeneration = Integer.parseInt(args[3]);
        mutationPercentageChance = Integer.parseInt(args[4]);
        surviveIndividualPercentage = Integer.parseInt(args[5]);

        fileWriter = new FileWriter(outputFileName);
        printWriter = new PrintWriter(fileWriter);

        saveSettingsToFile();
    }

    private void saveSettingsToFile() {
        printWriter.print("Valid input filename: " + inputFileName + "\r\n");
        printWriter.print("Output filename: " + outputFileName + "\r\n");
        printWriter.print("Size of population: " + populationSize + "\r\n");
        printWriter.print("Number of generations: " + numberOfGeneration + "\r\n");
        printWriter.print("Percentage chance of mutation: " + mutationPercentageChance + "\r\n");
        printWriter.print("Percentage of individuals that survive between generations: " + surviveIndividualPercentage + "\r\n");
        printWriter.print("\r\n");
    }

    public void addResult(String text) {

        printWriter.print(text);
    }

    public void closeConnection(){
        printWriter.close();
    }
}
