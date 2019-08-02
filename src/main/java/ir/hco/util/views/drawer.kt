package ir.hco.util.views

import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewManager
import androidx.drawerlayout.widget.DrawerLayout
import ir.hossainco.utils.view.factory

fun ViewManager.drawer(
	theme: Int = 0,
	init: DrawerLayout.() -> Unit = {}
) =
	factory(::DrawerLayout, theme = theme, init = init)

inline fun <T : View> T.drawerLParams(
	width: Int = WRAP_CONTENT,
	height: Int = WRAP_CONTENT,
	init: DrawerLayout.LayoutParams.() -> Unit = {}
): T {
	val layoutParams = DrawerLayout.LayoutParams(width, height)
	layoutParams.init()
	this@drawerLParams.layoutParams = layoutParams
	return this
}
