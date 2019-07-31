package ir.hossainco.utils

inline fun <T> tri(crossinline action: () -> T, default: (Throwable) -> T) = try {
	action()
} catch (ex: Throwable) {
	default(ex)
}

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

inline fun <T, R> Iterable<T>.tryMap(transform: (T) -> R): List<R?> {
	return map {
		try {
			transform(it)
		} catch (_: Throwable) {
			null
		}
	}
}

