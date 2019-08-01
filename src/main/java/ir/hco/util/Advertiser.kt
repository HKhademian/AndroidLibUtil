package ir.hco.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewManager
import org.jetbrains.anko.frameLayout

interface Advertiser {
	companion object {
		private const val MIN_AD_GAP = 2 * 60 * 1000L
		const val UNIT_DEFAULT = "default"
		const val UNIT_BANNER = "banner"
		const val UNIT_CONTENT = "content"
		val DEFAULT = object : Advertiser {
			override var lastInterstitialAdShown = System.currentTimeMillis()
		}

		protected fun mayShowInterstitial(
			activity: Activity,
			lastAdShown: Long = 0,
			minAdGap: Long = MIN_AD_GAP,
			hasInterstitial: (Activity) -> Boolean,
			showInterstitial: (Activity) -> Boolean
		): Long? {
			val now = System.currentTimeMillis()
			if (now - lastAdShown > minAdGap && (Math.random() * 100) < 30) {
				if (hasInterstitial(activity)) {
					val res = showInterstitial(activity)
					if (res) {
						return System.currentTimeMillis()
					}
					return null
				}
			}
			return null
		}
	}

	val minAdGap: Long get() = MIN_AD_GAP
	var lastInterstitialAdShown: Long


	fun init(context: Context) =
		Unit


	fun hasBanner(unitName: String? = null): Boolean =
		false

	fun createBanner(vm: ViewManager, unitName: String? = null, init: View.() -> Unit = {}): View? =
		with(vm) {
			frameLayout()
		}

	fun recycleBanner(view: View) =
		Unit


	fun hasInterstitial(activity: Activity): Boolean =
		false

	fun showInterstitial(activity: Activity): Boolean =
		false

	fun mayShowInterstitial(activity: Activity): Boolean {
		val res = mayShowInterstitial(
			activity,
			lastInterstitialAdShown,
			minAdGap,
			::hasInterstitial,
			::showInterstitial
		)
			?: return false
		lastInterstitialAdShown = res
		return true
	}
}
