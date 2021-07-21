/*
 * (C) Copyright IBM Deutschland GmbH 2021
 * (C) Copyright IBM Corp. 2021
 */

package de.rki.covpass.sdk.navigation

import android.app.Activity
import androidx.fragment.app.Fragment
import de.rki.covpass.sdk.annotationslib.Abortable

/** Executes a back button press. */
public fun Fragment.triggerBackPress() {
    requireActivity().onBackPressed()
}

/** Executes a back button press. */
public fun Activity.triggerBackPress() {
    onBackPressed()
}

/**
 * Interface for fragments wanting to handle the system back button press.
 */
public interface OnBackPressedNavigation {
    public fun onBackPressed(): Abortable
}
