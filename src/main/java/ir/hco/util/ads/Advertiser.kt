package ir.hco.util.ads

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewManager
import org.jetbrains.anko.frameLayout

interface Advertiser {
	companion object {
		const val MIN_AD_GAP = 2 * 60 * 1000L
		const val UNIT_DEFAULT = "default"
		const val UNIT_BANNER = "banner"
		const val UNIT_CONTENT = "content"

		val DEFAULT = object : Advertiser {
			override var lastFullAdShown = System.currentTimeMillis()
		}

		protected fun mayShowFull(
			activity: Activity,
			lastAdShown: Long = 0,
			minAdGap: Long = MIN_AD_GAP,
			hasFull: (Activity) -> Boolean,
			showFull: (Activity) -> Boolean
		): Long? {
			val now = System.currentTimeMillis()
			if (now - lastAdShown > minAdGap && (Math.random() * 100) < 30) {
				if (hasFull(activity)) {
					val res = showFull(activity)
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
	var lastFullAdShown: Long


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


	fun hasFull(activity: Activity): Boolean =
		false

	fun showFull(activity: Activity): Boolean =
		false

	fun mayShowFull(activity: Activity): Boolean {
		val res = mayShowFull(
			activity,
			lastFullAdShown,
			minAdGap,
			::hasFull,
			::showFull
		)
			?: return false
		lastFullAdShown = res
		return true
	}
}

open class AdvertiserWrapper(val baseAdvertiser: Advertiser) : Advertiser by baseAdvertiser
