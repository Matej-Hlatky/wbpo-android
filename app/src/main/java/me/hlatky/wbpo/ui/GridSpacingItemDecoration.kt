package me.hlatky.wbpo.ui

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/** [RecyclerView.ItemDecoration] that adds spaces between cells. */
class GridSpacingItemDecoration(
    @Px private val spacing: Int,
    private val includeEdge: Boolean = true
) : RecyclerView.ItemDecoration() {

    private var spanCount: Int = 0

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (spanCount == 0) {
            spanCount = when (val lm = parent.layoutManager) {
                is GridLayoutManager -> lm.spanCount
                else /* LinearLayoutManager */ -> 1
            }
        }

        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) {
                outRect.top = spacing
            }

            outRect.bottom = spacing
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount

            if (position >= spanCount) {
                outRect.top = spacing
            }
        }
    }
}
