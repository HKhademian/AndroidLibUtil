@file:Suppress("UNCHECKED_CAST")

package ir.hossainco.utils.arch

import android.util.Log
import androidx.lifecycle.LiveData
import ir.hossainco.utils.arch.liveDatas.mutableLiveData

class LiveMutableList<T>(
	private val list: MutableList<T> = mutableListOf(),
	init: LiveMutableList<T>.() -> Unit = {}
) : AbstractMutableList<T>() {
	private var freeze = false
	private val _liveData = mutableLiveData<MutableList<T>>(this)

	val mutableLiveData: LiveData<MutableList<T>> = _liveData
	val liveData = _liveData as LiveData<List<T>>

	init {
		init()
		update()
	}

	fun update() = update {}
	fun <T> update(block: () -> T): T {
		val res = block.invoke()
		if (!freeze) try {
			_liveData.postValue(this)
		} catch (ex: Exception) {
			ex.printStackTrace()
			Log.e(LiveMutableList::class.java.simpleName, ex.message)
		}
		return res
	}


	override val size get() = list.size
	override operator fun get(index: Int) = list[index]

	override fun add(index: Int, element: T) = update {
		list.add(index, element)
	}

	override fun removeAt(index: Int): T = update {
		list.removeAt(index)
	}

	override operator fun set(index: Int, element: T): T = update {
		list.set(index, element)
	}

	override fun clear() = update {
		list.clear()
	}

	fun replace(elements: Collection<T>) = update {
		list.clear()
		list += elements
	}

	operator fun component1() = liveData
	operator fun component2() = this

	fun freeze(updateAtLast: Boolean = false, block: LiveMutableList<T>.() -> Unit) = try {
		freeze = true
		block()
		freeze = false
		if (updateAtLast) update()
		Unit
	} finally {
		freeze = false
	}

	fun merge(item: T, updateAtLast: Boolean = true, predicate: (T) -> Boolean = { false }) = freeze(updateAtLast) {
		list.removeAll(predicate)
		list += item
	}

	fun merge(items: Iterable<T>, updateAtLast: Boolean = true, predicate: (T) -> Boolean = { false }) =
		freeze(updateAtLast) {
			list.removeAll(predicate)
			list += items
		}

	fun source(items: Iterable<T>, updateAtLast: Boolean = true) = freeze(updateAtLast) {
		list.clear()
		list += items
	}
}
