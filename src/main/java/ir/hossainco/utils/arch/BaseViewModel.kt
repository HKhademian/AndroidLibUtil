package ir.hossainco.utils.arch

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import ir.hossainco.utils.event.LiveEvent
import ir.hossainco.utils.event.MutableLiveEvent
import ir.hossainco.utils.event.postEvent

abstract class BaseViewModel : ViewModel() {
	private val _error = MutableLiveEvent<Throwable>()
	private val _contextTask = MutableLiveEvent<(Context) -> Unit>()
	private val _activityTask = MutableLiveEvent<(Activity) -> Unit>()

	val error: LiveEvent<Throwable> get() = _error
	val contextTask: LiveEvent<(Context) -> Unit> get() = _contextTask
	val activityTask: LiveEvent<(Activity) -> Unit> get() = _activityTask

	protected fun postError(error: Throwable) =
		_error.postEvent(error)

	protected fun postContextTask(task: (Context) -> Unit) =
		_contextTask.postEvent(task)

	protected fun postActivityTask(task: (Activity) -> Unit) =
		_activityTask.postEvent(task)
}
