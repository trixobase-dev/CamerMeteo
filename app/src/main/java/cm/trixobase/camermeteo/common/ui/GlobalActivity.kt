@file:Suppress("unused")

package cm.trixobase.camermeteo.common.ui

import androidx.activity.ComponentActivity
import cm.trixobase.camermeteo.R
import cm.trixobase.camermeteo.common.widget.SnakeBox
import cm.trixobase.camermeteo.common.widget.ToastBox

abstract class GlobalActivity : ComponentActivity() {

    open fun showAvailableSoon() {
        ToastBox.builder(applicationContext).withMessage(getString(R.string.warning_available_soon))
            .showLong()
    }

    open fun showError(message: String) {
        SnakeBox.builder(applicationContext).withMessage(message).showShort()
    }

    open fun showMessage(message: String) {
        ToastBox.builder(applicationContext).withMessage(message).showShort()
    }

}