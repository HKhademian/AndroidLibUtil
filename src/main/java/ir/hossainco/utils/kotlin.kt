package ir.hossainco.utils

inline fun <T> tryOrDefault(default: T, crossinline action: () -> T) = try {
	action()
} catch (_: Throwable) {
	default
}

inline fun <T> tryOrNull(crossinline action: () -> T) = try {
	action()
} catch (_: Throwable) {
	null
}

