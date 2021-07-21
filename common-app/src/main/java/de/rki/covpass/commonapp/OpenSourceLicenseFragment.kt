/*
 * (C) Copyright IBM Deutschland GmbH 2021
 * (C) Copyright IBM Corp. 2021
 */

package de.rki.covpass.commonapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibm.health.common.android.utils.androidDeps
import com.ibm.health.common.android.utils.attachToolbar
import com.ibm.health.common.android.utils.viewBinding
import com.ibm.health.common.navigation.android.FragmentNav
import de.rki.covpass.commonapp.databinding.OpenSourceLicenseBinding
import de.rki.covpass.commonapp.license.OpenSourceLicenseAdapter
import de.rki.covpass.commonapp.license.models.OpenSourceItem
import de.rki.covpass.sdk.utils.readTextAsset
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Parcelize
public class OpenSourceLicenseFragmentNav : FragmentNav(OpenSourceLicenseFragment::class)

/**
 * Displays open source licenses for all app dependencies. The licenses are
 * generated by the plugin https://github.com/jaredsburrows/gradle-license-plugin.
 */
public class OpenSourceLicenseFragment : BaseFragment() {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val binding by viewBinding(OpenSourceLicenseBinding::inflate)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar()
        setupRecyclerView()
    }

    private fun setupActionBar() {
        attachToolbar(binding.openSourceLicenseToolbar)
        (activity as? AppCompatActivity)?.run {
            supportActionBar?.run {
                setDisplayShowTitleEnabled(false)
                setDisplayHomeAsUpEnabled(true)
                setHomeAsUpIndicator(R.drawable.back_arrow)
            }
            binding.openSourceLicenseToolbar.title = getString(R.string.app_information_title_open_source)
        }
    }

    private fun setupRecyclerView() {
        val text: String = androidDeps.application.readTextAsset("open_source_licenses.json").trim()
        val listObj: MutableList<OpenSourceItem> =
            mutableListOf(
                OpenSourceItem(
                    null,
                    getString(R.string.app_information_message_open_source),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                )
            )
        listObj.addAll(json.decodeFromString(text))

        binding.openSourceLicenseRecyclerView.layoutManager = LinearLayoutManager(context)
        OpenSourceLicenseAdapter(this).apply {
            updateList(listObj)
            attachTo(binding.openSourceLicenseRecyclerView)
        }
    }
}
