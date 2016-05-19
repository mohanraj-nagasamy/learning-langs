package com.akka.multijobs;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JobMain {
    public static void main(String[] args) {
        int numElements = 100_000;
        Queue<Job> jobs = new ArrayDeque<>(numElements);
        for (int i = 0; i < numElements; i++) {
            jobs.add(new Job(i));
        }

        ExecutorService threadPool = Executors.newFixedThreadPool(20);

        for (int i = 0; i < numElements; i++) {
            Job job = jobs.remove();
            threadPool.submit(new JobTask(job));
        }
    }
}

class JobTask implements Runnable {
    private Job job;

    JobTask(Job job) {
        this.job = job;
    }

    public Job job() {
        return job;
    }

    @Override
    public void run() {
        job.process();
    }
}

class Job {
    private int jobId;
    private boolean isProcessed;
    private Object lock = new Object();

    public Job(int jobId) {
        this.jobId = jobId;
    }

    public void process() {
        synchronized (lock) {
            if (isProcessed) {
                throw new IllegalStateException(jobId + " executed multiple times");
            }
            System.out.println("Processing jobId = " + jobId + " isProcessed = " + isProcessed);
            try {
                Thread.sleep(5) ;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isProcessed = true;
        }
    }

}
