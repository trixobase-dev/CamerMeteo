package cm.trixobase.library.common.ui.widget

import android.content.Context
import android.widget.Toast
import cm.trixobase.library.common.R

/*
 * Powered by Trixobase Enterprise on 10/04/26
 */

class ToastBox {

    private constructor(context: Context) {
        this.context = context
    }

    private var context: Context
    private lateinit var text: String
    private var duration: Int = 0

    class Builder {

        internal constructor(context: Context) {
            instance = ToastBox(context)
            instance.text = context.getString(R.string.warning_empty_message)
            instance.duration = Toast.LENGTH_SHORT
        }

        private val instance: ToastBox

        fun showSoonMessage() {
            instance.text = instance.context.getString(R.string.warning_available_soon)
            instance.duration = Toast.LENGTH_LONG
            instance.display()
        }

        fun showLong(message: String) {
            instance.text = message
            instance.duration = Toast.LENGTH_LONG
            instance.display()
        }

        fun showShort(message: String) {
            instance.text = message
            instance.duration = Toast.LENGTH_SHORT
            instance.display()
        }

    }

    companion object {

        fun builder(context: Context): Builder {
            return Builder(context)
        }

    }

    private fun display() {
        Toast.makeText(context, text, duration).show()
    }

}