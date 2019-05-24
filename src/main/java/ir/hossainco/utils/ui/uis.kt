@file:JvmName("UIs")

package ir.hossainco.utils.ui

import android.graphics.Typeface
import ir.hossainco.utils.fields.trySetField

fun setDefaultTypefaces(default: Typeface, sansSerif: Typeface, serif: Typeface, monospace: Typeface) {
	trySetField(Typeface::class.java, "DEFAULT", null, default)
	trySetField(Typeface::class.java, "DEFAULT_BOLD", null, Typeface.create(default, Typeface.BOLD))

	trySetField(Typeface::class.java, "SANS_SERIF", null, sansSerif)
	trySetField(Typeface::class.java, "SERIF", null, serif)
	trySetField(Typeface::class.java, "MONOSPACE", null, monospace)
}
