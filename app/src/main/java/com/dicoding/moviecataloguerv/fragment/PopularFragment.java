package com.dicoding.moviecataloguerv.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dicoding.moviecataloguerv.MainActivity;
import com.dicoding.moviecataloguerv.R;
import com.dicoding.moviecataloguerv.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class PopularFragment extends Fragment {

    public PopularFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ViewPager viewPager = view.findViewById(R.id.viewpager);
        TabLayout tabLayout = view.findViewById(R.id.tabs);

        createViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager, false);
    }

    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PopularMovieFragment(), getResources().getString(R.string.movies_tab));
        adapter.addFragment(new PopularTvFragment(), getResources().getString(R.string.tv_shows_tab));
        viewPager.setAdapter(adapter);
    }
}
