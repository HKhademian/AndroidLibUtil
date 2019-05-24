///** https://proandroiddev.com/custom-attributes-using-bindingadapters-in-kotlin-971ef8fcc259 */
//@file:JvmName("ViewBindingUtils")
//
//package ir.hossainco.utils.binding
//
//import android.view.View
//import android.view.View.*
//
//@BindingAdapter("bind:goneUnless")
//fun View.goneUnless(unless: Boolean?) {
//	visibility = if (unless == true) VISIBLE else GONE
//}
//
//@BindingAdapter("bind:visibleUnless")
//fun View.visibleUnless(unless: Boolean?) {
//	visibility = if (unless == true) GONE else VISIBLE
//}
//
//@set:BindingAdapter("visibleOrGone")
//var View.visibleOrGone
//	get() = visibility == VISIBLE
//	set(value) {
//		visibility = if (value) VISIBLE else GONE
//	}
//
//@set:BindingAdapter("visible")
//var View.visible
//	get() = visibility == VISIBLE
//	set(value) {
//		visibility = if (value) VISIBLE else INVISIBLE
//	}
//
//@set:BindingAdapter("invisible")
//var View.invisible
//	get() = visibility == INVISIBLE
//	set(value) {
//		visibility = if (value) INVISIBLE else VISIBLE
//	}
//
//@set:BindingAdapter("gone")
//var View.gone
//	get() = visibility == GONE
//	set(value) {
//		visibility = if (value) GONE else VISIBLE
//	}
