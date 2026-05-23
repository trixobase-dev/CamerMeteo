@file:Suppress("unused")

package cm.trixobase.library.common.ui.domain

import androidx.activity.ComponentActivity
import cm.trixobase.library.common.ui.widget.SnakeBox
import cm.trixobase.library.common.ui.widget.ToastBox

abstract class GlobalActivity : ComponentActivity() {

    open fun showAvailableSoon() {
        ToastBox.builder(applicationContext).showSoonMessage()
    }

    open fun showError(message: String) {
        SnakeBox.builder(applicationContext).showShort(message)
    }

    open fun showMessage(message: String) {
        ToastBox.builder(applicationContext).showLong(message)
    }

}