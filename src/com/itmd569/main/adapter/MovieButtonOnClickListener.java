package com.itmd569.main.adapter;

import com.itmd569.main.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MovieButtonOnClickListener implements OnClickListener {

	private Context context;

	public MovieButtonOnClickListener(Context context) {
		this.context = context;
	}

	@Override
	public void onClick(View v) {
		Intent i = new Intent(context, MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(i);
	}

}
