package io.firstQues.EventSimulationTest;
import io.firstQues.EventSimulation.BiasnessCalculator;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
public class BiasnessCalculatorTest {
    private BiasnessCalculator biasnessCalculator;
    @Before
    public void setUp(){
        biasnessCalculator.insertOutcomes(1,0.2);
        biasnessCalculator.insertOutcomes(2,0.3);
        biasnessCalculator.insertOutcomes(3,0.4);
        biasnessCalculator.insertOutcomes(4,0.1);
    }
    @Test
    public void performSimulationsTes(){
        for(int i=0;i<100;i++){
            int result=biasnessCalculator.performSimulations();
            assertTrue(biasnessCalculator.outcomesAndBias.containsKey(result));
        }
    }

}
