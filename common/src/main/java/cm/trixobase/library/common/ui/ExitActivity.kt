package cm.trixobase.library.common.ui

import android.app.Activity
import android.os.Bundle
import cm.trixobase.library.common.utils.Utils

/*
 * Powered by Trixobase Enterprise on 06/04/26
 */

class ExitActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Utils.phone.isLollipop()) {
            finishAndRemoveTask();
        } else if (Utils.phone.isJellyBean()) {
            finishAffinity();
        } else {
            finish();
        }
    }

}