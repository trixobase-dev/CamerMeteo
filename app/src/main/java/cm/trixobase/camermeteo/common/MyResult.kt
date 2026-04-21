package cm.trixobase.camermeteo.common

/*
 * Powered by Trixobase Enterprise on 20/04/26
 */

sealed class MyResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?): MyResult<T>(data)
    class Error<T>(message: String, data: T? = null): MyResult<T>(data, message)
    class Loading<T>: MyResult<T>()
}