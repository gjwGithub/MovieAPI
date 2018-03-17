package com.example.gujiawei.movieapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Movie> {
    private int resourceId;

    public ListAdapter(Context context, int textViewResourceId, List<Movie> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder.movieImage = view.findViewById(R.id.movieImage);
            viewHolder.movieName = view.findViewById(R.id.movieName);
            viewHolder.moviePopularity = view.findViewById(R.id.moviePopularity);
            viewHolder.movieGenre = view.findViewById(R.id.movieGenre);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        Movie movie = getItem(position);
        viewHolder.movieImage.getLayoutParams().height = Metadata.imageHeight;
        viewHolder.movieImage.getLayoutParams().width = Metadata.imageWidth;
        viewHolder.movieImage.setImageBitmap(movie.getImage());
        viewHolder.movieName.setText(movie.getName());
        viewHolder.moviePopularity.setText(movie.getPopularityString());
        viewHolder.movieGenre.setText(movie.getGenresString());
        return view;
    }

    class ViewHolder {
        ImageView movieImage;
        TextView movieName;
        TextView moviePopularity;
        TextView movieGenre;
    }
}
