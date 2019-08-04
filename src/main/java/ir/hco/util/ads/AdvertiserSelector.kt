package ir.hco.util.ads

import ir.hossainco.utils.tryOrNull

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
