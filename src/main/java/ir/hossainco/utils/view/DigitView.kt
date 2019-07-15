package ir.hossainco.utils.view

import android.view.ViewManager
import android.widget.ImageView
import ir.hossainco.utils.R
import ir.hossainco.utils.ui.anims.animatedChange
import org.jetbrains.anko.imageView

fun ViewManager.digit(
	initValue: Int = 0,
	animated: Boolean = true,

	init: DigitView.() -> Unit = {}
) = DigitView(imageView {
	scaleType = ImageView.ScaleType.CENTER_CROP
	// backgroundColor = Color.WHITE
}).apply {
	this.value = initValue
	this.animated = animated
	init()
}

inline class DigitView(val view: ImageView) {
	companion object {
		val DIGITS = mutableListOf<Int>(
//			R.drawable.digit_0,
//			R.drawable.digit_1,
//			R.drawable.digit_2,
//			R.drawable.digit_3,
//			R.drawable.digit_4,
//			R.drawable.digit_5,
//			R.drawable.digit_6,
//			R.drawable.digit_7,
//			R.drawable.digit_8,
//			R.drawable.digit_9
		)
	}

	private val data: DigitData
		get() {
			val tag = view.tag
			return if (tag is DigitData) tag else {
				DigitData().also { view.tag = it }
			}
		}


	var value
		get() = data.value
		set(value) {
			if (value !in 0..9) return
			val data = data

			if (!animated || data.value < 0 || value == data.value) {
				view.setImageResource(DIGITS[value])
			} else {
				view.animatedChange(animBeforeRes = R.anim.down_out, animAfterRes = R.anim.down_in) {
					setImageResource(DIGITS[value])
				}
			}

			data.value = value
		}

	var animated
		get() = data.animated
		set(value) {
			data.animated = value
		}

}

private data class DigitData(var value: Int = -1, var animated: Boolean = true)
