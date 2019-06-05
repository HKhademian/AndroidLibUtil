package ir.hossainco.utils

import android.app.Application

object App {
	lateinit var app: Application
	inline val context get() = app.applicationContext
	inline val res get() = app.resources
	inline val assets get() = app.assets

	val label get() = app.getString(app.applicationInfo.labelRes)
	val appLink get() = "http://play.google.com/store/apps/details?id=${app.packageName}"
}
