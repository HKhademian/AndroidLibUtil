@file:JvmName("Fields")
@file:Suppress("PackageDirectoryMismatch")

package ir.hossainco.utils.fields

import java.lang.reflect.Field

fun Field.trySet(obj: Any?, value: Any): Boolean {
	try {
		val accessible = this.isAccessible
		try {
			this.isAccessible = true
		} catch (_: Throwable) {
		}
		this.set(obj, value)
		try {
			this.isAccessible = accessible
		} catch (_: Throwable) {
		}

		return true
	} catch (_: Throwable) {
		try {
			this.set(obj, value)
			return true
		} catch (_: Throwable) {
		}
	}

	return false
}

fun trySetField(clazz: Class<*>, fieldName: String, obj: Any?, value: Any) =
	clazz.tryGetField(fieldName)?.trySet(obj, value) ?: false

fun Class<*>.tryGetField(fieldName: String) = try {
	this.getDeclaredField(fieldName)
} catch (_: Throwable) {
	null
}
