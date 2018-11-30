package com.example.gerardus.newsapp;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import android.os.AsyncTask;
public class NewsReminderJobService extends JobService {
    static AsyncTask back;

    @Override
    public boolean onStartJob(final JobParameters params) {
        back = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                ReminderTask.executeTask(NewsReminderJobService.this, ReminderTask.ACTION_REMINDER);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(params, false);
            }
        };
        back.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        if (back != null) back.cancel(false);
        return true;
    }
}