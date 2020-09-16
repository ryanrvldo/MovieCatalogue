package com.ryanrvldo.moviecatalogue.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ryanrvldo.moviecatalogue.adapter.MoviesHorizontalAdapter
import com.ryanrvldo.moviecatalogue.databinding.FragmentMoviesBinding
import com.ryanrvldo.moviecatalogue.ui.viewmodel.MoviesViewModel
import com.ryanrvldo.moviecatalogue.utils.BaseFragment
import com.ryanrvldo.moviecatalogue.utils.LayoutManagerUtil.getHorizontalLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : BaseFragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding: FragmentMoviesBinding
        get() = _binding!!

    private val moviesViewModel: MoviesViewModel by activityViewModels()

    @Inject
    lateinit var popularAdapter: MoviesHorizontalAdapter

    @Inject
    lateinit var topRatedAdapter: MoviesHorizontalAdapter

    @Inject
    lateinit var nowPlayingAdapter: MoviesHorizontalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerViews()
        observesData()
    }

    private fun setupRecyclerViews() {
        binding.apply {
            rvPopular.layoutManager = getHorizontalLayoutManager(requireContext())
            rvPopular.adapter = popularAdapter

            rvTopRated.layoutManager = getHorizontalLayoutManager(requireContext())
            rvTopRated.adapter = topRatedAdapter

            rvNowPlaying.layoutManager = getHorizontalLayoutManager(requireContext())
            rvNowPlaying.adapter = nowPlayingAdapter
        }
    }

    private fun observesData() {
        moviesViewModel.popularMovies.observe(viewLifecycleOwner) { response ->
            response.data?.movieItems?.let { list ->
                handleResponseResult(
                    response,
                    list,
                    popularAdapter.differ,
                    binding.popularShimmerContainer
                )
            }
        }

        moviesViewModel.topRatedMovies.observe(viewLifecycleOwner) { response ->
            response.data?.movieItems?.let { list ->
                handleResponseResult(
                    response,
                    list,
                    topRatedAdapter.differ,
                    binding.topRatedShimmerContainer
                )
            }
        }

        moviesViewModel.nowPlayingMovies.observe(viewLifecycleOwner) { response ->
            response.data?.movieItems?.let { list ->
                handleResponseResult(
                    response,
                    list,
                    nowPlayingAdapter.differ,
                    binding.nowPlayingShimmerContainer
                )
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}