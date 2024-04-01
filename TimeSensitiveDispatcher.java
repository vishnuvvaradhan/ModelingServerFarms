/*
  File name:      TimeSensitiveDispatcher.java
  Authors:        Vishnu Varadhan
  Last modified:  3/26/2024
  Class Purpose:  Implements a dispatch strategy focusing on minimizing the average processing time
                  by selecting servers based on their current job queue lengths and estimated processing times.
                  This dispatcher aims to balance the workload across servers, prioritizing those with lower
                  average job processing times to optimize overall system performance.
*/

import java.util.*;

public class TimeSensitiveDispatcher extends JobDispatcher {

    private Random rand = new Random();

    // Constructor: Initializes the TimeSensitiveDispatcher with a specified number of servers and a visualization flag.
    public TimeSensitiveDispatcher(int k, boolean showViz) {
        super(k, showViz);
    }

    // Selects a server for the job based on attempting to minimize the average processing time.
    @Override
    public Server pickServer(Job j) {
        Server selectedServer = null;
        double bestAverageTime = Double.MAX_VALUE;

        // Iterates through each server to find the one with the lowest average processing time.
        for (Server server : serverList) {
            double averageTime = server.size() > 0 ? server.remainingWorkInQueue() / server.size() : 0;

            if (averageTime < bestAverageTime) {
                bestAverageTime = averageTime;
                selectedServer = server;
            }
        }

        // Fallback to a random server selection if no server has been selected based on the average time.
        if (selectedServer == null) {
            selectedServer = serverList.get(rand.nextInt(serverList.size()));
        }

        return selectedServer;
    }
}