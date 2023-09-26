package io.SecondQues.ExpressionEvaluator;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/*
*1) We defined the evaluateExpression function that sends an HTTP POST request to the API to evaluate a single expression and returns the result or an error message.

2) The submitExpression function processes a batch of expressions concurrently using a ThreadPoolExecutor with a limited number of workers.

3) In the buildBatches function,  split the list of expressions into batches to respect the API's rate limitations (e.g., 50 requests per second per client).

4) In main function initialize the list of expression and call onto buildBatches Function,submitExpressionFunction and evaluateExpression function We iterate through result obtained and  print result of each expression.


* */

public class ExpressionEvaluator {
    private static final String URL="https://OnlineSales.ai/evaluate-expression";
    private static final int batchSize=50;
    public static List<List<String>> buildBatches(List<String> expressions,int noOfExpressions){
        List<List<String>> expressionBatches=new ArrayList<>();
        for(int i=0;i<noOfExpressions;i+=batchSize){
            int endIdx=Math.min(i+batchSize,noOfExpressions);
            List<String> expression=expressions.subList(i,endIdx);
            expressionBatches.add(expression);
        }
        return expressionBatches;
    }
    public static List<String> submitExpression(List<List<String>> expressionBatches){
        List<String> results=new ArrayList<>();
        int threadPoolSize=10;
        ExecutorService executorService= Executors.newFixedThreadPool(threadPoolSize);
        for(List<String> expressions:expressionBatches){
            List<Future<String>> futures=new ArrayList<>();
            for(String expression:expressions){
                futures.add(executorService.submit(()->evaluateExpression(expression)));
            }
            for(Future<String> future:futures){
                try {
                    results.add(future.get());
                } catch (InterruptedException |ExecutionException e) {
                    e.printStackTrace();
                }
                finally {
                    executorService.shutdown();
                }
            }
        }

        return results;
    }
    public static String evaluateExpression(String expression){
        HttpClient httpClient=HttpClient.newHttpClient();
        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"expression\":\""+expression+"\"}"))
                .build();
        try {
            HttpResponse<String> response=httpClient.send(request,HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()==200){
                return "Expression:"+expression+", Result:"+response.body();
            }
            else{
                return "Expression:"+expression+", Error:"+response.statusCode();
            }
        } catch (IOException |InterruptedException e) {
            return "Expression: "+expression+", Error: "+e.getMessage();
        }

    }
    public static void main(String[] args){
        List<String> expressions= Arrays.asList("5+20","100/2","sqrt(4)","5*4","100-5");
        int noOfExpressions=expressions.size();
        List<List<String>> expressionBatches=buildBatches(expressions,noOfExpressions);
        List<String> results=submitExpression(expressionBatches);
        for(String result:results){
            System.out.println(result);
        }
    }

}
