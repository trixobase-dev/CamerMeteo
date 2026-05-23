package cm.trixobase.library.common.ui.widget

import android.content.Context

/*
 * Powered by Trixobase Enterprise on 10/04/26
 */

class SnakeBox {

    private constructor(context: Context) {
        this.context = context
    }

    private var context: Context
    private var message = ""

    class Builder {

        internal constructor(context: Context) {
            instance = SnakeBox(context)
        }

        private val instance: SnakeBox

        fun showShort(message: String) {
            instance.message = message
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