@file:Suppress("MemberVisibilityCanBePrivate", "DEPRECATION", "EXPERIMENTAL_FEATURE_WARNING")

package ir.hossainco.utils

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.Intent.*
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Process.killProcess
import android.os.Process.myPid
import java.io.File
import java.util.*

inline class Package(val packageInfo: PackageInfo) {
	constructor(context: Context, packageName: String)
		: this(context.packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES))

	constructor(context: Context)
		: this(context, context.packageName)

	inline val packageName get() = packageInfo.packageName

	val applicationInfo
		get() = packageInfo.applicationInfo ?: null

	val apkFile
		get() = applicationInfo?.apkFile

	val sendAppIntent
		get() = applicationInfo?.sendAppIntent

	companion object {
		val ApplicationInfo.apkFile
			get() = File(publicSourceDir)

		val PackageInfo.apkFile
			get() = applicationInfo.apkFile

		val Context.apkFile
			get() = applicationInfo.apkFile

		val ApplicationInfo.sendAppIntent: Intent
			get() {
				val intent = Intent(ACTION_SEND)
				intent.type = "application/vnd.android.package-archive"
				intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(apkFile))
				return intent
			}

		fun Intent.chooserIntent(title: CharSequence? = null) =
			createChooser(this, title ?: "With ...")

		@TargetApi(Build.VERSION_CODES.ECLAIR)
		fun Context.killMe() {
			val pid = myPid()
			if (this is Activity) {
				overridePendingTransition(0, 0)
				finish()
			}
			System.gc()
			killProcess(pid)
		}

		fun Context.startExternalActivity(intent: Intent) = try {
			intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
			startActivity(intent)
			intent
		} catch (_: Throwable) {
			null
		}

		fun Context.setLocale(locale: Locale) = try {
			Locale.setDefault(locale)

			val ctx = this
			val config = ctx.resources.configuration ?: Configuration()
			config.locale = locale

			try {
				ctx.resources.updateConfiguration(config, ctx.resources.displayMetrics)
			} catch (_: Throwable) {
			}

			try {
				val appCtx = ctx.applicationContext
				if (ctx != appCtx)
					appCtx.resources.updateConfiguration(config, appCtx.resources.displayMetrics)
			} catch (_: Throwable) {
			}

			if (ctx is ContextWrapper) try {
				val baseCtx = ctx.baseContext
				if (ctx != baseCtx)
					baseCtx.resources.updateConfiguration(config, baseCtx.resources.displayMetrics)
			} catch (_: Throwable) {
			}

			Unit
		} catch (_: Throwable) {
		}

		fun createShareTextIntent(text: String): Intent {
			val intent = Intent(ACTION_SEND)
			intent.type = "text/plain"
			intent.putExtra(EXTRA_TEXT, text)
			return intent
		}

		fun Context.createLaunchPackageIntent(packageName: String) =
			packageManager.getLaunchIntentForPackage(packageName)

		fun createDeletePackageIntent(packageName: String): Intent {
			val intent = Intent(Intent.ACTION_DELETE)
			intent.setPackage(packageName)
			return intent
		}

		fun createLinkIntent(url: String) =
			Intent(ACTION_VIEW, Uri.parse(url))

		fun Context.openLinkIntent(url: String, inChrome: Boolean = true): Intent {
			val intent = createLinkIntent(url)
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
			intent.setPackage("com.android.chrome")

			if (inChrome) try {
				startActivity(intent)
				return intent
			} catch (_: Throwable) {
			}

			intent.setPackage(null)
			try {
				startActivity(intent)
			} catch (_: Throwable) {
			}

			return intent
		}
	}
}
