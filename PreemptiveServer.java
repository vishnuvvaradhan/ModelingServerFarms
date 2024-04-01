/*
  file name:      PreemptiveServer.java
  Authors:        Vishnu Varadhan
  last modified:  03/24/2024
  Class Purpose:  Extends the Server class to implement a Preemptive Server that prioritizes jobs
                  with the least remaining processing time. This approach aims to optimize job
                  processing by dynamically selecting the next job to process based on its
                  remaining work, potentially reducing the average waiting time for all jobs.
*/

import java.awt.Graphics; 
import java.awt.Color;
import java.awt.Toolkit;
import java.util.Comparator;
import java.awt.Font;

public class PreemptiveServer extends Server {

    // Constructor: Initializes a PreemptiveServer object by calling the parent Server constructor.
    public PreemptiveServer() {
        super();
    }

    // Processes jobs up to a specified process time, prioritizing jobs with the least remaining processing time.
    public void processTo(double processTime) {
        double timeLeft = processTime - time; 
        LinkedList<Job> jobsLinkedList = (LinkedList<Job>) jobs;

        while (numJobs > 0 && timeLeft > 0) {
            Job currentJob = jobsLinkedList.findMin(new Comparator<Job>() {
                @Override
                public int compare(Job j1, Job j2) {
                    return Double.compare(j1.getProcessingTimeRemaining(), j2.getProcessingTimeRemaining());
                }
            });

            double timeToProcess = Math.min(currentJob.getProcessingTimeRemaining(), timeLeft);
            currentJob.process(timeToProcess, time);
            timeLeft -= timeToProcess; 

            if (currentJob.isFinished()) {
                jobsLinkedList.removeMin(new Comparator<Job>() {
                    @Override
                    public int compare(Job j1, Job j2) {
                        return Double.compare(j1.getProcessingTimeRemaining(), j2.getProcessingTimeRemaining());
                    }
                });
                totalWaitingTime += currentJob.timeInQueue();
                numJobs--;
                numJobsProcessed++;
                remainingTime -= currentJob.getProcessingTimeNeeded();
            }

            time += timeToProcess;
        }

        if (timeLeft > 0) {
            time = processTime;
        }
    }
}