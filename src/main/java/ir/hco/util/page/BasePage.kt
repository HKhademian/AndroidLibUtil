package ir.hco.util.page

import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ir.hco.util.BaseApp
import ir.hco.util.R
import ir.hossainco.utils.packages.setLocale

open class BasePage : Fragment() {
	open val hasAds = true
	open val title: String? = null

	protected open fun updateTitle(title: String?) =
		Unit

	protected open fun updateAds(hasAds: Boolean) {
		val activity = activity ?: return
		activity.findViewById<View>(R.id.ad)?.isVisible = hasAds
	}

	override fun onAttach(context: Context) {
		setLocale(context, (context.applicationContext as BaseApp).locale)
		super.onAttach(context)
	}

	override fun onResume() {
		super.onResume()
		updateTitle(title)
		updateAds(hasAds)
	}
}
