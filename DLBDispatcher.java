/*
  File name:      DLBDispatcher.java
  Authors:        Vishnu Varadhan
  Last modified:  3/26/2024
  Class Purpose:  Implements the Dynamic Load Balancing (DLB) dispatch strategy, aimed at evenly distributing
                  the workload across servers. This strategy considers both the queue length and the total
                  remaining work in each server's queue, calculating a score to determine the most suitable
                  server for each incoming job. The goal is to minimize overall waiting times and improve
                  system efficiency by balancing the load dynamically.
*/

public class DLBDispatcher extends JobDispatcher {

    // Constructor: Initializes the DLBDispatcher with a specified number of servers and visualization preferences.
    public DLBDispatcher(int k, boolean showViz) {
        super(k, showViz);
    }

    // Selects a server for the incoming job based on a calculated score, aiming for dynamic load balancing.
    @Override
    public Server pickServer(Job j) {
        Server selectedServer = serverList.get(0);
        double bestScore = Double.MAX_VALUE;
        
        // Iterate through each server to calculate and compare scores.
        for (Server server : serverList) {
            double score = calculateServerScore(server, j);
            if (score < bestScore) {
                bestScore = score;
                selectedServer = server;
            }
        }
        return selectedServer;
    }

    // Calculates a server's score based on its queue length and remaining work, using predefined weights.
    private double calculateServerScore(Server server, Job job) {
        double queueLengthWeight = 0.5; // Weight for the queue length component of the score
        double remainingWorkWeight = 0.5; // Weight for the remaining work component of the score
        return queueLengthWeight * server.size() + remainingWorkWeight * server.remainingWorkInQueue();
    }
}
