package com.example.notificationscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int JOB_ID = 0;
    private int selectedNetworkOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void cancelJobs(View view) {
        JobScheduler mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        if (mScheduler != null) {
            mScheduler.cancel(JOB_ID);
            Toast.makeText(this, "Jobs cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    public void scheduleJob(View view) {
        RadioGroup networkOptions = findViewById(R.id.networkOptions);
        int selectedNetworkID = networkOptions.getCheckedRadioButtonId();

        if(selectedNetworkID == R.id.wifiNetwork){
            selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
        }else if(selectedNetworkID == R.id.noNetwork){
            selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
        }else{
            selectedNetworkOption = 99;
        }

        if(selectedNetworkOption != 99){
            ComponentName serviceName = new ComponentName(getPackageName(), NotificationJobService.class.getName());
            JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName);
            builder.setRequiredNetworkType(selectedNetworkOption);
            builder.setPersisted(true);

            JobScheduler mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

            JobInfo myJobInfo = builder.build();
            mScheduler.schedule(myJobInfo);
            Toast.makeText(this, "Job Scheduled, job will run when " +
                    "the constraints are met.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Please set at least one constraint", Toast.LENGTH_SHORT).show();
        }

    }
}