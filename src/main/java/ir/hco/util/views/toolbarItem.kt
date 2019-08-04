//package ir.hco.util.views
//
//import android.view.View
//import android.view.ViewGroup.LayoutParams.MATCH_PARENT
//import android.view.ViewManager
//import android.widget.LinearLayout
//import androidx.annotation.IdRes
//import ir.hco.util.DrawableSource
//import ir.hco.util.StringSource
//import ir.hossainco.utils.ui.drawables.createRectGradientButton
//import org.jetbrains.anko.dip
//import org.jetbrains.anko.imageView
//import org.jetbrains.anko.padding
//import org.jetbrains.anko.toast
//
//fun ViewManager.simpleToolbarItem(
//	@IdRes id: Int? = null,
//
//	icon: DrawableSource = DrawableSource.of(null),
//	hint: StringSource = StringSource.of(null),
//
//	onClick: (view: View) -> Unit = {}
//) = imageView {
//	val context = context!!
//
//	if (id != null) {
//		setId(id)
//	}
//
//	// image = ResourcesCompat.getDrawable(resources, imageRes, null)
//	icon.setImage(this)
//
//	background = createRectGradientButton(0, 0, 0x33aaaaaa, 0, 0, 0)
//	padding = dip(8)
//	layoutParams = LinearLayout.LayoutParams(dip(48), MATCH_PARENT)
//
//	val hintText = hint.getString(context)
//	if (!hintText.isNullOrEmpty())
//		setOnLongClickListener {
//			context.toast(hintText)
//			true
//		}
//
//	setOnClickListener { onClick(it) }
//}
