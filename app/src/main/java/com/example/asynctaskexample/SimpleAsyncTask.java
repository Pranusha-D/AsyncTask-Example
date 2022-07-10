package com.example.asynctaskexample;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Integer, Integer, String> {
    private WeakReference<TextView> mTextView;
    private WeakReference<ProgressBar> mProgress;

    SimpleAsyncTask(TextView tv, ProgressBar progressBar) {
        mTextView = new WeakReference<>(tv);
        mProgress = new WeakReference<>(progressBar);
    }


    @Override
    protected String doInBackground(Integer... params) {

                Random r = new Random();
                int n = r.nextInt(11);

                int s = n * 200;


        try {
            Thread.sleep(s);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 100; i++) {
            if(isCancelled()){
                break;
            }else{
                Log.e("In Background","current value;"+ i);
                publishProgress(i);
            }

        }


        return "Awake at last after sleeping for " + s + " milliseconds!";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mProgress.get().setProgress(values[0]);
        Log.d("onProgressUpdate", "You are in progress update ... " + values[0]);

    }

    @Override
    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }
}
