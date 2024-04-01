/*
file name:      LeastWorkDispatcher.java
Authors:        Vishnu Varadhan
last modified:  03/24/2024
Class Purpose:  Implements the LeastWorkDispatcher strategy for dispatching jobs to servers. This class extends
                the JobDispatcher class, selecting the server with the least amount of remaining work to handle
                incoming jobs, aiming to balance the workload among servers efficiently.
*/

import java.awt.Graphics; 
import java.awt.Color;
import java.util.ArrayList;

public class LeastWorkDispatcher extends JobDispatcher {

    // Constructor: Initializes the dispatcher with a specified number of preemptive servers and a visualization flag.
    public LeastWorkDispatcher(int k, boolean showViz) {
        super(k, showViz);
    }

    // Picks the server with the least amount of remaining work to handle the new job.
    @Override
    public Server pickServer(Job j) {
        Server leastWorkServer = serverList.get(0); 
        double leastWork = leastWorkServer.remainingWorkInQueue(); 


        for (Server server : serverList) {
            double currentWork = server.remainingWorkInQueue();
            if (currentWork < leastWork) {
                leastWorkServer = server; 
                leastWork = currentWork; 
            }
        }

        return leastWorkServer; 
    }
}