package cm.trixobase.camermeteo.common.widget

import android.content.Context
import cm.trixobase.camermeteo.R

/*
 * Powered by Trixobase Enterprise on 10/04/26
 */

class DialogBox {

    private constructor(context: Context)

    private lateinit var message: String

    class Builder {

        private val instance: DialogBox

        internal constructor(context: Context) {
            instance = DialogBox(context)
            instance.message = context.getString(R.string.warning_empty_message)
        }

        fun withMessage(message: String) : Builder{
            instance.message = message
            return this
        }

        fun show() {
            instance.display()
        }

    }

    companion object {

        fun builder(context: Context): Builder {
            return Builder(context)
        }

    }

    private fun display() {

    }

}