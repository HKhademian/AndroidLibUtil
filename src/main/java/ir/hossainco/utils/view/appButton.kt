package ir.hossainco.utils.view

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.view.ViewManager
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import ir.hossainco.utils.ui.HalfButtonGradientDrawable
import ir.hossainco.utils.ui.TextSize
import ir.hossainco.utils.ui.drawables.createButton
import org.jetbrains.anko.button
import org.jetbrains.anko.dip
import org.jetbrains.anko.textColor

fun ViewManager.appButton(
	@IdRes id: Int? = null,

	@StringRes textRes: Int? = null,
	text: String? = null,

	textSize: TextSize = TextSize.DefaultTextSize,
	listener: (view: View) -> Unit = {}
) = button {
	if (id != null) {
		this.id = id
	}

	if (textRes != null)
		setText(textRes)
	else if (text != null)
		setText(text)

	buttonize(this, textSize)
	setOnClickListener { listener(it) }
}

fun buttonize(
	button: TextView,
	textSize: TextSize = TextSize.DefaultTextSize,
	back: Drawable = createOliveButtonDrawable()
) = button.apply {
	val padding = dip(2)

	minWidth = dip(196)
	background = back
	gravity = Gravity.CENTER
	this.textSize = (if (textSize is TextSize.DefaultTextSize) TextSize.MediumTextSize.value else textSize.value) + 2f
	typeface = Typeface.DEFAULT_BOLD
	textColor = 0xFFffffff.toInt()
	setPadding(padding * 4, padding, padding * 4, padding)
}

//fun buttonizeOlive(button: TextView, textSize: TextSize = TextSize.DefaultTextSize) =
//	buttonize(button, textSize, createOliveButtonDrawable())

//fun buttonizeWine(button: TextView, textSize: TextSize = TextSize.DefaultTextSize) =
//	buttonize(button, textSize, createWineButtonDrawable())


private fun createOliveButtonDrawable() =
	createButtonDrawable(
		0xFF7fbd80.toInt(),
		0xFF409e40.toInt(),
		0xFF2c942c.toInt(),
		0xFF108610.toInt(),
		0xFF007e01.toInt()
	)

//private fun createWineButtonDrawable() =
//	createButtonDrawable(0xFFbd7f80.toInt(), 0xFF9e4040.toInt(), 0xFF942c2c.toInt(), 0xFF861010.toInt(), 0xFF7e0001.toInt())

private fun createButtonDrawable(colorStart: Int, colorEnd: Int, colorMidGrad: Int, colorMidSol: Int, colorSol: Int) =
	createButton(
		HalfButtonGradientDrawable(
			false, 8f, 4f, 4f,
			colorStart, colorEnd, colorMidGrad, colorMidSol, colorSol
		),
		HalfButtonGradientDrawable(
			true, 8f, 4f, 4f,
			colorStart, colorEnd, colorMidGrad, colorMidSol, colorSol
		),
		HalfButtonGradientDrawable(
			false, 8f, 0f, 0f,
			colorMidSol, colorSol, colorStart, colorMidSol, colorSol
		)
	)
