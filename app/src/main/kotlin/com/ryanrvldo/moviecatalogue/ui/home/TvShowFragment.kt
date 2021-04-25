package com.ryanrvldo.moviecatalogue.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ryanrvldo.moviecatalogue.adapter.TvShowsHorizontalAdapter
import com.ryanrvldo.moviecatalogue.databinding.FragmentTvShowBinding
import com.ryanrvldo.moviecatalogue.ui.viewmodel.TvShowsViewModel
import com.ryanrvldo.moviecatalogue.utils.BaseFragment
import com.ryanrvldo.moviecatalogue.utils.LayoutManagerUtil.getHorizontalLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TvShowFragment : BaseFragment() {

    private var _binding: FragmentTvShowBinding? = null
    private val binding: FragmentTvShowBinding
        get() = _binding!!

    private val tvShowsViewModel: TvShowsViewModel by activityViewModels()

    @Inject
    lateinit var popularAdapter: TvShowsHorizontalAdapter

    @Inject
    lateinit var topRatedAdapter: TvShowsHorizontalAdapter

    @Inject
    lateinit var onTheAirAdapter: TvShowsHorizontalAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTvShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        setupRecyclerView()
        observeData()
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvPopular.layoutManager = getHorizontalLayoutManager(requireContext())
            rvPopular.adapter = popularAdapter

            rvTopRated.layoutManager = getHorizontalLayoutManager(requireContext())
            rvTopRated.adapter = topRatedAdapter

            rvNowPlaying.layoutManager = getHorizontalLayoutManager(requireContext())
            rvNowPlaying.adapter = onTheAirAdapter
        }
    }

    private fun observeData() {
        tvShowsViewModel.popularTv.observe(viewLifecycleOwner) { response ->
            handleListResponseResult(response, binding.popularShimmerContainer) {
                popularAdapter.differ.submitList(it.tvShowItems)
            }
        }

        tvShowsViewModel.topRatedTv.observe(viewLifecycleOwner) { response ->
            handleListResponseResult(response, binding.topRatedShimmerContainer) {
                topRatedAdapter.differ.submitList(it.tvShowItems)
            }
        }

        tvShowsViewModel.nowPlayingTv.observe(viewLifecycleOwner) { response ->
            handleListResponseResult(response, binding.nowPlayingShimmerContainer) {
                onTheAirAdapter.differ.submitList(it.tvShowItems)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
