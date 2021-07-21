/*
 * (C) Copyright IBM Deutschland GmbH 2021
 * (C) Copyright IBM Corp. 2021
 */

package de.rki.covpass.commonapp

import androidx.annotation.LayoutRes
import de.rki.covpass.commonapp.dependencies.commonDeps
import de.rki.covpass.sdk.annotationslib.Abortable
import de.rki.covpass.sdk.annotationslib.Continue
import de.rki.covpass.sdk.navigation.NavigatorOwner
import de.rki.covpass.sdk.navigation.OnBackPressedNavigation
import de.rki.covpass.sdk.utilslib.BaseHookedFragment

/** Common base fragment with some common functionality like error handling or loading behaviour. */
public abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0) :
    BaseHookedFragment(contentLayoutId = contentLayoutId),
    OnBackPressedNavigation {

    override fun onBackPressed(): Abortable =
        (this as? NavigatorOwner)?.navigator?.onBackPressed()
            ?: Continue

    override fun onError(error: Throwable) {
        commonDeps.errorHandler.handleError(error, childFragmentManager)
    }

    override fun setLoading(isLoading: Boolean) {}
}
