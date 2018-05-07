package com.example.android.popularmovies2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies2.data.ContentProvider_FavoriteMovies;
import com.example.android.popularmovies2.data.Contract_FavoriteMovies;
import com.example.android.popularmovies2.data.DbHelper_FavoriteMovies;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Details extends AppCompatActivity{

    @BindView(R.id.activity_details_scrollView)
    ScrollView scrollView;

    @BindView(R.id.activity_details_constraint_layout)
    ConstraintLayout constraintLayout;

    @BindView(R.id.originaltitle_tv)
    TextView originalTitle_tv;

    @BindView(R.id.poster_iv)
    ImageView poster_iv;

    @BindView(R.id.releaseyear_tv)
    TextView releaseYear_tv;

    @BindView(R.id.rating_tv)
    TextView rating_tv;

    @BindView(R.id.overview_tv)
    TextView overview_tv;

    @BindView(R.id.markfavorite_btn)
    Button markFavorite_btn;

    @BindView(R.id.removefavorite_btn)
    Button removefavorite_btn;

    @BindView(R.id.trailers_rv)
    RecyclerView trailers_rv;

    @BindView(R.id.no_trailers_tv)
    TextView no_trailers_tv;

    @BindView(R.id.horizontalLineBelowTrailers)
    View line_below_trailers;

    @BindView(R.id.reviews_rv)
    RecyclerView reviews_rv;

    @BindView(R.id.no_reviews_tv)
    TextView no_reviews_tv;

    Retrofit retrofit;
    TheMovieDbAPI api;

    TrailersList trailersList;
    ReviewsList reviewsList;

    private Adapter_Trailer tAdapter;
    private Adapter_Review rAdapter;

    private boolean isChanged=false;

    private static boolean isFirstTime=true;

    public final static String TRAILERS_RV_STATE_KEY = "state_trailers_rv";
    public final static String REVIEWS_RV_STATE_KEY = "state_reviews_rv";

    Parcelable state_trailers_rv;
    Parcelable state_reviews_rv;

    RecyclerView.LayoutManager mLayoutManager_trailers,mLayoutManager_reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setTitle("Movie Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        final Movie movie = (Movie) getIntent().getParcelableExtra("movie");

        Picasso.with(Activity_Details.this).load(TheMovieDbAPI.BASE_URL_FOR_POSTER+movie.getPoster_path()).into(poster_iv);

        originalTitle_tv.setText(movie.getTitle());
        releaseYear_tv.setText(movie.getRelease_date().substring(0,4));
        rating_tv.setText(movie.getUser_rating()+"/10");
        overview_tv.setText(movie.getOverview());

        setFavBtnOnClick(movie);
        setRemoveFavBtnOnClick(movie);

        showFavoriteButton(movie.getId());

        retrofit = new Retrofit.Builder()
                .baseUrl(TheMovieDbAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(TheMovieDbAPI.class);

        getTrailers(movie.getId());
        getReviews(movie.getId());
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("SCROLL_POSITION",
                new int[]{ scrollView.getScrollX(), scrollView.getScrollY()});

        state_trailers_rv = mLayoutManager_trailers.onSaveInstanceState();
        state_reviews_rv = mLayoutManager_reviews.onSaveInstanceState();

        outState.putParcelable(TRAILERS_RV_STATE_KEY, state_trailers_rv);
        outState.putParcelable(REVIEWS_RV_STATE_KEY, state_reviews_rv);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int[] position = savedInstanceState.getIntArray("SCROLL_POSITION");
        if(position != null)
            scrollView.post(new Runnable() {
                public void run() {
                    scrollView.scrollTo(position[0], 1400);
                }
            });

        if(state_trailers_rv != null)
            state_trailers_rv = savedInstanceState.getParcelable(TRAILERS_RV_STATE_KEY);

        if(state_reviews_rv != null)
            state_reviews_rv = savedInstanceState.getParcelable(REVIEWS_RV_STATE_KEY);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (state_trailers_rv != null) {
            mLayoutManager_trailers.onRestoreInstanceState(state_trailers_rv);
        }

        if (state_reviews_rv != null) {
            mLayoutManager_reviews.onRestoreInstanceState(state_reviews_rv);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                if(isChanged){
                    this.setResult(RESULT_OK);
                }
                else{
                    this.setResult(RESULT_CANCELED);
                }
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFavoriteButton(String movieId){

        String selection =  Contract_FavoriteMovies.MovieEntry.COLUMN_ID + " = ?";
        String[] selectionArgs = { movieId };

        Uri contentUri = Uri.withAppendedPath(ContentProvider_FavoriteMovies.CONTENT_URI, Contract_FavoriteMovies.MovieEntry.TABLE_NAME);
        Cursor cursor=getContentResolver().query(contentUri,null, selection,selectionArgs,null);

        if(cursor.getCount()>0) {
            markFavorite_btn.setVisibility(View.INVISIBLE);
            removefavorite_btn.setVisibility(View.VISIBLE);
        }
    }

    private void setFavBtnOnClick(final Movie movie){

        markFavorite_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isChanged=true;
                markFavorite_btn.setVisibility(View.INVISIBLE);

                // Create a new map of values, where column names are the keys
                ContentValues cv = new ContentValues();
                cv.put(Contract_FavoriteMovies.MovieEntry.COLUMN_ID, movie.getId());
                cv.put(Contract_FavoriteMovies.MovieEntry.COLUMN_TITLE, movie.getTitle());
                cv.put(Contract_FavoriteMovies.MovieEntry.COLUMN_POSTERPATH, movie.getPoster_path());
                cv.put(Contract_FavoriteMovies.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
                cv.put(Contract_FavoriteMovies.MovieEntry.COLUMN_USERRATING, movie.getUser_rating());
                cv.put(Contract_FavoriteMovies.MovieEntry.COLUMN_RELEASEYEAR, movie.getRelease_date());

                Uri contentUri = Uri.withAppendedPath(ContentProvider_FavoriteMovies.CONTENT_URI, Contract_FavoriteMovies.MovieEntry.TABLE_NAME);
                Uri resultUri = getContentResolver().insert(contentUri, cv);

                Toast.makeText(Activity_Details.this, "MARKED as FAVORITE", Toast.LENGTH_SHORT).show();

                removefavorite_btn.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setRemoveFavBtnOnClick(final Movie movie){

        removefavorite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isChanged=true;
                removefavorite_btn.setVisibility(View.INVISIBLE);

                String selection =  Contract_FavoriteMovies.MovieEntry.COLUMN_ID + " = ?";
                String[] selectionArgs = { movie.getId() };
                Uri contentUri = Uri.withAppendedPath(ContentProvider_FavoriteMovies.CONTENT_URI, Contract_FavoriteMovies.MovieEntry.TABLE_NAME);
                int deletedRows = getContentResolver().delete(contentUri,selection,selectionArgs);

                Toast.makeText(Activity_Details.this, "REMOVED from FAVORITES", Toast.LENGTH_SHORT).show();
                markFavorite_btn.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getTrailers(String movieId){
        Call<TrailersList> callForTrailers = api.getTrailers(movieId);
        callForTrailers.enqueue(new Callback<TrailersList>() {
            @Override
            public void onResponse(Call<TrailersList> call, Response<TrailersList> response) {
                trailersList = response.body();

                if(trailersList.getNoOfTrailers()==0){
                    trailers_rv.setVisibility(View.INVISIBLE);
                    no_trailers_tv.setVisibility(View.VISIBLE);

                    ConstraintSet constraintSet;

                    constraintLayout = (ConstraintLayout) findViewById(R.id.activity_details_constraint_layout);

                    constraintSet = new ConstraintSet();
                    constraintSet.clone(constraintLayout);

                    constraintSet.connect(line_below_trailers.getId(), ConstraintSet.TOP, no_trailers_tv.getId(), ConstraintSet.BOTTOM, 0);
                    constraintSet.applyTo(constraintLayout);

                }
                else {

                    tAdapter = new Adapter_Trailer(trailersList);
                    mLayoutManager_trailers = new LinearLayoutManager(getApplicationContext());
                    trailers_rv.setLayoutManager(mLayoutManager_trailers);
                    trailers_rv.setAdapter(tAdapter);
                }
            }

            @Override
            public void onFailure(Call<TrailersList> call, Throwable t) {
                Toast.makeText(Activity_Details.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getReviews(String movieId){
        Call<ReviewsList> callForReviews = api.getReviews(movieId);
        callForReviews.enqueue(new Callback<ReviewsList>() {
            @Override
            public void onResponse(Call<ReviewsList> call, Response<ReviewsList> response) {
                reviewsList = response.body();

                if(reviewsList.getNoOfReviews()==0){
                    reviews_rv.setVisibility(View.INVISIBLE);
                    no_reviews_tv.setVisibility(View.VISIBLE);
                }
                else{
                    rAdapter = new Adapter_Review(reviewsList);
                    mLayoutManager_reviews = new LinearLayoutManager(getApplicationContext());
                    reviews_rv.setLayoutManager(mLayoutManager_reviews);
                    reviews_rv.setAdapter(rAdapter);
                }
            }

            @Override
            public void onFailure(Call<ReviewsList> call, Throwable t) {
                Toast.makeText(Activity_Details.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
