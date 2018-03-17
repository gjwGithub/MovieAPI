package com.example.gujiawei.movieapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    public DownloadImageTask() {
    }

    protected Bitmap doInBackground(String... urls) {
        Bitmap bitmap = null;
        String url = urls[0];
        try {
            InputStream in = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
