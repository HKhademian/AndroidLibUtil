package ir.hco.util.page

import androidx.fragment.app.Fragment

open class BasePage : Fragment() {
	open val title: String? = null

	override fun onResume() {
		super.onResume()
		val title = title
		updateTitle(title)
	}

	protected open fun updateTitle(title: String?) =
		Unit
}
