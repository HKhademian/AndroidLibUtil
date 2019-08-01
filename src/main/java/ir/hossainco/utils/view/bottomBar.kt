package ir.hossainco.utils.view

import android.annotation.SuppressLint
import android.view.Gravity.CENTER
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewManager
import android.widget.LinearLayout
import androidx.annotation.IdRes
import ir.hco.util.R
import ir.hossainco.utils.ui.drawables.createRect3D
import org.jetbrains.anko._LinearLayout
import org.jetbrains.anko.linearLayout

@SuppressLint("RtlHardcoded")
fun ViewManager.simpleBottomBar(
	@IdRes id: Int? = R.id.toolbar,
	init: _LinearLayout.() -> Unit = {}
) = linearLayout {
	if (id != null) {
		this.id = id
	}
	gravity = CENTER
	layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
	background = simpleBottomBarBackground()

	init()
}

fun simpleBottomBarBackground(frontColor: Int = 0xFF113311.toInt(), backColor: Int = 0xFF119911.toInt()) =
	createRect3D(
		frontColor, backColor, 0,
		0f, 0f, 0f, -2f
	)
