package com.yasser.bosta_task.Utils

sealed  class UiStatus<out T> {

    data class Success<T>(val info:T): UiStatus<T>()
    class Loading<T>(): UiStatus<T>()
    class NetworkConnectionFailed<T>(val message:String): UiStatus<T>()
    class Failed<T>(val message:String): UiStatus<T>()

}