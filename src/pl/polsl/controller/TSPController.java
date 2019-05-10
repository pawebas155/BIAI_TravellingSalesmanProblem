package pl.polsl.controller;

import pl.polsl.model.Algorithm;
import pl.polsl.model.TSPGraph;

public class TSPController {

    public void run() {
        //TSPGraph graph = new TSPGraph("data/qa194.txt");
        Algorithm algorithm = new Algorithm(1000, "data/qa194.txt");
    }
}
