package cm.trixobase.camermeteo.ui

import androidx.activity.ComponentActivity
import cm.trixobase.camermeteo.R
import cm.trixobase.camermeteo.common.widget.SnakeBox
import cm.trixobase.camermeteo.common.widget.ToastBox

/*
 * Powered by Trixobase Enterprise on 09/04/26
 */

abstract class GlobalActivity : ComponentActivity() {

    open fun showAvailableSoon() {
        ToastBox.builder(applicationContext).withMessage(getString(R.string.warning_available_soon)).showShort()
    }

    open fun showError(message: String) {
        SnakeBox.builder(applicationContext).withMessage(message).showShort()
    }

    open fun showMessage(message: String) {
        ToastBox.builder(applicationContext).withMessage(message).showShort()
    }

}