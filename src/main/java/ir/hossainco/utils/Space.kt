package ir.hossainco.utils

sealed class Space(val value: Float) {
	class CustomSpace(value: Float) : Space(value)
	object DefaultSpace : Space(MediumSpace.value)

	object NoSpace : Space(0f)
	object ExtraSmallSpace : Space(2f)
	object SmallSpace : Space(4f)
	object MediumSpace : Space(6f)
	object LargeSpace : Space(8f)
	object ExtraLargeSpace : Space(10f)
	object TitleSpace : Space(12f)
	object HeaderSpace : Space(14f)

	operator fun plus(extra: Float) =
		CustomSpace(value + extra)

	operator fun plus(other: Space) =
		CustomSpace(value + other.value)

	operator fun minus(extra: Float) =
		CustomSpace(value - extra)

	operator fun minus(other: Space) =
		CustomSpace(value - other.value)

	operator fun times(extra: Float) =
		CustomSpace(value * extra)

	operator fun div(extra: Float) =
		CustomSpace(value / extra)
}
