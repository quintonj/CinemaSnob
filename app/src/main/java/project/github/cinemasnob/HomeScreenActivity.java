package project.github.cinemasnob;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.MovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/*
 * Home Screen
 */
public class HomeScreenActivity extends AppCompatActivity {

    private static final String API_KEY = "yedukp76ffytfuy24zsqk7f5";
    private static final int MOVIE_PAGE_LIMIT = 10;
    private ListView movieList;
//    private MovieList listOfMovies;
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Logout button sends back to login screen
        Button logout = (Button)findViewById(R.id.logout_button);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(HomeScreenActivity.this, LoginScreenActivity.class);
                startActivity(logout);
                finish();
            }
        });

        // Profile button sends to profile
        Button profile = (Button)findViewById(R.id.profile_button);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToProfile = new Intent(HomeScreenActivity.this, UserProfileActivity.class);
                startActivity(goToProfile);
            }
        });

        // RecentMovies button sends to recent movies
        Button recentMovies = (Button)findViewById(R.id.recentMovies_button);

        recentMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRecentMovie = new Intent(HomeScreenActivity.this, RecentMoviesActivity.class);
                startActivity(goToRecentMovie);
            }
        });

        // RecentDVDs button sends to recent DVDs
        Button recentDVD = (Button)findViewById(R.id.recentDVD_button);

        recentDVD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRecentDVD= new Intent(HomeScreenActivity.this, RecentDVDActivity.class);
                startActivity(goToRecentDVD);
            }
        });

        // Search for Movies
        movieList = (ListView) findViewById(R.id.movieList);
        final EditText searchBox = (EditText) findViewById(R.id.searchText);
//        searchBox.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // Do nothing
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String search = s.toString();
//                String url = "";
//                try {
//                    url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?q="
//                            + URLEncoder.encode(search,"UTF-8")
//                            + "&page_limit="
//                            + MOVIE_PAGE_LIMIT
//                            + "&page=1&apikey="
//                            + API_KEY;
//                } catch (UnsupportedEncodingException e){
//                    e.printStackTrace();
//                }
//                Log.d("URL Encode", url);
//
//                JsonObjectRequest jsonObjReq = new JsonObjectRequest (
//                        Request.Method.GET,
//                        url,
//                        null,
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                try {
//                                    JSONArray movies = response.getJSONArray("movies");
//                                    JSONObject movie = movies.getJSONObject(0);
//                                    String result = movie.getString("title");
//                                    Log.d("RESULT MOVIE TITLE", result);
//                                    // test.setText(result);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                    Toast.makeText(getApplicationContext(),
//                                            "Error: " + e.getMessage(),
//                                            Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.d("Ohfuck.jpg", "Shit fam you fucked up good");
////                                Log.d("Volley Error", error.getMessage());
//                            }
//                        });
//                RequestController.getInstance().addToRequestQueue(jsonObjReq);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // Do nothing
//            }
//        });

        //Display the movies related to the search text in the list view
        /***
         * pretty much the same code that brandon had here except now it is not a live text box
         * only makes one rest call after the entire search has been finished instead of
         * making REST calls every time user types a letter
         *
         * currently will get all related movies from the REST call and put them as a movie object
         * into a movie list class, then in the log it will display all of the titles
         *
         * COULD NOT GET ADAPTER TO WORK
         */
        Button searchForMovies = (Button)findViewById(R.id.searchMovies);
        searchForMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String movieSearchString = searchBox.getText().toString();
                final MovieList listOfMovies = new MovieList();
                String url = "";
                try {
                    url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?q="
                            + URLEncoder.encode(movieSearchString,"UTF-8")
                            + "&page_limit="
                            + MOVIE_PAGE_LIMIT
                            + "&page=1&apikey="
                            + API_KEY;
                } catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //create new movieList
                        //MovieList listOfMovies = new MovieList();
                        try {
                            JSONArray movies = response.getJSONArray("movies");
                            //one single movie
                            JSONObject movie = null;
                            //Test block to see how many movies there are that are returned in the JSON object
                            for (int i = 0; i < movies.length(); i++) {
                                movie = movies.getJSONObject(i);
                                //create a new movie
                                Movie newMovie = new Movie(movie.getString("title"), movie.getString("year"), movie.getString("mpaa_rating"));
                                listOfMovies.addMovie(newMovie);
                                //will print out all the titles of the movies that were returned from the REST call search
                                Log.d("Movie Object: ", listOfMovies.getTitleList().get(i).toString());
                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HomeScreenActivity.this,android.R.layout.simple_list_item_1, listOfMovies.getTitleList());
                            movieList.setAdapter(arrayAdapter);
//                            String result = movie.getString("title");
//                            Log.d("**Fetched Movie Title: ", result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Ohfuck.jpg", "We done goofed");
                            }
                        });
                RequestController.getInstance().addToRequestQueue(jsonObjReq);


            }
        });
    }
}
