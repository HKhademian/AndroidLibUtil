package ir.hco.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import ir.hossainco.utils.packages.isPackageExisted

interface Publisher {
	fun init(context: Context) = Unit

	fun isPublisherAvailable(context: Context): Boolean =
		false

	fun createDeveloperPageIntent(
		context: Context,
		developerId: String? = null,
		forceApp: Boolean = false
	): Intent

	fun createAppPageIntent(
		context: Context,
		packageName: String? = null,
		action: String = ACTION_VIEW,
		forceApp: Boolean = false
	): Intent

	companion object {
		const val ACTION_VIEW = "view"
		const val ACTION_RATE = "rate"
		const val ACTION_INSTALL = "install"
		const val ACTION_COMMENT = "comment"
		const val ACTION_DELETE = "delete"
	}
}

open class GooglePlayPublisher(
	private val developerId: String
) : Publisher {
	override fun isPublisherAvailable(context: Context) =
		isPackageExisted(context, PUBLISHER_PACKAGE)

	override fun createDeveloperPageIntent(context: Context, developerId: String?, forceApp: Boolean) =
		Intent(Intent.ACTION_VIEW).apply {
			data = Uri.parse(
				"https://play.google.com/store/apps/developer?id=${developerId ?: this@GooglePlayPublisher.developerId}"
			)
			if (forceApp) `package` = PUBLISHER_PACKAGE
		}

	override fun createAppPageIntent(context: Context, packageName: String?, action: String, forceApp: Boolean) =
		Intent(Intent.ACTION_VIEW).apply {
			data = Uri.parse("https://play.google.com/store/apps/details?id=${packageName ?: context.packageName}")
			if (forceApp) `package` = PUBLISHER_PACKAGE
		}

	companion object : GooglePlayPublisher(developerId = "HossainCo") {
		private const val PUBLISHER_PACKAGE = "com.android.vending"
	}
}

open class BazaarPublisher(
	private val developerId: String
) : Publisher {

	override fun isPublisherAvailable(context: Context) =
		isPackageExisted(context, PUBLISHER_PACKAGE)

	override fun createDeveloperPageIntent(context: Context, developerId: String?, forceApp: Boolean) =
		Intent(Intent.ACTION_VIEW).apply {
			data = Uri.parse("https://cafebazaar.ir/developer/${developerId ?: this@BazaarPublisher.developerId}")
			if (forceApp) `package` = PUBLISHER_PACKAGE
		}

	override fun createAppPageIntent(context: Context, packageName: String?, action: String, forceApp: Boolean) =
		Intent(Intent.ACTION_VIEW).apply {
			data = Uri.parse("https://cafebazaar.ir/app/${packageName ?: context.packageName}")
			if (forceApp) `package` = PUBLISHER_PACKAGE
		}

	companion object : BazaarPublisher(developerId = "hossainco") {
		private const val PUBLISHER_PACKAGE = "com.farsitel.bazaar"
	}

}

open class MyketPublisher(
private val developerId: String
) : Publisher {
	override fun isPublisherAvailable(context: Context) =
		isPackageExisted(context, PUBLISHER_PACKAGE)

	override fun createDeveloperPageIntent(context: Context, developerId: String?, forceApp: Boolean): Intent {
		val isPublisherAvailable = isPublisherAvailable(context)

		val link = if (!isPublisherAvailable)
			"https://myket.ir/developer/${developerId ?: this.developerId}/apps"
		else
			"myket://developer/${developerId ?: context.packageName}"

		return Intent(Intent.ACTION_VIEW).apply {
			data = Uri.parse(link)
			if (isPublisherAvailable && forceApp) `package` = PUBLISHER_PACKAGE
		}
	}

	override fun createAppPageIntent(context: Context, packageName: String?, action: String, forceApp: Boolean): Intent {
		val isPublisherAvailable = isPublisherAvailable(context)
		val publicLink = "https://myket.ir/app/${packageName ?: context.packageName}"

		val link = if (!isPublisherAvailable) publicLink else when (action) {
			Publisher.ACTION_INSTALL ->
				"myket://download/${packageName ?: context.packageName}"

			Publisher.ACTION_COMMENT, Publisher.ACTION_RATE ->
				"myket://comment?id=${packageName ?: context.packageName}"

			else -> publicLink
		}

		return Intent(Intent.ACTION_VIEW).apply {
			data = Uri.parse(link)
			if (isPublisherAvailable && forceApp) `package` = PUBLISHER_PACKAGE
		}
	}

	companion object : MyketPublisher(developerId = "dev-19620") {
		private const val PUBLISHER_PACKAGE = "ir.mservices.market"
	}
}
