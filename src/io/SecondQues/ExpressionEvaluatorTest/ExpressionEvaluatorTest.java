package io.SecondQues.ExpressionEvaluatorTest;
import io.SecondQues.ExpressionEvaluator.ExpressionEvaluator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ExpressionEvaluatorTest {
    private ExpressionEvaluator expressionEvaluator;

    @BeforeEach
    void setUp() {
        expressionEvaluator = new ExpressionEvaluator();
    }

    @Test
    void testSubmitExpression(){
        List<String> expressions= Arrays.asList("5+9","6*9","9/1","sqrt(4)");
        List<List<String>> batches=expressionEvaluator.buildBatches(expressions,expressions.size());
        List<String> result=expressionEvaluator.submitExpression(batches);
        assertEquals(result.size(),expressions.size());

    }

    @Test
    void testEvaluateExpressions() {
        List<String> expressions = Arrays.asList("2+2", "3*4", "5/0", "sqrt(16)");
        int n=expressions.size();
        List<List<String>> batches=expressionEvaluator.buildBatches(expressions,n);

        List<String> results = expressionEvaluator.submitExpression(batches);

        assertEquals(expressions.size(), results.size());

        // Check if results contain "Result:" or "Error:"
        for (String result : results) {
            assertTrue(result.contains("Result:") || result.contains("Error:"));
        }
    }


}
