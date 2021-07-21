/*
 * (C) Copyright IBM Deutschland GmbH 2021
 * (C) Copyright IBM Corp. 2021
 */

package de.rki.covpass.commonapp

import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.LayoutRes
import de.rki.covpass.commonapp.dependencies.commonDeps
import de.rki.covpass.sdk.annotationslib.Continue
import de.rki.covpass.sdk.navigation.Navigator
import de.rki.covpass.sdk.navigation.NavigatorOwner
import de.rki.covpass.sdk.utilslib.BaseHookedActivity
import de.rki.covpass.sdk.utilslib.isDebuggable

/** Common base activity with some common functionality like error handling or loading behaviour. */
public abstract class BaseActivity(@LayoutRes contentLayoutId: Int = 0) :
    BaseHookedActivity(contentLayoutId = contentLayoutId),
    NavigatorOwner {

    override val navigator: Navigator = Navigator()

    override fun onCreate(savedInstanceState: Bundle?) {
        if (!isDebuggable) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            )
        }
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        if (navigator.onBackPressed() == Continue) {
            super.onBackPressed()
        }
    }

    override fun onError(error: Throwable) {
        commonDeps.errorHandler.handleError(error, supportFragmentManager)
    }

    override fun setLoading(isLoading: Boolean) {}
}
