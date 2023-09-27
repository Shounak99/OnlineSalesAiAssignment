


package io.firstQues.EventSimulation;

import java.awt.geom.Arc2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/*
1)  Defined a BiasnessCalculator class that takes a dictionary outcomes_probabilities as input, where the keys are possible outcomes, and the values are their associated probabilities.

2) The validate_probabilities method checks if the total probabilities sum to approximately 1.0 (with a small tolerance).

3) The simulate_event method generates a random number between 0 and 1 and uses cumulative probabilities to select the outcome.

4) In the main method , defined the outcomes and their probabilities, create an instance of the BiasnessCalculator, and simulate the event multiple times.

 */
public class BiasnessCalculator {
    public Map<Integer,Double> outcomesAndBias; //Storing the outcomes and probability in map


    Random random;
    public BiasnessCalculator(Map<Integer,Double>  map){
        outcomesAndBias=map;
        random=new Random();

    }
    public void isTotalProbabilityValid(){

        double totalProbability=0.0;
        for(Map.Entry<Integer,Double> entry:outcomesAndBias.entrySet()){
            totalProbability+=entry.getValue();
        }
        try{
            if(Math.abs(totalProbability-1.0)>0.01){
                throw new InvalidProbabilityException("Cumulative probability should be almost equal to 1,0");
            }
        }catch (InvalidProbabilityException e){
            e.printStackTrace();
        }

    }
    public int performSimulations(){
            int result=-1;
            try {
                double probabilitySum = 0.0;
                double probability = random.nextDouble();
                for (Map.Entry<Integer, Double> events : outcomesAndBias.entrySet()) {
                    probabilitySum += events.getValue();
                    if (probability <= probabilitySum) {
                        result=events.getKey();
                    }
                }
                if(result==-1) {
                    throw new InvalidScenarioSimulationException("Event cannot be simulated");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return result;
    }

    public static void main(String[] args){


        Map<Integer,Double> map=new HashMap<>();
        //Initializing the outcomes and probabalities in map
        map.put(1,0.1);
        map.put(2,0.4);
        map.put(3,0.3);
        map.put(4,0.2);
        BiasnessCalculator biasnessCalculator=new BiasnessCalculator(map);
        biasnessCalculator.isTotalProbabilityValid();

        int numberOfSimulations=1000;
        //Calculating the result of simulations
        for(int i=0;i<numberOfSimulations;i++){
            int result=biasnessCalculator.performSimulations();
            System.out.println("The outcome of the simulation is:"+result);
        }
    }
}
