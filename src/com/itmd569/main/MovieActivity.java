package com.itmd569.main;

import java.io.IOException;
import java.net.URL;

import com.itmd569.main.adapter.MovieButtonOnClickListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieActivity extends Activity {

	String imageUrl;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_building);
		//We recover the data that was passed to the activity
		Bundle b = getIntent().getExtras();
		imageUrl = b.getString("imageUrl");
		//I execute it just after retrieving the url so it can start downloading the image
		new ImageAsync().execute();
		String synopsis = b.getString("synopsis");
		String title = b.getString("title");
		String rating = b.getString("rating");
		//Formatting of the building details
		String text = "Title: " + title + "\n" + "Rating: " + rating;

		//We check each piece of information is ok to be displayed
		if (text != null) {
			TextView textViewBuildingTitle = (TextView) findViewById(R.id.textViewBuildingTitle);
			textViewBuildingTitle.setText(text);
		}

		if (synopsis != null) {
			TextView textViewBuildingInfo = (TextView) findViewById(R.id.textViewBuildingInfo);
			textViewBuildingInfo.setText(synopsis);
		}

		Button button = (Button) findViewById(R.id.buttonBuilding);
		button.setOnClickListener(new MovieButtonOnClickListener(getApplicationContext()));
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.MnuOpt1:
			Intent i = new Intent(this, AboutActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
			finish();
			return true;
		case R.id.MnuOpt2:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class ImageAsync extends AsyncTask<String, Object, Void> {

		private Bitmap bmp;
		
		@Override
		protected Void doInBackground(String... params) {
			if (imageUrl != null) {
				try {
					URL url = new URL(imageUrl);
					bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void a) {
			ImageView imageViewBuilding = (ImageView) findViewById(R.id.imageViewBuilding);
			imageViewBuilding.setImageBitmap(bmp);
		}

	}
}
