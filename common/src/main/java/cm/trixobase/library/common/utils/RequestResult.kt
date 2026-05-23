package cm.trixobase.library.common.utils

/*
 * Powered by Trixobase Enterprise on 23/04/26
 */

sealed class RequestResult<T>(
    val data: T? = null,
    val error: String = ""
) {
    class Success<T>(data: T?) : RequestResult<T>(data = data)
    class Error<T>(error: String) : RequestResult<T>(error = error)
}