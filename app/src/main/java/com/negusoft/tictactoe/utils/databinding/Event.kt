package com.negusoft.tictactoe.utils.databinding

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * As described in https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150.
 * Used to avoid handling LiveData's events more than once. For error messages and triggers, for example.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write


    /**
     * Handle the content and prevents its use again.
     */
    fun handle(handler: (T) -> Unit) {
        if (!hasBeenHandled) {
            hasBeenHandled = true
            handler(content)
        }
    }

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

/** Trigger a new event with the given value */
fun <T> MutableLiveData<Event<T>>.setEventValue(value: T) = setValue(Event(value))

/** Convenience method to observe the value only if it hasn't been handled before. */
fun <T> LiveData<Event<T>>.observeEvent(owner: LifecycleOwner, observer: Observer<T>) {
    observe(owner, Observer { event ->
        event?.handle { observer.onChanged(it) }
    })
}