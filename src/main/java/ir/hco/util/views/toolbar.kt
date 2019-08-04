//package ir.hco.util.views
//
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.view.Gravity.*
//import android.view.View
//import android.view.ViewGroup.LayoutParams.MATCH_PARENT
//import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
//import android.view.ViewManager
//import android.widget.LinearLayout
//import androidx.annotation.IdRes
//import androidx.core.view.isVisible
//import ir.hco.util.BaseApp
//import ir.hco.util.ColorSource
//import ir.hco.util.R
//import ir.hossainco.utils.ui.TextSize
//import ir.hossainco.utils.ui.drawables.createRect3D
//import ir.hossainco.utils.view.appTextView
//import org.jetbrains.anko.*
//
//@SuppressLint("RtlHardcoded")
//fun ViewManager.simpleToolbar(
//	@IdRes id: Int? = R.id.toolbar,
//
//	backColor: ColorSource = ColorSource.ofInt(0xFF119911.toInt()),
//	frontColor: ColorSource = ColorSource.ofInt(0xFF113311.toInt()),
//	textColor: ColorSource = ColorSource.of(R.color.primaryTextColor),
//
//	init: _LinearLayout.() -> Unit = {}
//) =
//	linearLayout {
//
//		if (id != null) {
//			this.id = id
//		}
//		gravity = CENTER
//		layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
//
//		background = simpleToolbarBackground(
//			frontColor.getColor(context),
//			backColor.getColor(context)
//		)
//
//		imageView(context.applicationInfo.icon) {
//			padding = dip(4)
//		}.lparams(width = dip(56), height = dip(56)) {
//			gravity = CENTER
//		}
//
//		verticalLayout {
//			gravity = CENTER_VERTICAL or FILL_HORIZONTAL
//
//			appTextView(text = BaseApp.appLabel, dark = false, textSize = TextSize.LargeTextSize) {
//				padding = dip(2)
//				gravity = CENTER_VERTICAL or RIGHT
//				this.textColor = textColor.getColor(context)
//			}
//			appTextView(text = context.configuration.locale.displayName, dark = false, textSize = TextSize.MediumTextSize) {
//				padding = dip(2)
//				gravity = CENTER_VERTICAL or RIGHT
//				this.textColor = textColor.getColor(context)
//			}
//		}.lparams(width = 0, height = MATCH_PARENT, weight = 1f)
//
//		init()
//	}
//
//fun simpleToolbarBackground(frontColor: Int, backColor: Int) =
//	createRect3D(
//		frontColor, backColor, 0,
//		0f, 0f, 0f, 2f
//	)
//
//fun setSimpleMenuItemEnabled(view: View?, enabled: Boolean) = view?.apply {
//	isEnabled = enabled
//	isVisible = enabled
//}
//
//fun setSimpleMenuItemEnabled(activity: Activity?, id: Int, enabled: Boolean) =
//	setSimpleMenuItemEnabled(activity?.findViewById(id), enabled)
