package ir.hco.util.ads

import android.app.Activity

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
