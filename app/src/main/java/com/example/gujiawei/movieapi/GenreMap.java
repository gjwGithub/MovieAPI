package com.example.gujiawei.movieapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class GenreMap {
    private static HashMap<Integer, String> genreMap = new HashMap<>();

    public static int generateGenreMap() {
        String reply = null;
        try {
            GetRequestThread getRequestThread = new GetRequestThread(Metadata.genreListURL, Metadata.api_key);
            getRequestThread.start();
            getRequestThread.join();
            reply = getRequestThread.getReply();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
        if (reply == null)
            return -1;
        try {
            JSONObject jsonObject = new JSONObject(reply);
            JSONArray resultArray = jsonObject.getJSONArray("genres");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject item = (JSONObject) resultArray.get(i);
                Integer id = item.getInt("id");
                String name = item.getString("name");
                genreMap.put(id, name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
        return 0;
    }

    public static HashMap<Integer, String> getGenreMap() {
        return genreMap;
    }
}
