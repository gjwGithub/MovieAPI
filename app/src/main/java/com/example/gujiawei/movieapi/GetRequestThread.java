package com.example.gujiawei.movieapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetRequestThread extends Thread {
    String url;
    String api_key;
    String reply;
    int page;

    public GetRequestThread(String url, String api_key) {
        this.url = url;
        this.api_key = api_key;
        this.reply = "";
        this.page = -1;
    }

    public GetRequestThread(String url, String api_key, int page) {
        this.url = url;
        this.api_key = api_key;
        this.reply = "";
        this.page = page;
    }

    public void run() {
        HttpURLConnection conn = null;
        String urlStr = null;
        if (page == -1)
            urlStr = url + api_key;
        else
            urlStr = url + api_key + "&page=" + page;
        InputStream is = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bufferReader = new BufferedReader(isr);
                String inputLine = "";
                while ((inputLine = bufferReader.readLine()) != null) {
                    reply += inputLine + "\n";
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getReply() {
        return reply;
    }
}
