package ir.hossainco.utils

import android.app.Application

object App {
	lateinit var app: Application
	inline val context get() = app.applicationContext
	inline val res get() = app.resources
	inline val assets get() = app.assets
}
