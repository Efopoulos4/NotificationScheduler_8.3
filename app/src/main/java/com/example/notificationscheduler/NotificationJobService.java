package com.example.notificationscheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;

public class NotificationJobService extends JobService {

    private boolean jobCancelled = false;
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        doInBackground(jobParameters);
        jobCancelled = false;
        return true;
    }

    private void doInBackground(JobParameters jobParameters) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i < 20 ; i++){
                    if(jobCancelled){
                        return;
                    }
                    Log.d("paok", "run: "+ i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("paok", " Job finished: ");
                jobFinished(jobParameters, false);
            }
        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Toast.makeText(NotificationJobService.this, "Job cancelled before completion: ", Toast.LENGTH_SHORT).show();
        jobCancelled = true;
        return true;
    }
}
