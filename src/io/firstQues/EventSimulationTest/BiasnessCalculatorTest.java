package io.firstQues.EventSimulationTest;
import io.firstQues.EventSimulation.BiasnessCalculator;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class BiasnessCalculatorTest {
    private BiasnessCalculator biasnessCalculator;
    private Map<Integer,Double> map;
    @Before
    public void setUp(){
        map = new HashMap<>();
        map.put(1,0.2);
        map.put(2,0.7);
        map.put(3,0.1);
        biasnessCalculator=new BiasnessCalculator(map);


    }

    @Test
    public void isProbabilityValidTest(){
        double totalProbability=0.0;
        for(Double values:biasnessCalculator.outcomesAndBias.values()){
            totalProbability+=values;
        }
        assertEquals(1.0,totalProbability,0.01);
    }

    @Test
    public void performSimulationsTest(){
        for(int i=0;i<100;i++){
            int result=biasnessCalculator.performSimulations();
            assertTrue(biasnessCalculator.outcomesAndBias.containsKey(result));
        }
    }

}
