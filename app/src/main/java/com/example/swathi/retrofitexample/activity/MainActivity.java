package com.example.swathi.retrofitexample.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.swathi.retrofitexample.R;
import com.example.swathi.retrofitexample.adapter.MoviesAdapter;
import com.example.swathi.retrofitexample.model.Movie;
import com.example.swathi.retrofitexample.model.MovieResponse;
import com.example.swathi.retrofitexample.rest.ApiClient;
import com.example.swathi.retrofitexample.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private final static String API_KEY = "b467b494722fc21e4a8c3a21405e3775";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(),"please obtain api key",Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call = apiInterface.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
                Log.d(TAG,"number of movies recieved: "+ movies.size());
                recyclerView.setAdapter(new MoviesAdapter(movies,R.layout.list_item_movie,getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(TAG,t.toString());
            }
        });
    }
}
