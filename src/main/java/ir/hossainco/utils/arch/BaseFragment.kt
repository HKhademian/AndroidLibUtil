package ir.hossainco.utils.arch

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

abstract class BaseFragment : Fragment() {
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
