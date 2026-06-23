package cm.trixobase.camermeteo.domain

import android.app.Notification
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import cm.trixobase.camermeteo.App
import cm.trixobase.camermeteo.data.repository.WeatherRepository
import cm.trixobase.library.common.domain.RequestResult
import cm.trixobase.library.common.ui.widget.ToastBox
import cm.trixobase.library.common.utils.NotificationProcess
import cm.trixobase.library.common.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/*
 * Powered by Trixobase Enterprise on 15/06/26
 */

class MyWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        showLog("Work start")
        var notification: Notification? = null

        if(Utils.phone.hasInternet(applicationContext)) {
            showLog("Request start")
            WeatherRepository().getWeather(
                context = applicationContext,
            ).collect { result ->
                if (result is RequestResult.Success) {
                    showLog("Request end with data: [${result.data}]")
                    notification = App.getWeatherNotification(
                        context = applicationContext,
                        weather = result.data!!
                    )
                } else {
                    showLog("Request end with error: ${result.error}")
                    notification = null
                }
            }
        } else {
            showLog("Request can't start without internet")
            withContext(Dispatchers.Main) {
                ToastBox.builder(applicationContext).showShort("CamerMétéo n'accède pas à internet")
            }
        }

        notification?.let {
            withContext(Dispatchers.Main) {
                NotificationProcess.builder(applicationContext).notify(it) }
        }
        showLog("Work end")
        return Result.success()
    }

    private fun showLog(error: String) {
        Utils.process.showLog("MyWorker", "doWork", error)
    }

}