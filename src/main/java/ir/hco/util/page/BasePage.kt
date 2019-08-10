package ir.hco.util.page

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import ir.hco.util.BaseApp
import ir.hco.util.R
import ir.hco.util.StringSource
import ir.hco.util.views.SimpleToolbar
import ir.hossainco.utils.packages.setLocale

open class BasePage : Fragment() {
	open val hasToolbar: Boolean = true
	open val hasMenu: Boolean = true
	open val hasAds = true
	open val title: String? = null

	protected open fun updateTitle(title: String? = this.title) {
		val activity = activity ?: return
		val toolbar = activity.findViewById<SimpleToolbar>(R.id.toolbar) ?: return
		toolbar.subtitle = StringSource.of(title)
	}

	protected open fun updateAds(hasAds: Boolean = this.hasAds) {
		val activity = activity ?: return
		activity.findViewById<View>(R.id.ad)?.isVisible = hasAds
	}

	protected open fun updateToolbar(hasToolbar: Boolean = this.hasToolbar, hasMenu: Boolean = this.hasMenu) {
		val activity = activity ?: return
		val toolbar = activity.findViewById<SimpleToolbar>(R.id.toolbar) ?: return

		toolbar.isVisible = hasToolbar

		if (hasToolbar && hasMenu) {
			val menu = toolbar.inflate(this::onOptionsItemSelected) { menu ->
				onCreateOptionsMenu(menu, activity.menuInflater)
			}
			onOptionsMenuCreated(menu)
		}
	}

	override fun onAttach(context: Context) {
		setLocale(context, (context.applicationContext as BaseApp).locale)
		super.onAttach(context)
	}

	override fun onResume() {
		super.onResume()
		updateTitle(title)
		updateAds(hasAds)
		updateToolbar(hasToolbar, hasMenu)
	}


	// Handle Options Menu

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		activity?.onCreateOptionsMenu(menu)
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		val activity = activity ?: return super.onOptionsItemSelected(item)
		return activity.onOptionsItemSelected(item)
	}

	protected open fun onOptionsMenuCreated(menu: Menu) {
	}


	// Handle keys

	protected open fun onBackKeyPressed() =
		false

	protected open fun onKeyPressed(keyCode: Int, event: KeyEvent) =
		false

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		view.isFocusableInTouchMode = true
		view.requestFocus()
		view.setOnKeyListener { _, keyCode, event ->
			((keyCode == KeyEvent.KEYCODE_BACK && onBackKeyPressed()) || onKeyPressed(keyCode, event))
		}
	}
}
