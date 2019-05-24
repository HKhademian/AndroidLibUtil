//package ir.hossainco.utils.arch
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//
//class LiveBundleList<T, B>(
//	init: LiveBundleList<T, B>.() -> Unit = {}
//) : BundleList<T, B>() {
//	private var freeze = false
//	private val _liveData: MutableLiveData<BundleList<T, B>> = mutableLiveData(this)
//	val liveData: LiveData<BundleList<T, B>> = _liveData
//
//	init {
//		init()
//		update()
//	}
//
//	fun update() = update {}
//	fun <T> update(block: () -> T): T {
//		val res = block.invoke()
//		if (!freeze) try {
//			_liveData.postValue(this)
//		} catch (ex: Exception) {
//			ex.printStackTrace()
//			Log.e(LiveBundleList::class.java.simpleName, ex.message)
//		}
//		return res
//	}
//
//
//	override fun clearBundle(): Unit = update {
//		super.clearBundle()
//	}
//
//	override fun removeBundleAt(position: Int) = update {
//		super.removeBundleAt(position)
//	}
//
//	override fun setBundleAt(position: Int, value: B) = update {
//		super.setBundleAt(position, value)
//	}
//}
