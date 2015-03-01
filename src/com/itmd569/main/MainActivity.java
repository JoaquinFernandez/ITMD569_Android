package com.itmd569.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.itmd569.main.adapter.MoviesAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ProgressDialog progressDialog;

	private List<Movie> movies = new ArrayList<Movie>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progressDialog = new ProgressDialog(this);
		new JSONAsync().execute();
	}

	public void resetMainScreen() {
		ListView list = (ListView) findViewById(R.id.listView);
		MoviesAdapter adapter = new MoviesAdapter(getApplicationContext(), 
				R.id.textViewlist, movies);
		// Assign adapter to ListView
		list.setAdapter(adapter);
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
	private class JSONAsync extends AsyncTask<String, Object, Void> {

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			progressDialog.setMessage("Loading");
			progressDialog.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			//It gets the buildings and buildings name from JSON and 
			String buildings = readBuildings();
			try {
				JSONObject buildingsObject = new JSONObject(buildings);
				JSONArray moviesObject = buildingsObject.getJSONArray("movies");
				int length = moviesObject.length();
				for (int i = 0; i < length; i++) {
					JSONObject movie = (moviesObject.getJSONObject(i));
					String title = movie.getString("title");
					String rating = movie.getJSONObject("ratings").getString("audience_score");
					String synopsis = movie.getString("synopsis");
					String poster = movie.getJSONObject("posters").getString("detailed");
					Movie newMovie = new Movie(title, rating, synopsis, poster);
					movies.add(newMovie);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			System.out.println("");;
			return null;
		}

		@Override
		protected void onPostExecute(Void a) {
			super.onPostExecute(a);
			progressDialog.dismiss();

			//Creates the listview and applies the adapter
			ListView list = (ListView) findViewById(R.id.listView);
			MoviesAdapter adapter = new MoviesAdapter(getApplicationContext(), 
					R.id.textViewlist, movies);
			// Assign adapter to ListView
			list.setAdapter(adapter);
		}

		public String readBuildings() {
			return postJSON("http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?apikey=f3nf55g8ezu9d8tvwrth9256");

		}


		private String postJSON(String stringURL) {
			StringBuilder builder = new StringBuilder();
			// Create a new HttpClient and Post Header
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(stringURL);

			try {
				// Add your data
				//List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				//nameValuePairs.add(new BasicNameValuePair("id", "12345"));
				//nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
				//httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				httpget.addHeader("Content-Type","application/json; charset=utf-8");

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}

			return builder.toString();
		}
	}

}
