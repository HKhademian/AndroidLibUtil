package ir.hco.util.views

import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewManager
import ir.hco.util.BaseApp
import ir.hco.util.R
import ir.hossainco.utils.view.appTextView
import org.jetbrains.anko._FrameLayout
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.dip
import org.jetbrains.anko.frameLayout

fun ViewManager.ads(
	unitName: String? = null,
	refresh: Long = 0,
	init: _FrameLayout.() -> Unit = {}
) =
	frameLayout {
		val context = context!!
		val baseApp = context.applicationContext as? BaseApp
		val ads = baseApp?.advertiser
		val autoRefresh = refresh > 0

		id = R.id.ad
		backgroundColorResource = R.color.primaryDarkColor
		minimumHeight = dip(48)

		appTextView(textRes = R.string.ads, dark = false)
			.lparams(gravity = Gravity.CENTER)

		val adPlace = frameLayout()
			.lparams(width = WRAP_CONTENT, height = WRAP_CONTENT, gravity = Gravity.CENTER)

		var lastAd: View? = null
		fun refreshAd() {
			adPlace.removeAllViews()

			if (ads == null) {
				lastAd = null
				return
			}

			lastAd?.let { ads.recycleBanner(it) }
			lastAd = if (!ads.hasBanner(unitName)) null
			else ads.createBanner(adPlace, unitName)

			if (autoRefresh) {
				adPlace.postDelayed({ refreshAd() }, refresh)
			}
		}

		refreshAd()
		init()
	}

