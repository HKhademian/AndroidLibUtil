package ir.hossainco.utils.views

import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewManager
import android.widget.LinearLayout
import androidx.annotation.IdRes
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.dip
import org.jetbrains.anko.view

fun ViewManager.spliter(
	@IdRes id: Int? = null,

	vertical: Boolean = true,

	init: View.() -> Unit = {}
) = view {
	if (id != null) {
		this.id = id
	}

	backgroundColor = 0x33666666.toInt()

	layoutParams = if (vertical)
		LinearLayout.LayoutParams(dip(1), MATCH_PARENT)
	else
		LinearLayout.LayoutParams(MATCH_PARENT, dip(1))

	init()
}
