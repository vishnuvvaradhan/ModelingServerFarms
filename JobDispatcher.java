/*
file name:      JobDispatcher.java
Authors:        Vishnu Varadhan
last modified:  03/24/2024
Class Purpose:  This abstract class defines the structure and functionality of a job dispatcher within a server farm simulation. 
                It manages a list of servers, distributing jobs among them based on the specific implementation of the pickServer method.
*/

import java.awt.Graphics; 
import java.awt.Color;
import java.util.ArrayList;

public abstract class JobDispatcher {
    protected ArrayList<Server> serverList;
    private int numOfJobs;
    private double time; 
    private ServerFarmViz visualViz; 

    // Constructor: Initializes the dispatcher with a specified number of servers and visualization flag.
    public JobDispatcher(int k, boolean showViz) {
        serverList = new ArrayList<>();
        numOfJobs = 0;
        time = 0;
        visualViz = new ServerFarmViz(this, showViz);

        for (int i = 0; i < k; i++) {
            serverList.add(new Server());
        }
    }

    // Returns the current system time.
    public double getTime() {
        return time;
    }

    // Returns the list of servers managed by this dispatcher.
    public ArrayList<Server> getServerList() {
        return serverList;
    }

    // Advances system time to a specified time and processes jobs in each server up to this time.
    public void advanceTimeTo(double time) {
        this.time = time;
        for (Server server : serverList) {
            server.processTo(time);
        }
    }

    // Abstract method to be implemented by subclasses for picking the appropriate server for a job.
    public abstract Server pickServer(Job j);

    // Handles a new job by advancing time, picking a server, and adding the job to the chosen server.
    public void handleJob(Job job) {
        advanceTimeTo(job.getArrivalTime());
        visualViz.repaint();
        Server chosenServer = pickServer(job);
        chosenServer.addJob(job);
        numOfJobs++;
        visualViz.repaint();
    }

    // Processes remaining jobs in each server and advances the system time to when all jobs are completed.
    public void finishUp() {
        double maxTimeToFinish = 0;
        for (Server server : serverList) {
            double serverTimeToFinish = server.remainingWorkInQueue();
            if (serverTimeToFinish > maxTimeToFinish) {
                maxTimeToFinish = serverTimeToFinish;
            }
        }
        double finishTime = this.time + maxTimeToFinish;
        for (Server server : serverList) {
            server.processTo(finishTime);
        }
        this.time = finishTime;
    }

    // Returns the total number of jobs handled by all servers.
    public int getNumJobsHandled() {
        return numOfJobs;
    }

    // Calculates and returns the average waiting time for all jobs across all servers.
    public double getAverageWaitingTime() {
        double totalWaitingTime = 0.;
        for (Server s : serverList) {
            totalWaitingTime += s.getTotalWaitingTime();
        }
        return totalWaitingTime / numOfJobs;
    }

    // Draws the current state of the server farm using the provided graphics context.
    public void draw(Graphics g) {
        double sep = (ServerFarmViz.HEIGHT - 20) / (getServerList().size() + 2.0);
        g.drawString("Time: " + getTime(), (int) sep, ServerFarmViz.HEIGHT - 20);
        g.drawString("Jobs handled: " + getNumJobsHandled(), (int) sep, ServerFarmViz.HEIGHT - 10);
        for (int i = 0; i < getServerList().size(); i++) {
            getServerList().get(i).draw(g, (i % 2 == 0) ? Color.GRAY : Color.DARK_GRAY, (i + 1) * sep, getServerList().size());
        }
    }
}
