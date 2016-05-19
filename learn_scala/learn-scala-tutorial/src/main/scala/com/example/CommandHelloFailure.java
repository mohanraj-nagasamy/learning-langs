package com.example;

import static org.junit.Assert.*;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.*;
import com.netflix.hystrix.examples.demo.CreditCardCommand;
import org.apache.commons.configuration.AbstractConfiguration;
import org.junit.Test;

public class CommandHelloFailure extends HystrixCommand<String> {

    private final String name;
    private static long counter = 0;

    public CommandHelloFailure(String name) {
        super(Setter
                        .withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                        .andCommandKey(HystrixCommandKey.Factory.asKey("ExampleGroup"))
                        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerErrorThresholdPercentage(10))
//                        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerRequestVolumeThreshold(10))
//                        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withCircuitBreakerSleepWindowInMilliseconds(5000))
//                        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(2000))


        );
        this.name = name;
    }

    @Override
    protected String run() throws InterruptedException {
        counter += 1;
        TimeUnit.MILLISECONDS.sleep(200);
        boolean b = counter <= 20;
        if (b) {
            System.out.println(String.format("run: counter = %s boolean %s", counter, b));
            throw new RuntimeException("run: this command always fails");
        }
        System.out.println("run: isCircuitBreakerOpen() = " + isCircuitBreakerOpen());
        return "run: " + counter + "";
    }

    @Override
    protected String getFallback() {
        System.out.println("getFallback: isCircuitBreakerOpen() = " + isCircuitBreakerOpen());
        String s = "getFallback:: Hello Failure " + counter + "!";
        return s;
    }

    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        // ignore
                    }
                    HystrixCommandMetrics metrics = HystrixCommandMetrics.getInstance(HystrixCommandKey.Factory.asKey("ExampleGroup"));
                    System.out.println("metrics\n" + getStatsStringFromMetrics(metrics));
                }
            }

            private String getStatsStringFromMetrics(HystrixCommandMetrics metrics) {
                StringBuilder m = new StringBuilder();
                if (metrics != null) {
                    HystrixCommandMetrics.HealthCounts health = metrics.getHealthCounts();
                    m.append("Requests: ").append(health.getTotalRequests()).append(" ");
                    m.append("Errors: ").append(health.getErrorCount()).append(" (").append(health.getErrorPercentage()).append("%)   ");
                    m.append("Mean: ").append(metrics.getExecutionTimePercentile(50)).append(" ");
                    m.append("75th: ").append(metrics.getExecutionTimePercentile(75)).append(" ");
                    m.append("90th: ").append(metrics.getExecutionTimePercentile(90)).append(" ");
                    m.append("99th: ").append(metrics.getExecutionTimePercentile(99)).append(" ");
                    m.append("CommandGroup").append(metrics.getCommandGroup()).append(" ");
                }
                return m.toString();
            }

        }
        );

//        thread.start();

//        ConfigurationManager.getConfigInstance().setProperty("hystrix.command.default.circuitBreaker.errorThresholdPercentage", 10);
        for (int i = 0; i < 100; i++) {
            System.out.println(new CommandHelloFailure("World").execute());
        }

        System.out.println("------------------Sleep1------------------");
        TimeUnit.SECONDS.sleep(7);
        for (int i = 0; i < 100; i++) {
            System.out.println(new CommandHelloFailure("World").execute());
        }

        System.out.println("------------------Sleep2------------------");
        TimeUnit.SECONDS.sleep(7);
        for (int i = 0; i < 100; i++) {
            System.out.println(new CommandHelloFailure("World").execute());
        }
    }

}