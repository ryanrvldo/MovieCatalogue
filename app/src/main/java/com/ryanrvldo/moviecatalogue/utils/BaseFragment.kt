package com.ryanrvldo.moviecatalogue.utils

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AsyncListDiffer
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import com.ryanrvldo.moviecatalogue.R
import com.ryanrvldo.moviecatalogue.data.vo.Resource
import com.ryanrvldo.moviecatalogue.data.vo.Status

open class BaseFragment : Fragment() {

    fun showActionSnackbar(msg: Int, actionName: Int, actionListener: View.OnClickListener) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT)
            .setAction(actionName, actionListener)
            .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            .show()
    }

    fun showMessageSnackbar(msg: String) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun hideShimmer(container: ShimmerFrameLayout) {
        container.hideShimmer()
        container.visibility = View.GONE
    }

    fun <R, D> handleResponseResult(
        resource: Resource<R>,
        list: List<D>,
        differ: AsyncListDiffer<D>,
        shimmerContainer: ShimmerFrameLayout
    ) {
        resource.data?.let { }
        when (resource.status) {
            Status.SUCCESS -> {
                resource.data?.let {
                    differ.submitList(list)
                    hideShimmer(shimmerContainer)
                }
            }
            Status.ERROR -> {
                showMessageSnackbar(resource.message!!)
                hideShimmer(shimmerContainer)
            }
            Status.LOADING -> shimmerContainer.startShimmer()
        }
    }
}
