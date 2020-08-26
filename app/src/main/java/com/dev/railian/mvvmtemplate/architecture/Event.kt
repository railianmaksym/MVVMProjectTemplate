package com.dev.railian.mvvmtemplate.architecture

data class Event<out T>(val status: Status, val data: T?, val error: Throwable?) {

    companion object {
        fun <T> loading(): Event<T> {
            return Event(Status.LOADING, null, null)
        }

        fun <T> success(data: T?): Event<T> {
            return Event(Status.SUCCESS, data, null)
        }

        fun <T> error(exception: Throwable?): Event<T> {
            return Event(Status.ERROR, null, exception)
        }
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}