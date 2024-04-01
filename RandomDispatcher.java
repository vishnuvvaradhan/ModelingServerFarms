/*
  file name:      RandomDispatcher.java
  Authors:        Vishnu Varadhan
  last modified:  03/24/2024
  Class Purpose:  Extends the JobDispatcher class to implement a RandomDispatcher strategy. This dispatcher
                  randomly selects a server for each incoming job, aiming to distribute the workload
                  across servers in a non-deterministic manner. The purpose is to explore the effects of
                  random job assignment on the overall efficiency and average waiting time in a server farm simulation.
*/

import java.util.Random;

public class RandomDispatcher extends JobDispatcher {
    private final Random rand = new Random(); 

    // Constructor: Initializes the RandomDispatcher with a specified number of servers and visualization flag.
    public RandomDispatcher(int k, boolean showViz) {
        super(k, showViz);
    }
    
    // Overrides the pickServer method to randomly select and return a server for the incoming job.
    @Override
    public Server pickServer(Job j) {
       
        return serverList.get(rand.nextInt(serverList.size()));
    }
}