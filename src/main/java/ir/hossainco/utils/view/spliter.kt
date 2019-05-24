package ir.hossainco.utils.view

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.dip
import org.jetbrains.anko.view

private const val DEF_COLOR = 0x33666666.toInt()

fun ViewManager.spliter(
	@IdRes id: Int? = null,

	vertical: Boolean = true,

	@ColorInt color: Int? = null,
	@ColorRes colorRes: Int? = null,

	size: Float = 1f,

	init: View.() -> Unit = {}
) = view {
	if (id != null) {
		this.id = id
	}
	backgroundColor = when {
		color != null -> color
		colorRes != null -> context?.resources?.getColor(colorRes) ?: DEF_COLOR
		else -> DEF_COLOR
	}

	layoutParams = if (vertical)
		ViewGroup.LayoutParams(MATCH_PARENT, dip(size))
	else
		ViewGroup.LayoutParams(dip(size), MATCH_PARENT)

	init()
}
