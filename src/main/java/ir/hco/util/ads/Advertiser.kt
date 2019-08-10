package ir.hco.util.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewManager
import org.jetbrains.anko.frameLayout

interface Advertiser {
	companion object {
		const val MIN_AD_GAP = 1 * 60 * 1000L
		const val UNIT_DEFAULT = "default"
		const val UNIT_BANNER = "banner"
		const val UNIT_CONTENT = "content"

		val DEFAULT = object : Advertiser {
			override var fullLastAdShown = System.currentTimeMillis()
		}

		protected fun mayShowFull(
			activity: Activity,
			lastAdShown: Long = 0,
			minAdGap: Long = MIN_AD_GAP,
			fillRate: Float = 0.3f,
			hasFull: (Activity) -> Boolean,
			showFull: (Activity) -> Boolean
		): Long? {
			fun log(msg: String?) {
				println("Advertiser: $msg")
				Log.e("Advertiser", msg)
			}

			val now = System.currentTimeMillis()
			val past = now - lastAdShown

			if (past < minAdGap) {
				log("Too soon to show full. past: $past & minAdGap: $minAdGap")
				return null
			}

			val rnd = Math.random()
			if (rnd > fillRate) {
				log("No fillRate chance to show full. fillRate: $fillRate & rnd: $rnd")
				return null
			}

			if (!hasFull(activity)) {
				log("No hasFull to show full")
				return null
			}

			val res = showFull(activity)
			if (!res) {
				log("not showFull")
				return null
			}

			log("full shown")
			return System.currentTimeMillis()
		}
	}

	val code get() = "ads"
	val title get() = "Advertiser"

	val fullMinAdGap: Long get() = MIN_AD_GAP
	val fullFillRate: Float get() = 0.3f
	var fullLastAdShown: Long


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
			fullLastAdShown,
			fullMinAdGap,
			fullFillRate,
			::hasFull,
			::showFull
		)
			?: return false
		fullLastAdShown = res
		return true
	}
}

open class AdvertiserWrapper(val baseAdvertiser: Advertiser) : Advertiser by baseAdvertiser
