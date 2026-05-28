package cm.trixobase.library.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

/*
 * Powered by Trixobase Enterprise on 28/05/26
 */

class PictureProcess {

    private constructor(context: Context) {
        this.context = context
    }

    private val context: Context



    class Builder {

        internal constructor(context: Context) {
            this.instance = PictureProcess(context)
        }

        private var instance: PictureProcess

        fun build(): PictureProcess {
            return instance
        }

    }

    companion object {

        fun builder(context: Context): Builder = Builder(context)

        fun convert(context: Context, drawable: Int): Bitmap = BitmapFactory
                .decodeResource(context.resources, drawable)

    }

}