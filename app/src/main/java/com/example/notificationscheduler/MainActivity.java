package com.example.notificationscheduler;

import androidx.appcompat.app.AppCompatActivity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int JOB_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button downloadButton = findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleJob();
            }
        });
        scheduleJob();
    }

    public void scheduleJob() {
        ComponentName serviceName = new ComponentName(getPackageName(),
                NotificationJobService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresCharging(true)
                .setRequiresDeviceIdle(true);

        JobInfo myJobInfo = builder.build();
        JobScheduler mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        mScheduler.schedule(myJobInfo);
        Toast.makeText(this, "Downloading will start when the constraints are met.", Toast.LENGTH_SHORT).show();
    }
}