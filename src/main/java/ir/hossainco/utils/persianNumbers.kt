/// base source : https://github.com/mahmoud-eskandari/NumToPersian/blob/master/src/num2persian.js

@file:Suppress("PackageDirectoryMismatch")

package ir.hossainco.utils.texts

val Number.length
	get() = when (this) {
		0 -> 1
		else -> Math.log10(Math.abs(toDouble())).toInt() + 1
	}

private const val DELIMITER = " و "
private const val ZERO = "صفر"
private const val NEG = "منفی "
private const val OUT_OF_RANGE = "خارج از محدوده"
private val LETTERS = arrayOf(
	arrayOf(ZERO, "یک", "دو", "سه", "چهار", "پنج", "شش", "هفت", "هشت", "نه"),
	arrayOf("ده", "یازده", "دوازده", "سیزده", "چهارده", "پانزده", "شانزده", "هفده", "هجده", "نوزده", "بیست"),
	arrayOf("", "", "بیست", "سی", "چهل", "پنجاه", "شصت", "هفتاد", "هشتاد", "نود"),
	arrayOf("", "یکصد", "دویست", "سیصد", "چهارصد", "پانصد", "ششصد", "هفتصد", "هشتصد", "نهصد"),
	arrayOf(
		"", " هزار", " میلیون", " میلیارد", " بیلیون", " بیلیارد", " تریلیون", " تریلیارد",
		"کوآدریلیون", " کادریلیارد", " کوینتیلیون", " کوانتینیارد", " سکستیلیون", " سکستیلیارد", " سپتیلیون",
		"سپتیلیارد", " اکتیلیون", " اکتیلیارد", " نانیلیون", " نانیلیارد", " دسیلیون", " دسیلیارد"
	)
)

private fun groupNumber(num: Long): List<Int> {
	val out = when (num.length % 3) {
		1 -> "00$num"
		2 -> "0$num"
		else -> num.toString()
	}
	return out.chunked(3).map(String::toInt)
}

private fun groupToLetter(num: Int): String {
	if (num == 0) {
		return ""
	}

	if (num < 10) {
		return LETTERS[0][num]
	}

	if (num <= 20) {
		return LETTERS[1][num - 10]
	}

	if (num < 100) {
		val one = num % 10
		val ten = (num - one) / 10
		if (one > 0) {
			return LETTERS[2][ten] + DELIMITER + LETTERS[0][one]
		}
		return LETTERS[2][ten]
	}

	val one = num % 10
	val hundreds = (num - (num % 100)) / 100
	val ten = (num - ((hundreds * 100) + one)) / 10
	val out = mutableListOf(LETTERS[3][hundreds])
	val secondPart = ((ten * 10) + one)

	if (secondPart > 0) {
		if (secondPart < 10) {
			out.add(LETTERS[0][secondPart])
		} else if (secondPart <= 20) {
			out.add(LETTERS[1][secondPart - 10])
		} else {
			out.add(LETTERS[2][ten])
			if (one > 0) {
				out.add(LETTERS[0][one])
			}
		}
	}

	return out.joinToString(DELIMITER)
}

fun Number.toPersianLetter(): String {
	val abs = this.toLong()
	val num = Math.abs(abs)

	// return ZERO
	if (num == 0L) {
		return ZERO
	}

	if (num.length > LETTERS[4].size * 3) {
		// if (num.length > 66) {
		return OUT_OF_RANGE
	}

	val groups = groupNumber(num)
	val groupCount = groups.size
	val out = mutableListOf<String>()

	(0 until groupCount).forEach { i ->
		val title = LETTERS[4][groupCount - (i + 1)]
		val converted = groupToLetter(groups[i])
		if (converted != "") {
			out.add(converted + title)
		}
	}

	return if (abs < 0)
		NEG + out.joinToString(DELIMITER)
	else
		out.joinToString(DELIMITER)
}
