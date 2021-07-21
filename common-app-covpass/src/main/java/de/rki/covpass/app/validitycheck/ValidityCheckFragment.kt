/*
 * (C) Copyright IBM Deutschland GmbH 2021
 * (C) Copyright IBM Corp. 2021
 */

package de.rki.covpass.app.validitycheck

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import de.rki.covpass.app.R
import de.rki.covpass.app.databinding.ValidityCheckPopupContentBinding
import de.rki.covpass.commonapp.BaseBottomSheet
import de.rki.covpass.sdk.navigation.FragmentNav
import de.rki.covpass.sdk.navigation.findNavigator
import de.rki.covpass.sdk.utilslib.viewBinding
import kotlinx.parcelize.Parcelize

@Parcelize
internal class ValidityCheckFragmentNav : FragmentNav(ValidityCheckFragment::class)

/**
 * Fragment to check the validity of all certificates for the selected country and date
 */
// FIXME This is just a placeholder for now, implement this
internal class ValidityCheckFragment : BaseBottomSheet() {

    override val buttonTextRes = R.string.certificate_add_popup_scan_button_title

    init {
        viewBinding(ValidityCheckPopupContentBinding::inflate)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomSheetBinding.bottomSheetTitle.text = "Validity Check TBD"
    }

    override fun onActionButtonClicked() {
        findNavigator().pop()
    }
}
