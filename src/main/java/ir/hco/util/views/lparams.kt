package ir.hco.util.views

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout

inline fun <T : View> T.simpleLParams(
	width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
	height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
	init: ViewGroup.LayoutParams.() -> Unit = {}
): T {
	val layoutParams = ViewGroup.LayoutParams(width, height)
	layoutParams.init()
	this@simpleLParams.layoutParams = layoutParams
	return this
}

inline fun <T : View> T.marginLParams(
	width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
	height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
	init: ViewGroup.MarginLayoutParams.() -> Unit = {}
): T {
	val layoutParams = ViewGroup.MarginLayoutParams(width, height)
	layoutParams.init()
	this@marginLParams.layoutParams = layoutParams
	return this
}

inline fun <T : View> T.frameLParams(
	width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
	height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
	init: FrameLayout.LayoutParams.() -> Unit = {}
): T {
	val layoutParams = FrameLayout.LayoutParams(width, height)
	layoutParams.init()
	this@frameLParams.layoutParams = layoutParams
	return this
}

inline fun <T : View> T.linearLParams(
	width: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
	height: Int = android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
	init: LinearLayout.LayoutParams.() -> Unit = {}
): T {
	val layoutParams = LinearLayout.LayoutParams(width, height)
	layoutParams.init()
	this@linearLParams.layoutParams = layoutParams
	return this
}
