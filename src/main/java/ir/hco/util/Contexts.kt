package ir.hco.util

import android.content.Context
import android.os.Build
import org.jetbrains.anko.configuration

val Context.local
	get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
		this.configuration.locales[0]
	} else {
		this.configuration.locale
	}


