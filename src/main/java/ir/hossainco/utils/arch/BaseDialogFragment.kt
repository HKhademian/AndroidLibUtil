package ir.hossainco.utils.arch

import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer

abstract class BaseDialogFragment : DialogFragment() {
	protected fun observeEvents(viewModel: BaseViewModel) {
		viewModel.contextTask.observe(this, Observer {
			val context = context ?: return@Observer
			val task = it?.take() ?: return@Observer
			task.invoke(context)
		})

		viewModel.activityTask.observe(this, Observer {
			val activity = activity ?: return@Observer
			val task = it?.take() ?: return@Observer
			task.invoke(activity)
		})
	}
}
