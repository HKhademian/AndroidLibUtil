@file:Suppress("unused")

package ir.hco.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf

interface Logger {
	companion object {
		const val VERBOSE = Log.VERBOSE
		const val DEBUG = Log.DEBUG
		const val INFO = Log.INFO
		const val WARN = Log.WARN
		const val ERROR = Log.ERROR
		const val ASSERT = Log.ASSERT

		const val SOURCE_UNKNOWN = "unknown"
		const val SOURCE_GLOBAL = "global"
		const val EVENT_APP_OPEN = "appOpen"
		const val EVENT_PAGE_OPEN = "pageOpen"
		const val EVENT_LOG = "log"

		const val DATA_MESSAGE = "message"
		const val DATA_THROWABLE = "throwable"

		fun Logger.v(tag: String, message: String? = null, throwable: Throwable? = null) =
			log(VERBOSE, tag, message, throwable)

		fun Logger.d(tag: String, message: String? = null, throwable: Throwable? = null) =
			log(DEBUG, tag, message, throwable)

		fun Logger.i(tag: String, message: String? = null, throwable: Throwable? = null) =
			log(INFO, tag, message, throwable)

		fun Logger.w(tag: String, message: String? = null, throwable: Throwable? = null) =
			log(WARN, tag, message, throwable)

		fun Logger.e(tag: String, message: String? = null, throwable: Throwable? = null) =
			log(Log.ERROR, tag, message, throwable)

		fun Logger.a(tag: String, message: String? = null, throwable: Throwable? = null) =
			log(Log.ASSERT, tag, message, throwable)

		fun Logger.wtf(tag: String, message: String? = null, throwable: Throwable? = null) =
			log(Log.ASSERT, tag, message, throwable)
	}

	fun init(context: Context) = Unit


	fun event(priority: Int = VERBOSE, source: String, event: String, data: Bundle?)

	fun event(priority: Int = VERBOSE, source: String, event: String, data: Map<String, Any?> = emptyMap()) =
		event(priority, source, event, bundleOf(*data.entries.map { it.key to it.value }.toTypedArray()))

	fun event(priority: Int = VERBOSE, source: String, event: String, vararg data: Pair<String, Any?>) =
		event(priority, source, event, bundleOf(*data))


	fun appOpen(intent: Intent?) =
		event(VERBOSE, SOURCE_GLOBAL, EVENT_APP_OPEN, intent?.extras)

	fun pageOpen(page: String, data: Map<String, String> = emptyMap()) =
		event(VERBOSE, SOURCE_GLOBAL, EVENT_PAGE_OPEN, data)

	fun log(
		priority: Int = Log.VERBOSE,
		tag: String = SOURCE_GLOBAL,
		message: String? = null,
		throwable: Throwable? = null
	) {
		event(
			priority, tag, EVENT_LOG,
			DATA_MESSAGE to message,
			DATA_THROWABLE to throwable?.toString()
		)
	}
}

object LogcatLogger : Logger {
	override fun event(priority: Int, source: String, event: String, data: Bundle?) {
		Log.println(priority, source, data?.get(Logger.DATA_MESSAGE)?.toString())
	}
}
