package ir.hossainco.utils

fun String.persianizeLatinNumbers() = this
	.replace("0", "۰")
	.replace("1", "۱")
	.replace("2", "۲")
	.replace("3", "۳")
	.replace("4", "۴")
	.replace("5", "۵")
	.replace("6", "۶")
	.replace("7", "۷")
	.replace("8", "۸")
	.replace("9", "۹")

fun String.persianizeArabicNumbers() = this
	.replace("٠", "۰")
	.replace("١", "۱")
	.replace("٢", "۲")
	.replace("٣", "۳")
	.replace("٤", "۴")
	.replace("٥", "۵")
	.replace("٦", "۶")
	.replace("٧", "۷")
	.replace("٨", "۸")
	.replace("٩", "۹")

fun String.persianizeArabicWords() = this
	.replace("ؠ", "ی")
	.replace("ي", "ی")
	.replace("ئ", "ی")
	.replace("ك", "ک")
	.replace("ک", "ک")
	.replace("ؤ", "و")
	.replace("أ", "ا")
	.replace("أ", "ا")
	.replace("إ", "ا")
	// .replace("آ", "ا")
	.replace("ء", "ا")
	.replace("‌", " ")

fun String.persianize() = this
	.persianizeLatinNumbers()
	.persianizeArabicNumbers()
	.persianizeArabicWords()

