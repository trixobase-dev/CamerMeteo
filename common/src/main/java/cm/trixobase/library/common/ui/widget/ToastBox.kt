package cm.trixobase.library.common.ui.widget

import android.content.Context
import android.widget.Toast
import cm.trixobase.library.common.R

/*
 * Powered by Trixobase Enterprise on 10/04/26
 */

class ToastBox {

    private var context: Context

    private constructor(context: Context) {
        this.context = context
    }

    private lateinit var message: String
    private var duration: Int = 0

    class Builder {

        private val instance: ToastBox

        internal constructor(context: Context) {
            instance = ToastBox(context)
            instance.message = context.getString(R.string.warning_empty_message)
            instance.duration = Toast.LENGTH_SHORT
        }

        fun withMessage(message: String) : Builder{
            instance.message = message
            return this
        }

        fun showLong() {
            instance.duration = Toast.LENGTH_LONG
            instance.display()
        }

        fun showShort() {
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
        Toast.makeText(context, message, duration).show()
    }

}