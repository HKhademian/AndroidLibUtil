package ir.hco.util.views

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewManager
import androidx.annotation.StyleRes
import ir.hossainco.utils.view.factory
import org.jetbrains.anko._FrameLayout

fun ViewManager.squareLayout(
	mode: SquareMode = SquareMode.Auto,
	@StyleRes theme: Int = 0,
	init: SquareLayout.() -> Unit = {}
) = factory({ context -> SquareLayout(context, mode) }, theme, init)

@SuppressLint("ViewConstructor")
class SquareLayout(
	context: Context,
	mode: SquareMode = SquareMode.Auto
) : _FrameLayout(context) {
	var mode = mode
		set(value) {
			field = value
			requestLayout()
		}

	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		val sizeSpec = mode.measure(this, widthMeasureSpec, heightMeasureSpec)
		val sizeMode = MeasureSpec.getMode(sizeSpec)
		val size = MeasureSpec.getSize(sizeSpec)

		super.onMeasure(sizeSpec, sizeSpec)
		if (sizeMode == MeasureSpec.EXACTLY)
			setMeasuredDimension(size, size)
	}
}

sealed class SquareMode {
	abstract fun measure(view: SquareLayout, widthMeasureSpec: Int, heightMeasureSpec: Int): Int

	object Max : SquareMode() {
		override fun measure(view: SquareLayout, widthMeasureSpec: Int, heightMeasureSpec: Int) =
			if (View.MeasureSpec.getSize(widthMeasureSpec) > View.MeasureSpec.getSize(heightMeasureSpec))
				widthMeasureSpec
			else
				heightMeasureSpec
	}

	object Min : SquareMode() {
		override fun measure(view: SquareLayout, widthMeasureSpec: Int, heightMeasureSpec: Int) =
			if (View.MeasureSpec.getSize(widthMeasureSpec) < View.MeasureSpec.getSize(heightMeasureSpec))
				widthMeasureSpec
			else
				heightMeasureSpec
	}

	object Width : SquareMode() {
		override fun measure(view: SquareLayout, widthMeasureSpec: Int, heightMeasureSpec: Int) =
			widthMeasureSpec
	}

	object Height : SquareMode() {
		override fun measure(view: SquareLayout, widthMeasureSpec: Int, heightMeasureSpec: Int) =
			heightMeasureSpec
	}

	object Auto : SquareMode() {
		override fun measure(view: SquareLayout, widthMeasureSpec: Int, heightMeasureSpec: Int): Int {
			val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
			val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
			val width = View.MeasureSpec.getSize(widthMeasureSpec)
			val height = View.MeasureSpec.getSize(heightMeasureSpec)

			return if (widthMode == View.MeasureSpec.EXACTLY && heightMode != View.MeasureSpec.EXACTLY) {
				widthMeasureSpec
			} else if (heightMode == View.MeasureSpec.EXACTLY && widthMode != View.MeasureSpec.EXACTLY) {
				heightMeasureSpec
			} else {
				Min.measure(view, widthMeasureSpec, heightMeasureSpec)
			}
		}
	}

	abstract class Custom : SquareMode()
}
