/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 *
 * heavy inspired from:
 * https://medium.com/google-developers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */

package ir.hossainco.utils.event

open class Event<out T>(val content: T) {
	var hasBeenHandled = false; private set

	/** Returns the content and prevents its use again */
	fun take(): T? = synchronized(hasBeenHandled) {
		return if (hasBeenHandled) {
			null
		} else {
			hasBeenHandled = true
			content
		}
	}

	/** Returns the content without any preventions */
	fun peek() =
		content
}
