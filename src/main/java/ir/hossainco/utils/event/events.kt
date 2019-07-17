@file:JvmName("EventUtils")
@file:Suppress("NOTHING_TO_INLINE")

package ir.hossainco.utils.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

typealias LiveEvent<T> = LiveData<Event<T>>

typealias MutableLiveEvent<T> = MutableLiveData<Event<T>>

inline fun <T> LiveEvent<T>.takeEvent(owner: LifecycleOwner, crossinline observer: (T) -> Unit) =
	observe(owner, Observer {
		it?.take()?.let(observer)
	})

inline fun <T> LiveEvent<T>.peekEvent(owner: LifecycleOwner, crossinline observer: (T) -> Unit) =
	observe(owner, Observer {
		it?.peek()?.let(observer)
	})

inline fun <T> LiveEvent<T>.observeEvent(owner: LifecycleOwner, crossinline observer: (T) -> Unit) =
	observe(owner, Observer {
		it?.take()?.let(observer)
	})

inline fun <T> MutableLiveEvent<T>.postEvent(content: T) =
	postValue(Event(content))

