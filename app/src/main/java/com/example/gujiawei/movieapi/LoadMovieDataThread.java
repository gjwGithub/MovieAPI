package com.example.gujiawei.movieapi;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

public class LoadMovieDataThread extends Thread {
    private Metadata.TabName tabName;
    private Context context;
    private Handler handler;
    private int page;
    private ArrayList<Movie> allMovies;

    public LoadMovieDataThread(Metadata.TabName type, Context context, Handler handler, int page, ArrayList<Movie> allMovies) {
        this.tabName = type;
        this.context = context;
        this.handler = handler;
        this.page = page;
        this.allMovies = allMovies;
    }

    @Override
    public void run() {
        int totalPages = 0;
        ArrayList<Movie> movies = null;
        if (tabName == Metadata.TabName.NowPlaying) {
            totalPages = MovieList.getNowPlayingTotalPages();
            if (page <= totalPages)
                movies = MovieList.getNowPlayingMovies(page);
        } else if (tabName == Metadata.TabName.Upcoming) {
            totalPages = MovieList.getUpcomingTotalPages();
            if (page <= totalPages)
                movies = MovieList.getUpcomingMovies(page);
        }
        allMovies.addAll(movies);
        ListAdapter listAdapter = new ListAdapter(context, R.layout.listview_item, allMovies);
        Message message = new Message();
        message.arg1 = totalPages;
        message.obj = listAdapter;
        handler.sendMessage(message);
    }
}
