package com.sendinfo.androidkit.demo;

import android.os.AsyncTask;

public class MyAsynTask extends AsyncTask<String,Double,Boolean> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... strings) {

        return null;
    }

    @Override
    protected void onProgressUpdate(Double... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

    }

}
