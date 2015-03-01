package com.itmd569.main.adapter;

import com.itmd569.main.MovieActivity;
import com.itmd569.main.Movie;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ListItemClickListener implements OnClickListener {

	private Context context;
	private Movie movie;
	private boolean flag = false;

	public ListItemClickListener(Context context, Movie movie) {
		this.context = context;
		this.movie = movie;
	}

	@Override
	public void onClick(View v) {
		TextView text = (TextView) v;
		if (flag)
			text.setText(movie.title);
		else {
			Intent i = new Intent(context, MovieActivity.class);
			Bundle bundle = new Bundle();
			
			//Data the activity needs to display the buldings ingo
			bundle.putString("synopsis", movie.synopsis);
			bundle.putString("title", movie.title);
			bundle.putString("imageUrl", movie.posterUrl);
			bundle.putString("rating", movie.rating);
			i.putExtras(bundle);
			//Flags for the new activity
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(i);
		}
		flag = !flag;
	}

}
