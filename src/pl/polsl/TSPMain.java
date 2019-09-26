package pl.polsl;

import pl.polsl.controller.TSPController;

public class TSPMain {

    public static void main(String[] args){

        if(args.length == 6){
            TSPController controller = new TSPController();
            controller.run(args);
        } else{
            System.out.println("miss arguments\n" +
                    "arg[0] - Valid input filename\n" +
                    "arg[1] - Output filename\n" +
                    "arg[2] - Size of population\n" +
                    "arg[3] - Number of generations\n" +
                    "arg[4] - Percentage chance of mutation\n" +
                    "arg[5] - Percentage of individuals that survive between generations\n");
        }
    }
}
