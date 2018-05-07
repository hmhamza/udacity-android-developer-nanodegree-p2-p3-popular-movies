package com.example.android.popularmovies2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies2.data.ContentProvider_FavoriteMovies;
import com.example.android.popularmovies2.data.Contract_FavoriteMovies;
import com.example.android.popularmovies2.data.DbHelper_FavoriteMovies;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Main extends AppCompatActivity{

    static final int POPULAR=1;
    static final int TOP_RATED=2;
    static final int FAVORITE=3;
    int currentFilter,currentGridviewPosition;

    String STATE_CURRENT_FILTER="currentFilter";
    String STATE_CURRENT_GRIDVIEW_POSITION="currentPosition";

    @Nullable @BindView(R.id.gridview)
    GridView gridview;

    @BindView(R.id.no_favorites_tv)
    TextView no_favorites_tv;

    @Nullable @BindView(R.id.nointernetmsg_tv)
    TextView noInternetMsg_tv;

    @Nullable @BindView(R.id.retry_btn)
    Button retry_btn;

    Retrofit retrofit;
    TheMovieDbAPI api;

    MoviesList moviesList=null;
    Adapter_Movie movieAdapter;

    AlertDialog.Builder alertDialogBuilder;

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            currentFilter = savedInstanceState.getInt(STATE_CURRENT_FILTER);
            currentGridviewPosition=savedInstanceState.getInt(STATE_CURRENT_GRIDVIEW_POSITION);

        } else {
            // Probably initialize members with default values for a new instance
            currentFilter=POPULAR;
            currentGridviewPosition=0;
        }

        DbHelper_FavoriteMovies dbHelper = new DbHelper_FavoriteMovies(this);
        mDb = dbHelper.getReadableDatabase();

        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(R.string.no_internet_msg_short);
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                }
        );

        if(!isOnline()) {
            setContentView(R.layout.no_internet);
            ButterKnife.bind(this);

            noInternetMsg_tv.setText(R.string.no_internet_msg_long);

            retry_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isOnline()) {
                        continueSetup();
                    }
                }
            });
        }
        else{
            continueSetup();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(STATE_CURRENT_FILTER, currentFilter);
        savedInstanceState.putInt(STATE_CURRENT_GRIDVIEW_POSITION, gridview.getFirstVisiblePosition());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    private void continueSetup(){
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        retrofit = new Retrofit.Builder()
                .baseUrl(TheMovieDbAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(TheMovieDbAPI.class);

        movieAdapter = new Adapter_Movie(Activity_Main.this, moviesList);
        gridview.setAdapter(movieAdapter);

        switch (currentFilter){
            case POPULAR:
                showPopularMovies();
                break;
            case TOP_RATED:
                showTopRatedMovies();
                break;
            case FAVORITE:
                showFavoriteMovies();
                break;
        }

        gridview.post(new Runnable() {
            @Override
            public void run() {
                try {
                    gridview.setSelection(currentGridviewPosition);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(Activity_Main.this, Activity_Details.class);
                intent.putExtra("movie", moviesList.getSingleMovie(position));
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.movie_filter_menu, menu);

        if(currentFilter==POPULAR){
            MenuItem item = menu.findItem(R.id.popular_mi);
            item.setVisible(false);
        }
        else if(currentFilter==TOP_RATED){
            MenuItem item = menu.findItem(R.id.toprated_mi);
            item.setVisible(false);
        }
        else if(currentFilter==FAVORITE){
            MenuItem item = menu.findItem(R.id.favorite_mi);
            item.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.popular_mi:
                showPopularMovies();
                invalidateOptionsMenu();
                break;
            case R.id.toprated_mi:
                showTopRatedMovies();
                invalidateOptionsMenu();
                break;
            case R.id.favorite_mi:
                showFavoriteMovies();
                invalidateOptionsMenu();
                break;
        }
        return true;
    }

    private void showPopularMovies(){
        gridview.setVisibility(View.VISIBLE);
        no_favorites_tv.setVisibility(View.INVISIBLE);

        if(isOnline()) {
            setTitle(R.string.title_pop_movies);
            currentFilter=POPULAR;

            Call<MoviesList> call= api.getPopular();

            call.enqueue(new Callback<MoviesList>() {
                @Override
                public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                    if(moviesList!=null)
                        moviesList.clear();
                    moviesList=response.body();
                    movieAdapter.updateData(moviesList);
                    movieAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<MoviesList> call, Throwable t) {
                    Toast.makeText(Activity_Main.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void showTopRatedMovies(){
        gridview.setVisibility(View.VISIBLE);
        no_favorites_tv.setVisibility(View.INVISIBLE);
        if(isOnline()) {
            setTitle(R.string.title_top_movies);
            currentFilter = TOP_RATED;

            Call<MoviesList> call = api.getTopRated();

            call.enqueue(new Callback<MoviesList>() {
                @Override
                public void onResponse(Call<MoviesList> call, Response<MoviesList> response) {
                    if(moviesList!=null)
                        moviesList.clear();
                    moviesList = response.body();
                    movieAdapter.updateData(moviesList);
                    movieAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<MoviesList> call, Throwable t) {
                    Toast.makeText(Activity_Main.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    private void showFavoriteMovies(){

        setTitle(R.string.title_fav_movies);
        currentFilter=FAVORITE;

        Uri contentUri = Uri.withAppendedPath(ContentProvider_FavoriteMovies.CONTENT_URI, Contract_FavoriteMovies.MovieEntry.TABLE_NAME);
        Cursor cursor=getContentResolver().query(contentUri,null, null,null,null);


        if(cursor.getCount()==0){
            gridview.setVisibility(View.INVISIBLE);
            no_favorites_tv.setVisibility(View.VISIBLE);
        }
        else {
            if (moviesList == null)
                moviesList = new MoviesList();
            else
                moviesList.clear();

            List<Movie> tempMoviesList = new ArrayList<Movie>();
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(Contract_FavoriteMovies.MovieEntry.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(Contract_FavoriteMovies.MovieEntry.COLUMN_TITLE));
                String poster = cursor.getString(cursor.getColumnIndexOrThrow(Contract_FavoriteMovies.MovieEntry.COLUMN_POSTERPATH));
                String overview = cursor.getString(cursor.getColumnIndexOrThrow(Contract_FavoriteMovies.MovieEntry.COLUMN_OVERVIEW));
                String rating = cursor.getString(cursor.getColumnIndexOrThrow(Contract_FavoriteMovies.MovieEntry.COLUMN_USERRATING));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(Contract_FavoriteMovies.MovieEntry.COLUMN_RELEASEYEAR));
                tempMoviesList.add(new Movie(id, title, poster, overview, rating, date));
            }
            cursor.close();

            moviesList.updateList(tempMoviesList);

            movieAdapter.updateData(moviesList);
            movieAdapter.notifyDataSetChanged();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent retData) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    if( currentFilter==FAVORITE){
                        showFavoriteMovies();
                    }
                }
                break;
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Activity_Main.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
