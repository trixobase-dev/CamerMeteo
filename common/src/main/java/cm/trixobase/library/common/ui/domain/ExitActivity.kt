package cm.trixobase.library.common.ui.domain

import android.app.Activity
import android.os.Bundle

/*
 * Powered by Trixobase Enterprise on 06/04/26
 */

class ExitActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        finish()
        /*
        if (Utils.phone.isLollipop()) {
            finishAndRemoveTask()
        } else if (Utils.phone.isJellyBean()) {
            finishAffinity()
        } else {
            finish()
        }
        */
    }

}