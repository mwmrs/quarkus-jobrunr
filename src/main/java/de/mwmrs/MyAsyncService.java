package de.mwmrs;

import org.jobrunr.jobs.annotations.AsyncJob;
import org.jobrunr.jobs.annotations.Job;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@AsyncJob
public class MyAsyncService {

    @Job(name = "Just a simple async job")
    public void performAsyncBackgroundJob(String message) {
        System.out.printf("Executing async background job: %s", message);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Async background job completed.");
    }
}
