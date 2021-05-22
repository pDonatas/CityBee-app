package com.example.mainactivity.data

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    val error: String? = null

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
        }
    }
}