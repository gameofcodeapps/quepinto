package com.gameofcode.quepinto.broadcast_reciver;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.gameofcode.quepinto.services.NoticacionService;

public class BootReceiver extends BroadcastReceiver {
    private static final int PERIOD_MS = 30000;
    @Override
    public void onReceive(Context context, Intent intent) {
        scheduleJob(context);
    }

    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, NoticacionService.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        //builder.setPeriodic(PERIOD_MS);
        builder.setMinimumLatency(PERIOD_MS);
        builder.setOverrideDeadline(PERIOD_MS);
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
    }
}
