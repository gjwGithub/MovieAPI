package com.example.gujiawei.movieapi;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Movie {
    private String imagePath;
    private String name;
    private int popularity;
    private ArrayList<String> genres;
    private Bitmap image;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularity() {
        return popularity;
    }

    public String getPopularityString() {
        return "Popularity: " + popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public String getGenresString() {
        if (genres.size() == 0)
            return "Genre: N.A.";
        String result = "Genre: ";
        for (int i = 0; i < genres.size() - 1; i++) {
            result += genres.get(i) + ", ";
        }
        result += genres.get(genres.size() - 1);
        return result;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
