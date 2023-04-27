package me.hlatky.wbpo.ui

import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

/**
 * On [recyclerView] scrolling changes [target] visibility.
 * On [target] click, [RecyclerView.smoothScrollToPosition] ic called.
 */
class RecyclerViewScrollButtonHelper(
    private val recyclerView: RecyclerView,
    private val target: View,
) {
    init {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            fun verticalOffset() =
                recyclerView.computeVerticalScrollOffset()

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val yTreshold = recyclerView.height * 0.8

                if (dy > 0 && target.isVisible.not() && verticalOffset() >= yTreshold) {
                    target.isVisible = true
                }

                if (dy < 0 && target.isVisible && verticalOffset() < yTreshold) {
                    target.isVisible = false
                }
            }
        })

        target.setOnClickListener {
            it.isGone = true
            recyclerView.smoothScrollToPosition(0)
        }
    }
}
