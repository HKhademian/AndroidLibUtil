@file:Suppress("unused")

package ir.hossainco.utils.ui

sealed class TextSize(val value: Float) {
	class CustomTextSize(value: Float) : TextSize(value)
	object DefaultTextSize : TextSize(MediumTextSize.value)

	object ExtraSmallTextSize : TextSize(10f)
	object SmallTextSize : TextSize(12f)
	object MediumTextSize : TextSize(13f)
	object LargeTextSize : TextSize(16f)
	object ExtraLargeTextSize : TextSize(18f)
	object TitleTextSize : TextSize(20f)
	object HeaderTextSize : TextSize(22f)

	operator fun plus(extra: Float) =
		CustomTextSize(value + extra)

	operator fun plus(other: TextSize) =
		CustomTextSize(value + other.value)

	operator fun minus(extra: Float) =
		CustomTextSize(value - extra)

	operator fun minus(other: TextSize) =
		CustomTextSize(value - other.value)

	operator fun times(extra: Float) =
		CustomTextSize(value * extra)

	operator fun div(extra: Float) =
		CustomTextSize(value / extra)
}
