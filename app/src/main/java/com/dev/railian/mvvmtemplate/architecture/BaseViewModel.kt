package com.dev.railian.mvvmtemplate.architecture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import retrofit2.Response

abstract class BaseViewModel : ViewModel() {

    protected val _messages = SingleLiveEvent<Int>()
    val messages: LiveData<Int>
        get() = _messages

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun <T> requestWithLiveData(
        liveData: MutableLiveData<Event<T>>,
        request: suspend () -> Response<T>
    ) {

        liveData.postValue(Event.loading())
        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = request.invoke()
                if (response.code() == 200 && response.isSuccessful) {
                    liveData.postValue(Event.success(response.body()))
                } else {
                    liveData.postValue(Event.error(null))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                liveData.postValue(Event.error(e))
            }
        }
    }

    fun <T> requestWithCallback(
        request: suspend () -> Response<T>,
        response: (Event<T>) -> Unit
    ) {

        response(Event.loading())
        this.viewModelScope.launch(Dispatchers.IO) {
            try {
                val res = request.invoke()
                launch(Dispatchers.Main) {
                    if (res.code() == 200 && res.isSuccessful) {
                        response(Event.success(res.body()))
                    } else {
                        response(Event.error(null))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    response(Event.error(e))
                }
            }
        }
    }

    fun <T> requestWithSuspendCallback(
        request: suspend () -> Response<T>,
        response: suspend (Event<T>) -> Unit
    ) {

        this.viewModelScope.launch(Dispatchers.IO) {
            response(Event.loading())
            try {
                val res = request.invoke()
                launch(Dispatchers.Main) {
                    if (res.code() == 200 && res.isSuccessful) {
                        response(Event.success(res.body()))
                    } else {
                        response(Event.error(null))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    response(Event.error(e))
                }
            }
        }
    }
}