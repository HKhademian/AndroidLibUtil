@file:JvmName("LiveDataUtils")
@file:JvmMultifileClass
@file:Suppress("PackageDirectoryMismatch")

package ir.hossainco.utils.arch.liveDatas

import androidx.lifecycle.*
import ir.hossainco.utils.Quadruple


typealias LiveList<T> = LiveData<List<T>>

typealias MutableLiveList<T> = MutableLiveData<List<T>>


fun <T> hiddenMutableLiveData(): LiveData<T> =
	MutableLiveData<T>()

fun <T> hiddenMutableLiveData(init: T): LiveData<T> =
	mutableLiveData(init)

fun <T> hiddenMutableLiveData(init: MutableLiveData<T>.() -> Unit): LiveData<T> =
	mutableLiveData(init)

fun <T> mutableLiveData(init: T) =
	MutableLiveData<T>().apply { postValue(init) }

fun <T> mutableLiveData(init: MutableLiveData<T>.() -> Unit) =
	MutableLiveData<T>().apply(init)


fun <T, R> LiveData<T>.map(mapper: (T) -> R): LiveData<R> =
	Transformations.map(this, mapper)
//  fun <A, B, R> LiveData<Pair<A, B>>.map(mapper: (A, B) -> R): LiveData<R> =
//    Transformations.map(this) { (a, b) -> mapper(a, b) }
//  fun <A, B, C, R> LiveData<Triple<A, B, C>>.map(mapper: (A, B, C) -> R): LiveData<R> =
//    Transformations.map(this) { (a, b, c) -> mapper(a, b, c) }

fun <T> merge(vararg liveData: LiveData<T>): LiveData<T> =
	liveData.foldRight(MediatorLiveData<T>()) { it, mediator ->
		mediator.apply {
			addSource(it) { value -> mediator.value = value }
		}
	}

inline fun <T> LiveData<T>.observe(owner: LifecycleOwner, crossinline observer: (T) -> Unit) =
	observe(owner, Observer { observer(it!!) })

inline fun <T> LiveData<T>.observe(owner: LifecycleOwner, default: T, crossinline observer: (T) -> Unit) =
	observe(owner, Observer { observer(it ?: default) })


inline fun <T> LiveData<T>.firstObserve(owner: LifecycleOwner, crossinline observer: (T) -> Unit) {
	observe(owner, Observer { observer(it!!) })
	observer(value!!)
}

inline fun <T> LiveData<T>.firstObserve(owner: LifecycleOwner, default: T, crossinline observer: (T) -> Unit) {
	observe(owner, Observer { observer(it ?: default) })
	observer(value ?: default)
}

inline fun <T> LiveData<T>.withObserve(owner: LifecycleOwner, crossinline observer: (T?, Observer<T?>) -> Unit) =
	observe(owner, object : Observer<T?> {
		override fun onChanged(t: T?) =
			observer(t, this)
	})


inline fun <T> LiveData<T>.singleObserve(owner: LifecycleOwner, crossinline observer: (T) -> Unit) =
	withObserve(owner) { t, baseObserver ->
		observer(t!!)
		removeObserver(baseObserver)
	}


inline fun <T> LiveData<T>.singleObserve(owner: LifecycleOwner, default: T, crossinline observer: (T) -> Unit) =
	withObserve(owner) { t, baseObserver ->
		observer(t ?: default)
		removeObserver(baseObserver)
	}


fun <T> LiveData<T>.distinct(): LiveData<T> {
	val mediatorLiveData: MediatorLiveData<T> = MediatorLiveData()
	mediatorLiveData.addSource(this) {
		if (it != mediatorLiveData.value)
			mediatorLiveData.value = it
	}
	return mediatorLiveData
}

/** https://medium.com/@gauravgyal/combine-results-from-multiple-async-requests-90b6b45978f7 */
fun <A, B> zip(a: LiveData<A>, b: LiveData<B>): LiveData<Pair<A, B>> {
	return MediatorLiveData<Pair<A, B>>().apply {
		var lastA: A? = null
		var lastB: B? = null

		fun update() {
			val localLastA = lastA
			val localLastB = lastB
			if (localLastA != null && localLastB != null)
				this.value = localLastA to localLastB
		}

		addSource(a) {
			lastA = it
			update()
		}
		addSource(b) {
			lastB = it
			update()
		}
	}
}

fun <A, B, C> zip(a: LiveData<A>, b: LiveData<B>, c: LiveData<C>): LiveData<Triple<A, B, C>> {
	return MediatorLiveData<Triple<A, B, C>>().apply {
		var lastA: A? = null
		var lastB: B? = null
		var lastC: C? = null

		fun update() {
			val localLastA = lastA
			val localLastB = lastB
			val localLastC = lastC
			if (localLastA != null && localLastB != null && localLastC != null)
				this.value = Triple(localLastA, localLastB, localLastC)
		}

		addSource(a) {
			lastA = it
			update()
		}
		addSource(b) {
			lastB = it
			update()
		}
		addSource(c) {
			lastC = it
			update()
		}
	}
}

fun <A, B, C, D> zip(a: LiveData<A>, b: LiveData<B>, c: LiveData<C>, d: LiveData<D>): LiveData<Quadruple<A, B, C, D>> {
	return MediatorLiveData<Quadruple<A, B, C, D>>().apply {
		var lastA: A? = null
		var lastB: B? = null
		var lastC: C? = null
		var lastD: D? = null

		fun update() {
			val localLastA = lastA
			val localLastB = lastB
			val localLastC = lastC
			val localLastD = lastD
			if (localLastA != null && localLastB != null && localLastC != null && localLastD != null)
				this.value = (localLastA to localLastB) to (localLastC to localLastD)
		}

		addSource(a) {
			lastA = it
			update()
		}
		addSource(b) {
			lastB = it
			update()
		}
		addSource(c) {
			lastC = it
			update()
		}
		addSource(d) {
			lastD = it
			update()
		}
	}
}
