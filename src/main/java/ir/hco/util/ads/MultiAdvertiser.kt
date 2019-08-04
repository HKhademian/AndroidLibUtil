package ir.hco.util.ads

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewManager

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

private class BannerData(
	val advertiser: Advertiser,
	val data: Any?
)

fun Advertiser.multi(bannerFillRate: Float = 1f, fullFillRate: Float = 1f) =
	SimpleMultiAdvertiserData(this, bannerFillRate, fullFillRate)
