package cm.trixobase.camermeteo.common.widget

import android.content.Context
import android.widget.Toast
import cm.trixobase.camermeteo.R

/*
 * Powered by Trixobase Enterprise on 10/04/26
 */

class ToastBox {

    private var context: Context

    private constructor(context: Context) {
        this.context = context
    }

    private lateinit var message: String

    class Builder {

        private val instance: ToastBox

        internal constructor(context: Context) {
            instance = ToastBox(context)
            instance.message = context.getString(R.string.warning_empty_message)
        }

        fun withMessage(message: String) : Builder{
            instance.message = message
            return this
        }

        fun showShort() {
            instance.displayShort()
        }

    }

    companion object {

        fun builder(context: Context): Builder {
            return Builder(context)
        }

    }

    private fun displayShort() {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}