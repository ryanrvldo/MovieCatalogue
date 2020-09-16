package com.ryanrvldo.moviecatalogue.ui.newRelease

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.ryanrvldo.moviecatalogue.R
import com.ryanrvldo.moviecatalogue.adapter.ViewPagerAdapter
import com.ryanrvldo.moviecatalogue.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewReleaseFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    @Inject
    lateinit var adapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        adapter.addFragment(NewReleaseMovieFragment())
        adapter.addFragment(NewReleaseTvShowFragment())
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager, true) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.movies_tab)
                1 -> tab.text = getString(R.string.tv_shows_tab)
            }
        }.attach()
    }
}