/*
  file name:      Server.java
  Authors:        Vishnu Varadhan
  last modified:  03/24/2024
  Class Purpose:  This class represents a server in a server farm simulation. It is designed to handle
                  job processing, tracking the total waiting time, and managing the queue of jobs based
                  on their processing needs. The Server class provides functionality to add jobs, process
                  them up to a certain time, and calculate metrics like total waiting time and remaining work.
*/

import java.awt.Graphics; 
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Font;
import java.util.LinkedList;
import java.util.Queue;

public class Server {
    protected Queue<Job> jobs;
    protected double time; 
    protected double totalWaitingTime; 
    protected double remainingTime; 
    protected int numJobs; 
    protected int numJobsProcessed;

    // Constructor: Initializes a new Server with empty job queue and resets metrics
    public Server() {
        time = 0;
        totalWaitingTime = 0;
        remainingTime = 0;
        numJobs = 0;
        numJobsProcessed = 0;
        jobs = new LinkedList<>();
    }

    // Returns the total waiting time for all jobs processed by this server
    public double getTotalWaitingTime() {
        return totalWaitingTime;
    }

    // Adds a job to the server's queue and updates metrics
    public void addJob(Job job) {
        jobs.offer(job);
        remainingTime += job.getProcessingTimeNeeded();
        numJobs++;
    }

    // Processes jobs up to the specified processTime, updating metrics accordingly
    public void processTo(double processTime) {
        double timeLeft = processTime - time;
        while (numJobs > 0 && timeLeft > 0) {
            Job currentJob = jobs.peek();
            double timeToProcess = Math.min(currentJob.getProcessingTimeRemaining(), timeLeft);
            currentJob.process(timeToProcess, time);
            timeLeft -= timeToProcess;
    
            if (currentJob.isFinished()) {
                jobs.poll();
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

    // Returns the total remaining processing time for all jobs in the queue
    public double remainingWorkInQueue() {
        return remainingTime;
    }

    // Returns the number of jobs currently in the queue
    public int size() {
        return numJobs;
    }

    // Draws the server's current state, including work and job count, using the provided Graphics context
    public void draw(Graphics g, Color c, double loc, int numberOfServers) {
        double sep = (ServerFarmViz.HEIGHT - 20) / (numberOfServers + 2.0);
        g.setColor(Color.BLACK);
        g.setFont(new Font(g.getFont().getName(), g.getFont().getStyle(), (int) (72.0 * (sep * .5) / Toolkit.getDefaultToolkit().getScreenResolution())));
        g.drawString("Work: " + (remainingWorkInQueue() < 1000 ? remainingWorkInQueue() : ">1000"), 2, (int) (loc + .2 * sep));
        g.drawString("Jobs: " + (size() < 1000 ? size() : ">1000"), 5 , (int) (loc + .55 * sep));
        g.setColor(c);
        g.fillRect((int) (3 * sep), (int) loc, (int) (.8 * remainingWorkInQueue()), (int) sep);
        g.drawOval(2 * (int) sep, (int) loc, (int) sep, (int) sep);
        if (remainingWorkInQueue() == 0) g.setColor(Color.GREEN.darker());
        else g.setColor(Color.RED.darker());
        g.fillOval(2 * (int) sep, (int) loc, (int) sep, (int) sep);
    }
}