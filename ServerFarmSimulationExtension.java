/*
  file name:      ServerFarmSimulationExtension.java
  Authors:        Vishnu Varadhan
  last modified:  03/24/2024
  Class Purpose:  This class is designed to simulate and compare the performance of different job dispatching strategies I implemented as an extension
                  within a server farm. It assesses the average waiting time of jobs under various dispatchers and
                  analyzes the impact of changing the number of servers on the efficiency of the shortest queue dispatcher.
*/

import java.util.ArrayList;

public class ServerFarmSimulationExtension {
    // Entry point of the simulation. It compares dispatchers and analyzes the shortest queue with varying servers.
    public static void main(String[] args) {
        compareDispatchers();
        analyzeShortestQueueWithVaryingServers();
    }

    // Compares different dispatcher strategies by running simulations and printing their average waiting times.
    private static void compareDispatchers() {
        int meanArrivalTime = 3;
        int meanProcessingTime = 100;
        int numServers = 34;
        int numJobs = 10000000;
        boolean showViz = false;
        // Added "timeSensitive" and "dlb" to the array of dispatcher types to be tested
        String[] dispatcherTypes = {"random", "round", "shortest", "least", "timeSensitive", "dlb"};

        System.out.println("Dispatcher Type | Average Waiting Time");

        for (String dispatcherType : dispatcherTypes) {
            JobDispatcher dispatcher = createDispatcher(dispatcherType, numServers, showViz, meanArrivalTime, meanProcessingTime);
            runSimulation(dispatcher, numJobs);
            System.out.println(dispatcherType + "    " + dispatcher.getAverageWaitingTime());
        }
    }

    // Creates a dispatcher of a specific type for the simulation.
    private static JobDispatcher createDispatcher(String type, int numServers, boolean showViz, int meanArrivalTime, int meanProcessingTime) {
        // Added cases for "timeSensitive" and "dlb"
        switch (type) {
            case "random":
                return new RandomDispatcher(numServers, showViz);
            case "round":
                return new RoundRobinDispatcher(numServers, showViz);
            case "shortest":
                return new ShortestQueueDispatcher(numServers, showViz);
            case "least":
                return new LeastWorkDispatcher(numServers, showViz);
            case "timeSensitive":
                return new TimeSensitiveDispatcher(numServers, showViz); // Assuming this is your implemented class
            case "dlb":
                return new DLBDispatcher(numServers, showViz); // Assuming this is your implemented class
            default:
                return null;
        }
    }

    // Runs the simulation for a given dispatcher and number of jobs, then finalizes the simulation.
    private static void runSimulation(JobDispatcher dispatcher, int numJobs) {
        JobMaker jobMaker = new JobMaker(3, 100);
        for (int i = 0; i < numJobs; i++) {
            dispatcher.handleJob(jobMaker.getNextJob());
        }
        dispatcher.finishUp();
    }

    // Analyzes the performance of the Shortest Queue dispatcher as the number of servers varies.
    private static void analyzeShortestQueueWithVaryingServers() {
        int meanArrivalTime = 3;
        int meanProcessingTime = 100;
        int numJobs = 10000000;
        boolean showViz = false;

        System.out.println("\nNumber of Servers\tAverage Waiting Time -- DLB ");

        for (int numServers = 30; numServers <= 40; numServers++) {
            JobDispatcher dispatcher = new DLBDispatcher(numServers, showViz);
            runSimulation(dispatcher, numJobs);
            System.out.println(numServers + "\t\t\t\t" + dispatcher.getAverageWaitingTime());
        }


        System.out.println("\nNumber of Servers\tAverage Waiting Time -- TSD ");

        for (int numServers = 30; numServers <= 40; numServers++) {
            JobDispatcher dispatcher = new TimeSensitiveDispatcher(numServers, showViz);
            runSimulation(dispatcher, numJobs);
            System.out.println(numServers + "\t\t\t\t" + dispatcher.getAverageWaitingTime());
        }
    }
}
