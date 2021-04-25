package com.ryanrvldo.moviecatalogue.utils

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.snackbar.Snackbar
import com.ryanrvldo.moviecatalogue.R
import com.ryanrvldo.moviecatalogue.data.vo.Resource
import com.ryanrvldo.moviecatalogue.data.vo.Status

open class BaseFragment : Fragment() {

    fun <R> handleListResponseResult(
        resource: Resource<R>,
        shimmerContainer: ShimmerFrameLayout,
        onSuccess: (data: R) -> Unit,
    ) {
        when (resource.status) {
            Status.SUCCESS -> {
                resource.data?.let {
                    onSuccess(it)
                    hideShimmer(shimmerContainer)
                }
            }
            Status.LOADING -> shimmerContainer.startShimmer()
            Status.ERROR -> {
                showMessageSnackbar(resource.message!!)
                hideShimmer(shimmerContainer)
            }
        }
    }

    fun showActionSnackbar(msg: Int, actionName: Int, actionListener: View.OnClickListener) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG)
            .setAction(actionName, actionListener)
            .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
            .show()
    }

    fun showMessageSnackbar(msg: String) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun hideShimmer(container: ShimmerFrameLayout) {
        container.hideShimmer()
        container.visibility = View.GONE
    }

}
