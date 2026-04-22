package cm.trixobase.library.common.widget

import android.content.Context
import cm.trixobase.library.common.R

/*
 * Powered by Trixobase Enterprise on 10/04/26
 */

class SnakeBox {

    private constructor(context: Context)

    private lateinit var message: String

    class Builder {

        private val instance: SnakeBox

        internal constructor(context: Context) {
            instance = SnakeBox(context)
            instance.message = context.getString(R.string.warning_empty_message)
        }

        fun withMessage(message: String): Builder {
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

    }

}