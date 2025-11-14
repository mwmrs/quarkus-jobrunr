package de.mwmrs;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.jobrunr.scheduling.JobScheduler;
import org.jobrunr.scheduling.cron.Cron;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/jobs")
public class JobsResource {

    @Inject
    MyService myService;

    @Inject
    MyAsyncService myAsyncService;

    @Inject
    JobScheduler jobScheduler;

    @GET
    @Path("/enqueue")
    @Produces(MediaType.TEXT_PLAIN)
    public String enqueueJobs() {
        jobScheduler.enqueue(() -> myService.performBackgroundJob("Hello from JobRunr!"));
        return "Job has been enqueued.";
    }

    @GET
    @Path("/schedule")
    @Produces(MediaType.TEXT_PLAIN)
    public String scheduleJobs() {
        jobScheduler.schedule(Instant.now().plus(5, ChronoUnit.MINUTES), () -> myService.performBackgroundJob("Hello from scheduled JobRunr!"));
        return "Future job has been enqueued.";
    }

    @GET
    @Path("/recurring")
    @Produces(MediaType.TEXT_PLAIN)
    public String recurringJobs() {
        jobScheduler.scheduleRecurrently(Cron.every5minutes(), () -> myService.performBackgroundJob("Hello from recurring JobRunr!"));
        return "Recurring job has been enqueued.";
    }

    @GET
    @Path("/async")
    @Produces(MediaType.TEXT_PLAIN)
    public String asyncJobs() {
        myAsyncService.performAsyncBackgroundJob("Hello from async JobRunr!");
        return "Async Job has been enqueued.";
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String hello() {
        String dashboard = "<a href=\"http://localhost:8000/dashboard\" target=\"_blank\">JobRunr Dashboard</a><br/>";
        String enquedJob = "<br><a href=\"/jobs/enqueue\">Enqueue a Job</a><br/>";
        String scheduledJob = "<br><a href=\"/jobs/schedule\">Schedule a Job</a><br/>";
        String recurringJob = "<br><a href=\"/jobs/recurring\">Start recurring Job</a><br/>";
        String asyncJob = "<br><a href=\"/jobs/async\">Start async Job</a><br/>";
        return dashboard + enquedJob + scheduledJob + recurringJob + asyncJob;
    }
}
