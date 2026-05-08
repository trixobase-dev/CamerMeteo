package cm.trixobase.library.common.utils

/*
 * Powered by Trixobase Enterprise on 23/04/26
 */

sealed class NetworkResult<T>(
    val data: T? = null,
    val error: String = ""
) {
    class Success<T>(data: T?) : NetworkResult<T>(data = data)
    class Error<T>(error: String) : NetworkResult<T>(error = error)
}