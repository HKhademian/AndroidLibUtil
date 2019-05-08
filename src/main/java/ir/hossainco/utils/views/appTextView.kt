package ir.hossainco.utils.views

import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewManager
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import ir.hossainco.utils.TextSize
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

inline fun ViewManager.appTextView(
	@IdRes id: Int? = null,

	@StringRes textRes: Int? = null,
	text: String? = null,

	dark: Boolean = true,

	@ColorInt notebookLineColor: Int? = null,
	@ColorRes notebookLineColorRes: Int? = null,

	textSize: TextSize = TextSize.DefaultTextSize,
	init: TextView.() -> Unit = {}
): TextView {
	val view = when {
		notebookLineColor != null -> ankoView({ ctx ->
			LinedTextView(
				ctx,
				lineColor = notebookLineColor,
				lineColorRes = notebookLineColorRes
			)
		}, 0, {})
		else -> textView()
	}

	return view.apply {
		if (id != null) {
			this.id = id
		}

		if (textRes != null)
			setText(textRes)
		else if (text != null)
			setText(text)

		textViewize(this, dark, textSize)
		init()
	}
}

fun textViewize(
	view: TextView,
	dark: Boolean = true,
	textSize: TextSize = TextSize.DefaultTextSize
) = view.apply {
	padding = dip(8)
	includeFontPadding = false
	gravity = Gravity.CENTER
	typeface = Typeface.DEFAULT_BOLD
	this.textSize = if (textSize is TextSize.DefaultTextSize) TextSize.MediumTextSize.value else textSize.value
	textColor = (if (dark) 0x06 else 0xf0).gray.opaque
	setLineSpacing(0f, 1.25f)
	setShadowLayer(2f, 1f, 1f, (if (dark) 0x99 else 0x66).gray.opaque)
}
