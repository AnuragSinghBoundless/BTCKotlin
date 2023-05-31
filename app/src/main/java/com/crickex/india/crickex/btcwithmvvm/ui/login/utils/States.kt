package com.crickex.india.crickex.btcwithmvvm.ui.login.utils


sealed class States<T>(val data: T? = null, val errorMessage: String? = null) {
    class LOADING<T> : States<T>()
    class FAILURE<T>(errorMessage: String?) : States<T>(errorMessage = errorMessage)
    class SUCCESS<T>(data: T? = null) : States<T>(data = data)

}
