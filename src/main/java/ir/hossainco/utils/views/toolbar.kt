package ir.hossainco.utils.views

import android.annotation.SuppressLint
import android.app.Activity
import android.view.Gravity.*
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewManager
import android.widget.LinearLayout
import androidx.annotation.IdRes
import ir.hossainco.utils.Drawables
import ir.hossainco.utils.R
import ir.hossainco.utils.TextSize
import org.jetbrains.anko.*

@SuppressLint("RtlHardcoded")
fun ViewManager.toolbar(
	@IdRes id: Int? = R.id.toolbar,

	init: _LinearLayout.() -> Unit = {}
) =
	linearLayout {
		if (id != null) {
			this.id = id
		}
		gravity = CENTER
		layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
		background = toolbarBackground()

		init()

		appTextView(text = context.applicationInfo.name, dark = false, textSize = TextSize.ExtraLargeTextSize) {
			padding = dip(2)
			gravity = CENTER_VERTICAL or RIGHT
			// typeface = Typeface.SERIF
			// setShadowLayer(3f, 0f, 0f, Colors.alphaColor(Colors.contrastColor(textClr), 100))
		}.lparams(width = 0, height = MATCH_PARENT, weight = 1f)

		imageView(context.applicationInfo.icon) {
			padding = dip(4)
		}.lparams(width = dip(56), height = dip(56))
	}

//fun ViewManager.toolbar(init: LinearLayout.() -> Unit = {}) =
//	linearLayout {
//		id = R.id.toolbar
//		gravity = CENTER
//		layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
//
//		imageView(R.mipmap.ic_launcher_round) {
//			padding = dip(4)
//		}.lparams(width = dip(56), height = dip(56))
//
//		appTextView(R.string.app_name, dark = false, textSize = TextSize.HeaderTextSize) {
//			padding = dip(2)
//			gravity = CENTER_VERTICAL or LEFT
//			typeface = Typeface.SERIF
//			//setShadowLayer(3f, 0f, 0f, Colors.alphaColor(Colors.contrastColor(textClr), 100))
//		}.lparams(width = 0, height = MATCH_PARENT, weight = 1f)
//
//		toolbarItem(R.drawable.ic_share) {
//			id = R.id.menu_mark
//			setMenuItemEnabled(this, false)
//		}
//
//		// spliter()
//		toolbarItem(R.drawable.ic_share) {
//			id = R.id.menu_share
//			setMenuItemEnabled(this, false)
//		}
//
//		// spliter()
//		toolbarItem(R.drawable.ic_share) {
//			setOnClickListener { }
//		}
//
//		// spliter()
//		toolbarItem(R.drawable.ic_share) {
//			setOnClickListener { }
//		}
//
//		init()
//	}

fun toolbarBackground(frontColor: Int = 0xFF113311.toInt(), backColor: Int = 0xFF119911.toInt()) =
	Drawables.createRect3D(
		frontColor, backColor, 0,
		0f, 0f, 0f, 2f
	)

//fun toolbarWineBackground() =
//	toolbarBackground(0xFF991111.toInt(), 0xFF661111.toInt())
//
//fun toolbarOliveBackground() =
//	toolbarBackground(0xFF113311.toInt(), 0xFF119911.toInt())
//
//fun toolbarSoilBackground() =
//	toolbarBackground(0xFF991111.toInt(), 0xFF661111.toInt())

fun setMenuItemEnabled(view: View?, enabled: Boolean) = view?.apply {
	isEnabled = enabled
	// visibility = if (enabled) View.VISIBLE else View.INVISIBLE
	visibility = if (enabled) View.VISIBLE else View.GONE
}

fun setMenuItemEnabled(activity: Activity?, id: Int, enabled: Boolean) =
	setMenuItemEnabled(activity?.findViewById(id), enabled)
