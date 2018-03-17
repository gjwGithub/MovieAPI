package com.example.gujiawei.movieapi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;

public class UpcomingMoviesFragment extends Fragment {

    private ProgressDialog pd = null;
    private View view = null;
    private int currentPage = 0;
    private int totalPages = 0;
    private ListView listView = null;
    private ArrayList<Movie> allMovies = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_upcoming_movies, container, false);
        listView = (ListView) view.findViewById(R.id.listView_upcoming);
        allMovies = new ArrayList<>();

        pd = ProgressDialog.show(getContext(), "Please wait", "Waiting for downloading data from themoviedb.org");
        LoadMovieDataThread loadMovieDataThread = new LoadMovieDataThread(Metadata.TabName.Upcoming, getContext(), handler, ++currentPage, allMovies);
        loadMovieDataThread.start();

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1 && currentPage <= totalPages) {
                        pd = ProgressDialog.show(getContext(), "Please wait", "Waiting for more data from themoviedb.org");
                        LoadMovieDataThread loadMovieDataThread = new LoadMovieDataThread(Metadata.TabName.NowPlaying, getContext(), handler, ++currentPage, allMovies);
                        loadMovieDataThread.start();
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            totalPages = msg.arg1;
            ListAdapter adapter = (ListAdapter) msg.obj;
            pd.dismiss();
            listView.setAdapter(adapter);
            listView.setSelection((currentPage - 1) * 20 - 1);
            return true;
        }
    });
}
