package com.jenniferhawk.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Scheduler {

    private ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
    private final Logger LOG = LoggerFactory.getLogger(Scheduler.class);
    private boolean isCanceled;

    public ScheduledFuture<?> schedule(Runnable runnable, long initialDelay, long period, TimeUnit timeUnit) {
        LOG.info("Beginning scheduler for " + runnable.toString());

        ScheduledFuture<?> timerHandle =
                scheduler.scheduleAtFixedRate(runnable, initialDelay, period, timeUnit);

        scheduler.setRemoveOnCancelPolicy(true);
        scheduler.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);

        ScheduledExecutorService  timerCancelCheckExecutor = Executors.newSingleThreadScheduledExecutor();
        timerCancelCheckExecutor.scheduleAtFixedRate(() -> {
            if( isCanceled ) timerHandle.cancel(true);  },1,1,SECONDS);

        return timerHandle;
    }

    // This is the code I need to put in whatever class I use this.
        final Runnable timer = () -> {
            // method();
        };

    void cancel() {
        isCanceled = true;
    }
}
