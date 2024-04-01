/*
  file name:      ShortestQueueDispatcher.java
  Authors:        Vishnu Varadhan
  last modified:  03/24/2024
  Class Purpose:  This class extends the JobDispatcher to implement a dispatching strategy that
                  always selects the server with the shortest queue. This strategy aims to evenly
                  distribute jobs among servers by minimizing the queue length, potentially reducing
                  the overall waiting time for jobs in a server farm simulation.
*/

public class ShortestQueueDispatcher extends JobDispatcher {

    // Constructor: Initializes the ShortestQueueDispatcher with a specified number of servers and a visualization flag.
    public ShortestQueueDispatcher(int k, boolean showViz) {
        super(k, showViz);
    }

    // Overrides the pickServer method to select and return the server with the shortest queue.
    @Override
    public Server pickServer(Job j) {
        Server shortestQueueServer = serverList.get(0);
        int shortestQueueSize = shortestQueueServer.size();

        for (Server server : serverList) {
            int currentQueueSize = server.size();
            if (currentQueueSize < shortestQueueSize) {
                shortestQueueServer = server;
                shortestQueueSize = currentQueueSize;
            }
        }

        return shortestQueueServer;
    }
}
