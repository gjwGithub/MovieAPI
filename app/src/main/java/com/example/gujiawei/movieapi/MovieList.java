package com.example.gujiawei.movieapi;

import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MovieList {

    public static ArrayList<Movie> getNowPlayingMovies(int page) {
        return getMovies(Metadata.nowPlayingURL, page);
    }

    public static ArrayList<Movie> getUpcomingMovies(int page) {
        return getMovies(Metadata.upcomingURL, page);
    }

    private static ArrayList<Movie> getMovies(String url, int page) {
        ArrayList<Movie> result = new ArrayList<>();
        String reply = null;
        try {
            GetRequestThread getRequestThread = new GetRequestThread(url, Metadata.api_key, page);
            getRequestThread.start();
            getRequestThread.join();
            reply = getRequestThread.getReply();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        if (reply == null)
            return null;
        try {
            JSONObject jsonObject = new JSONObject(reply);
            JSONArray resultArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {
                JSONObject item = (JSONObject) resultArray.get(i);
                Movie movie = new Movie();
                movie.setName(item.getString("title"));
                movie.setPopularity(item.getInt("popularity"));
                String posterPath = item.getString("poster_path");
                if (posterPath == "null") {
                    movie.setImagePath(null);
                    movie.setImage(null);
                } else {
                    movie.setImagePath(Metadata.imageURL + posterPath);
                    DownloadImageTask downloadMovieImage = new DownloadImageTask();
                    movie.setImage(downloadMovieImage.execute(movie.getImagePath()).get());
                }
                JSONArray genres = item.getJSONArray("genre_ids");
                ArrayList<String> genreList = new ArrayList<>();
                for (int j = 0; j < genres.length(); j++) {
                    Integer id = genres.getInt(j);
                    String genre = GenreMap.getGenreMap().get(id);
                    genreList.add(genre);
                }
                movie.setGenres(genreList);
                result.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public static int getNowPlayingTotalPages() {
        return getTotalPages(Metadata.nowPlayingURL);
    }

    public static int getUpcomingTotalPages() {
        return getTotalPages(Metadata.upcomingURL);
    }

    private static int getTotalPages(String url) {
        int result = 0;
        String reply = null;
        try {
            GetRequestThread getRequestThread = new GetRequestThread(url, Metadata.api_key);
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
            result = jsonObject.getInt("total_pages");
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
        return result;
    }
}


