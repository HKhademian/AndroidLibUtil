package ir.hossainco.utils.views

import android.annotation.SuppressLint
import android.view.Gravity.CENTER
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewManager
import android.widget.LinearLayout
import androidx.annotation.IdRes
import ir.hossainco.utils.Drawables
import ir.hossainco.utils.R
import org.jetbrains.anko._LinearLayout
import org.jetbrains.anko.linearLayout

@SuppressLint("RtlHardcoded")
fun ViewManager.bottomBar(
	@IdRes id: Int? = R.id.toolbar,
	init: _LinearLayout.() -> Unit = {}
) = linearLayout {
	if (id != null) {
		this.id = id
	}
	gravity = CENTER
	layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
	background = bottomBarBackground()

	init()
}

fun bottomBarBackground(frontColor: Int = 0xFF113311.toInt(), backColor: Int = 0xFF119911.toInt()) =
	Drawables.createRect3D(
		frontColor, backColor, 0,
		0f, 0f, 0f, -2f
	)
