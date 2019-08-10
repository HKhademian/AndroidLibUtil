package ir.hossainco.utils.view

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewManager
import androidx.annotation.IdRes
import ir.hco.util.DrawableSource
import org.jetbrains.anko.dip
import org.jetbrains.anko.horizontalMargin
import org.jetbrains.anko.verticalMargin
import org.jetbrains.anko.view

fun ViewManager.splitter(
	@IdRes id: Int? = null,
	vertical: Boolean = true,
	background: DrawableSource = DrawableSource.ofColor(0x33666666),
	size: Float = 1f,
	margin: Float = 4f,

	init: View.() -> Unit = {}
) = view {
	if (id != null) this.id = id

	background.setBackground(this)

	layoutParams = if (vertical)
		ViewGroup.MarginLayoutParams(MATCH_PARENT, dip(size)).apply {
			this.verticalMargin = dip(margin)
		}
	else
		ViewGroup.MarginLayoutParams(dip(size), MATCH_PARENT).apply {
			this.horizontalMargin = dip(margin)
		}

	init()
}
