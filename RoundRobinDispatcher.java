/*
  file name:      RoundRobinDispatcher.java
  Authors:        Vishnu Varadhan
  last modified:  03/24/2024
  Class Purpose:  This class extends JobDispatcher to implement a RoundRobin dispatching strategy for a server farm.
                  In this strategy, jobs are assigned to servers in a cyclical manner, ensuring an even distribution
                  of workload across all servers. This approach aims to prevent any single server from becoming
                  a bottleneck due to an uneven job distribution.
*/

public class RoundRobinDispatcher extends JobDispatcher {
    private int serverSelect; 

    // Constructor: Initializes the RoundRobinDispatcher with a specified number of servers and visualization flag.
    public RoundRobinDispatcher(int k, boolean showViz) {
        super(k, showViz); 
        this.serverSelect = 0;
    }

    // Selects the next server in a round-robin fashion to assign the incoming job.
    @Override
    public Server pickServer(Job j) {
        Server server = serverList.get(serverSelect % serverList.size());
        serverSelect = (serverSelect + 1) % serverList.size(); 
        return server;
    }
}