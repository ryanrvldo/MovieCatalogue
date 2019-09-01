package com.dicoding.moviecataloguerv.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.activity.MovieDetailActivity;
import com.dicoding.moviecataloguerv.adapter.MovieAdapter;
import com.dicoding.moviecataloguerv.model.Movie;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements MovieAdapter.OnItemClicked {

    private String[] movieTitle, movieDesc, movieRelease, moviePoster;
    private ArrayList<Movie> movies;
    private MovieAdapter adapter;



    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvMovie = view.findViewById(R.id.recycler_view);
        rvMovie.setHasFixedSize(true);


        rvMovie.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new MovieAdapter(movies, getActivity());
        rvMovie.setAdapter(adapter);

        prepare();
        addData();

        adapter.setOnCLick(this);


    }

    @Override
    public void onItemClick(int position) {
        Intent detailMovie = new Intent(getActivity(), MovieDetailActivity.class);
        detailMovie.putExtra(MovieDetailActivity.EXTRA_MOVIE, movies.get(position));
        startActivity(detailMovie);
    }


    private void addData() {
        movies = new ArrayList<>();

        for (int i = 0; i< movieTitle.length; i++) {
            Movie movie = new Movie();
            movie.setTitle(movieTitle[i]);
            movie.setPoster(moviePoster[i]);
            movie.setDesc(movieDesc[i]);
            movie.setRelease(movieRelease[i]);
            movies.add(movie);
        }
        adapter.setMovies(movies);
    }

    private void prepare(){
        movieTitle = getResources().getStringArray(R.array.movie_title);
        movieDesc = getResources().getStringArray(R.array.movie_desc);
        movieRelease = getResources().getStringArray(R.array.movie_release);
        moviePoster = getResources().getStringArray(R.array.movie_poster);

    }
}
