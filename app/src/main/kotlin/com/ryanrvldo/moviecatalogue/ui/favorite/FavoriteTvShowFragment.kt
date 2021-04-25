package com.ryanrvldo.moviecatalogue.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ryanrvldo.moviecatalogue.adapter.TvShowsVerticalAdapter
import com.ryanrvldo.moviecatalogue.databinding.FragmentTabBinding
import com.ryanrvldo.moviecatalogue.ui.viewmodel.FavoritesViewModel
import com.ryanrvldo.moviecatalogue.utils.BaseFragment
import com.ryanrvldo.moviecatalogue.utils.LayoutManagerUtil.getVerticalLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteTvShowFragment : BaseFragment() {

    private var _binding: FragmentTabBinding? = null
    private val binding: FragmentTabBinding
        get() = _binding!!

    private val favoritesViewModel: FavoritesViewModel by viewModels()

    @Inject
    lateinit var adapter: TvShowsVerticalAdapter

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
        binding.rvMovies.adapter = adapter

        observeData()
        attachTouchHelper()
    }

    private fun observeData() {
        favoritesViewModel.favoriteTvShows.observe(viewLifecycleOwner) { tvShowList ->
            tvShowList?.let {
                adapter.differ.submitList(tvShowList)
                if (tvShowList.isEmpty()) {
                    binding.tvNull.visibility = View.VISIBLE
                } else {
                    binding.tvNull.visibility = View.GONE
                }
                showLoading(false)
            }
        }
    }

    private fun attachTouchHelper() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val selectedTvShow = adapter.differ.currentList[viewHolder.adapterPosition]
                favoritesViewModel.deleteFavTv(selectedTvShow)
                Toast.makeText(
                    requireContext(),
                    selectedTvShow.title + " deleted from tv show favorite list.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }).attachToRecyclerView(binding.rvMovies)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}