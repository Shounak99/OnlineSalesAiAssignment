


package io.firstQues.EventSimulation;

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

    double cumulativeProbability;
    Random random;
    BiasnessCalculator(){
        outcomesAndBias=new HashMap<>();
        random=new Random();
        cumulativeProbability=0.0;
    }
    public void insertOutcomes(int outcome,double probability){
        double totalProbability=0.0;
        try {
            totalProbability+=probability;
            if(totalProbability<0.0 || totalProbability>1.0){   //if totalProbability <0.0 or >1.0 then it is invalid
                throw new InvalidProbabilityException("Total probability should be between 0.0 and 1.0");
            }
            outcomesAndBias.put(outcome, probability);

        }catch(InvalidProbabilityException e){
            e.printStackTrace();
        }
        cumulativeProbability=totalProbability;
        try{
            if(Math.abs(cumulativeProbability-1.0)>0.01){   //final cumulative probability should be as close to 0.01 as possible
                throw new InvalidProbabilityException("Total probability should be between 0.0 and 1.0");
            }
        }catch(InvalidProbabilityException e){
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
                throw new InvalidScenarioSimulationException("Event cannot be simulated");
            }catch(Exception e){
                e.printStackTrace();
            }
            return result;
    }

    public static void main(String[] args){
        BiasnessCalculator biasnessCalculator=new BiasnessCalculator();

        Scanner sc=new Scanner(System.in);
        int numberOfOutcomes= sc.nextInt();
        //Initializing the outcomes and probabalities in map
        for(int i=0;i<numberOfOutcomes;i++){
            int outcome=sc.nextInt();
            double probability=sc.nextDouble();
            biasnessCalculator.insertOutcomes(outcome,probability);
        }

        int numberOfSimulations=sc.nextInt();
        //Calculating the result of simulations
        for(int i=0;i<numberOfSimulations;i++){
            int result=biasnessCalculator.performSimulations();
            System.out.println("The outcome of the simulation is:"+result);
        }
    }
}
