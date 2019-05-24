package ir.hossainco.utils.view

import android.view.Gravity.START
import android.view.Gravity.TOP
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewManager
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.annotation.IdRes
import org.jetbrains.anko._LinearLayout
import org.jetbrains.anko.scrollView
import org.jetbrains.anko.verticalLayout

fun ViewManager.verticalScrollView(
	@IdRes scrollViewId: Int? = null,
	@IdRes id: Int? = null,
	gravity: Int = TOP or START,
	init: _LinearLayout.() -> Unit = {}
) =
	scrollView {
		if (scrollViewId != null) {
			this.id = scrollViewId
		}
		isFillViewport = true
		isHorizontalScrollBarEnabled = false
		isVerticalScrollBarEnabled = false
		overScrollMode = ScrollView.OVER_SCROLL_NEVER
		layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)

		verticalLayout {
			if (id != null) {
				this.id = id
			}
			this.gravity = gravity

			init()
		}.lparams(
			width = MATCH_PARENT,
			height = WRAP_CONTENT
		)
	}
