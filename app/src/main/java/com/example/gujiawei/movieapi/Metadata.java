package com.example.gujiawei.movieapi;

public final class Metadata {
    public static final String api_key = "746c4f8d613074162fcb983f35234aaf";

    public static final String nowPlayingURL = "https://api.themoviedb.org/3/movie/now_playing?api_key=";
    public static final String upcomingURL = "https://api.themoviedb.org/3/movie/upcoming?api_key=";
    public static final String genreListURL = "https://api.themoviedb.org/3/genre/movie/list?api_key=";
    public static final String imageURL = "https://image.tmdb.org/t/p/w500";

    public static final String[] tabTitles = new String[]{"Now Playing", "Upcoming movies"};

    public enum TabName {NowPlaying, Upcoming};

    public static final int imageHeight = 352;
    public static final int imageWidth = 198;
}
