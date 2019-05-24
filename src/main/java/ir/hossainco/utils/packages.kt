@file:Suppress(
	"MemberVisibilityCanBePrivate", "DEPRECATION", "EXPERIMENTAL_FEATURE_WARNING",
	"PackageDirectoryMismatch"
)

package ir.hossainco.utils.packages

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
import android.view.View.LAYOUT_DIRECTION_LOCALE
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
}


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

@TargetApi(Build.VERSION_CODES.ECLAIR)
fun killMe(context: Context) {
	val pid = myPid()
	if (context is Activity) {
		context.overridePendingTransition(0, 0)
		context.finish()
	}
	System.gc()
	killProcess(pid)
}

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
fun Activity.forceLayoutDir(dir: Int = LAYOUT_DIRECTION_LOCALE) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
		window.decorView.layoutDirection = dir
	}
}

fun setLocale(context: Context, locale: Locale) = try {
	Locale.setDefault(locale)

	val config = context.resources.configuration ?: Configuration()
	config.locale = locale

	try {
		context.resources.updateConfiguration(config, context.resources.displayMetrics)
	} catch (_: Throwable) {
	}

	try {
		val appCtx = context.applicationContext
		if (context != appCtx)
			appCtx.resources.updateConfiguration(config, appCtx.resources.displayMetrics)
	} catch (_: Throwable) {
	}

	if (context is ContextWrapper) try {
		val baseCtx = context.baseContext
		if (context != baseCtx)
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
	val intent = Intent(ACTION_DELETE)
	intent.setPackage(packageName)
	return intent
}

fun createLinkIntent(url: String) =
	Intent(ACTION_VIEW, Uri.parse(url))

fun openLinkIntent(context: Context, url: String, inChrome: Boolean = true): Intent {
	val intent = createLinkIntent(url)
	intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
	intent.setPackage("com.android.chrome")

	if (inChrome) try {
		context.startActivity(intent)
		return intent
	} catch (_: Throwable) {
	}

	intent.setPackage(null)
	try {
		context.startActivity(intent)
	} catch (_: Throwable) {
	}

	return intent
}

fun Intent.startNewTask(context: Context) = try {
	addFlags(FLAG_ACTIVITY_NEW_TASK)
	context.startActivity(this)
} catch (ignored: Throwable) {
}
