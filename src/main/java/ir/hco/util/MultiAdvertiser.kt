package ir.hco.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewManager
import ir.hossainco.utils.tryOrNull

class MultiAdvertiser(
	override val minAdGap: Long = Advertiser.MIN_AD_GAP,
	override var lastFullAdShown: Long = System.currentTimeMillis(),
	val selector: AdvertiserSelector = RandomAdvertiserSelector,
	val advertisers: List<MultiAdvertiserData>
) : Advertiser {
	constructor(
		minAdGap: Long = Advertiser.MIN_AD_GAP,
		lastFullAdShown: Long = System.currentTimeMillis(),
		selector: AdvertiserSelector = RandomAdvertiserSelector,
		vararg advertisers: Advertiser
	) : this(
		minAdGap = minAdGap,
		lastFullAdShown = lastFullAdShown,
		selector = selector,
		advertisers = advertisers.map { it.multi() }
	)


	override fun init(context: Context) =
		advertisers.forEach { it.advertiser.init(context) }

	override fun hasFull(activity: Activity) =
		advertisers.any { it.hasMultiFull(activity) && it.advertiser.hasFull(activity) }

	override fun showFull(activity: Activity) =
		selector.selectAdvertiser(advertisers) { it.hasMultiFull(activity) && it.advertiser.hasFull(activity) }
			?.advertiser?.showFull(activity)
			?: false


	override fun hasBanner(unitName: String?) =
		advertisers.any { it.hasMultiBanner(unitName) }

	override fun createBanner(vm: ViewManager, unitName: String?, init: View.() -> Unit): View? =
		selector.selectAdvertiser(advertisers) { it.hasMultiBanner(unitName) && it.advertiser.hasBanner(unitName) }
			?.let {
				val advertiser = it.advertiser
				val banner = advertiser.createBanner(vm, unitName, init)
				if (banner != null) {
					banner.tag = BannerData(advertiser, banner.tag)
				}
				banner
			}

	override fun recycleBanner(view: View) {
		val bannerData = view.tag as? BannerData ?: return // advertisers.forEach { it.recycleBanner(view) }
		val advertiser = bannerData.advertiser
		view.tag = bannerData.data
		advertiser.recycleBanner(view)
	}
}

interface AdvertiserSelector {
	fun selectAdvertiser(
		advertisers: List<MultiAdvertiserData>,
		condition: (MultiAdvertiserData) -> Boolean
	): MultiAdvertiserData? =
		null
}

object RandomAdvertiserSelector : AdvertiserSelector {
	override fun selectAdvertiser(advertisers: List<MultiAdvertiserData>, condition: (MultiAdvertiserData) -> Boolean) =
		tryOrNull {
			advertisers.filter(condition).random()
		}
}

interface MultiAdvertiserData {
	val advertiser: Advertiser

	fun hasMultiBanner(unitName: String?): Boolean
	fun getMultiBannerFillRate(unitName: String?): Float

	fun hasMultiFull(activity: Activity): Boolean
	fun getMultiFullFillRate(activity: Activity): Float
}

open class SimpleMultiAdvertiserData(
	override val advertiser: Advertiser,
	val bannerFillRate: Float = 1f,
	val fullFillRate: Float = 1f
) : MultiAdvertiserData {
	override fun hasMultiBanner(unitName: String?) =
		bannerFillRate > 0

	override fun getMultiBannerFillRate(unitName: String?) =
		bannerFillRate

	override fun hasMultiFull(activity: Activity) =
		fullFillRate > 0

	override fun getMultiFullFillRate(activity: Activity) =
		fullFillRate
}

private class BannerData(
	val advertiser: Advertiser,
	val data: Any?
)

fun Advertiser.multi(bannerFillRate: Float = 1f, fullFillRate: Float = 1f) =
	SimpleMultiAdvertiserData(this, bannerFillRate, fullFillRate)
