package ir.hco.util.views

import android.view.Gravity
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewManager
import ir.hco.util.BaseApp
import ir.hco.util.R
import ir.hossainco.utils.view.appTextView
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.dip
import org.jetbrains.anko.frameLayout

fun ViewManager.ads() =
	frameLayout {
		val context = context!!
		val baseApp = context.applicationContext as? BaseApp

		id = R.id.ad
		backgroundColorResource = R.color.primaryDarkColor
		minimumHeight = dip(48)

		appTextView(textRes = R.string.ads, dark = false)
			.lparams(gravity = Gravity.CENTER)

		val ads = baseApp?.advertiser
		if (ads != null && ads.hasBanner()) {
			ads.createBanner(this)
				?.lparams(width = MATCH_PARENT, height = WRAP_CONTENT, gravity = Gravity.CENTER)
		}
	}

