package com.ryanrvldo.moviecatalogue.ui.newRelease

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ryanrvldo.moviecatalogue.adapter.TvShowsVerticalAdapter
import com.ryanrvldo.moviecatalogue.databinding.FragmentTabBinding
import com.ryanrvldo.moviecatalogue.ui.viewmodel.TvShowsViewModel
import com.ryanrvldo.moviecatalogue.utils.LayoutManagerUtil.getVerticalLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewReleaseTvShowFragment : Fragment() {

    private var _binding: FragmentTabBinding? = null
    private val binding: FragmentTabBinding
        get() = _binding!!

    private val viewModel: TvShowsViewModel by viewModels()

    @Inject
    lateinit var moviesAdapter: TvShowsVerticalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)

        binding.rvMovies.layoutManager = getVerticalLayoutManager(requireContext())
        binding.rvMovies.adapter = moviesAdapter
        observeData()
    }

    private fun observeData() {
        viewModel.newReleaseTvShows.observe(viewLifecycleOwner) { response ->
            response?.let {
                moviesAdapter.differ.submitList(response.tvShowItems)
                showLoading(false)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}