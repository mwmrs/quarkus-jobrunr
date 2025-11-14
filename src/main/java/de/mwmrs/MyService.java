package de.mwmrs;

import org.jobrunr.jobs.annotations.Job;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MyService {
    
    @Job(name = "Just a simple job")
    public void performBackgroundJob(String message) {
        System.out.printf("Executing background job: %s", message);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Background job completed.");
    }

}
