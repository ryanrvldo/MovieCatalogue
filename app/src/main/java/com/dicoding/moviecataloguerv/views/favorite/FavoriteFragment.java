package com.dicoding.moviecataloguerv.views.favorite;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.adapter.ViewPagerAdapter;
import com.dicoding.moviecataloguerv.databinding.FragmentFavoriteBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private FragmentFavoriteBinding binding;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment_movie
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        createViewPager(binding.viewpager);
        binding.tabs.setupWithViewPager(binding.viewpager, false);
    }

    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FavoriteMovieFragment(), getResources().getString(R.string.movies_tab));
        adapter.addFragment(new FavoriteTVFragment(), getResources().getString(R.string.tv_shows_tab));
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
