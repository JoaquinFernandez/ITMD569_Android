package com.itmd569.main.adapter;

import java.util.List;

import com.itmd569.main.Movie;
import com.itmd569.main.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MoviesAdapter extends ArrayAdapter<Movie> { 
	
	Context context;
    int layoutResourceId;   
    List<Movie> arrayList = null;
   
    public MoviesAdapter(Context context, int layoutResourceId, List<Movie> movies) {
        super(context, layoutResourceId, movies);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.arrayList = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView row = (TextView) convertView;
		if(row == null) {
            // Get a new instance of the row layout view
            LayoutInflater inflater = LayoutInflater.from(context);
            row = (TextView) inflater.inflate(R.layout.list_items, null);
        }
		// Set the text into the textView
		row.setText(arrayList.get(position).title);
        // Apply the OnClikListener
        row.setOnClickListener(new ListItemClickListener(context, arrayList.get(position)));    
        return row;
    }
}