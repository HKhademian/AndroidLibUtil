@file:Suppress("unused")

package ir.hco.util

import android.app.Application
import android.graphics.Typeface
import ir.hossainco.utils.packages.setLocale
import ir.hossainco.utils.tryOrDefault
import ir.hossainco.utils.ui.setDefaultTypefaces
import java.util.*

open class BaseApp(
	private val locale: Locale = LOCALE_FA
) : Application() {
	companion object {
		private val LOCALE_FA = Locale("fa")

		private lateinit var instance: Application

		val context get() = instance.applicationContext
		val res get() = instance.resources
		val assets get() = instance.assets

		val appLabel get() = instance.getString(instance.applicationInfo.labelRes)
		val appLink get() = "http://play.google.com/store/apps/details?id=${instance.packageName}"
	}

	@Suppress("LeakingThis")
	open val logger: Logger = LogcatLogger
	open val advertiser: Advertiser = Advertiser.DEFAULT
	open val publisher: Publisher = GooglePlayPublisher

	override fun onCreate() {
		super.onCreate()
		instance = this

		initLocale()
		initLogger()
		initPublisher()
		initAdvertiser()
		initRepository()
		initMessaging()
		initFonts()
	}

	protected open fun initLocale() {
		setLocale(this, locale)
	}

	protected open fun initLogger() {
		logger.init(applicationContext)
	}

	protected open fun initPublisher() {
		publisher.init(applicationContext)
	}

	protected open fun initAdvertiser() {
		advertiser.init(applicationContext)
	}

	protected open fun initRepository() {
	}

	protected open fun initMessaging() {
	}

	protected open fun initFonts() {
		loadFonts()
	}

	open fun loadFonts() {
		val (sans, serif) = arrayOf(
			tryOrDefault(Typeface.SANS_SERIF) { Typeface.createFromAsset(assets, "fonts/iransans_light.ttf") },
			tryOrDefault(Typeface.SERIF) { Typeface.createFromAsset(assets, "fonts/byekan.ttf") }
		)
		setDefaultTypefaces(
			default = sans,
			sansSerif = sans,
			serif = serif,
			monospace = serif
		)
	}

}
