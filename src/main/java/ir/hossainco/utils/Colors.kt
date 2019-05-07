@file:Suppress("unused", "NAME_SHADOWING", "MemberVisibilityCanBePrivate")

package ir.hossainco.utils

import android.graphics.Color.*
import java.lang.Math.*

object Colors {
	inline val Int.red get() = red(this)
	inline val Int.green get() = green(this)
	inline val Int.blue get() = blue(this)
	inline val Int.alpha get() = alpha(this)

	fun extractColor(color: Int) = arrayOf(
		color.red,
		color.green,
		color.blue,
		color.alpha
	)

	fun alphaColor(color: Int, alpha: Int): Int {
		return argb(alpha, red(color), green(color), blue(color))
	}

	/** detect y of YIQ color */
	fun isLightColor(color: Int) = (299 * red(color) + 587 * green(color) + 114 * blue(color)) / 1000 > 128f

	fun contrastColor(color: Int) =
		if (isLightColor(color)) -0x1000000 else -0x1

	fun brightenColor(color: Int, scale: Float): Int {
		var scale = scale
		if (scale == 0f)
			return color
		val (r, g, b, a) = extractColor(color)

		if (scale < 0) {
			scale = -max(-1f, scale)
			val red = round(max(0f, r - 255 * scale))
			val green = round(max(0f, g - 255 * scale))
			val blue = round(max(0f, b - 255 * scale))
			return argb(a, red, green, blue)
		}
		scale = min(1f, scale)
		val red = round((r * (1 - scale) / 255 + scale) * 255)
		val green = round((g * (1 - scale) / 255 + scale) * 255)
		val blue = round((b * (1 - scale) / 255 + scale) * 255)
		return argb(a, red, green, blue)
	}

}
