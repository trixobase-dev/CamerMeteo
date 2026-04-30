package cm.trixobase.camermeteo.data.di

/*
 * Powered by Trixobase Enterprise on 23/04/26
 */

sealed class Network<T>(
    val data: T? = null,
    val error: String = "No error"
) {
    class Success<T>(data: T?) : Network<T>(data = data)
    class Error<T>(error: String) : Network<T>(error = error)
}