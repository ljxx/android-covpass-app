/*
 * (C) Copyright IBM Deutschland GmbH 2021
 * (C) Copyright IBM Corp. 2021
 */

package de.rki.covpass.commonapp

import android.app.Application
import android.webkit.WebView
import androidx.fragment.app.FragmentActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import de.rki.covpass.sdk.cert.toTrustedCerts
import de.rki.covpass.sdk.dependencies.SdkDependencies
import de.rki.covpass.sdk.dependencies.sdkDeps
import de.rki.covpass.sdk.httplib.HttpLogLevel
import de.rki.covpass.sdk.httplib.httpConfig
import de.rki.covpass.sdk.logginglib.Lumber
import de.rki.covpass.sdk.navigation.*
import de.rki.covpass.sdk.securityproviderlib.initSecurityProvider
import de.rki.covpass.sdk.utils.DSC_UPDATE_INTERVAL_HOURS
import de.rki.covpass.sdk.utils.DscListUpdater
import de.rki.covpass.sdk.utilslib.AndroidDependencies
import de.rki.covpass.sdk.utilslib.androidDeps
import de.rki.covpass.sdk.utilslib.isDebuggable
import java.util.concurrent.TimeUnit

/** Common base application with some common functionality like setting up logging. */
public abstract class CommonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // IMPORTANT: The security provider has to be initialized before anything else
        initSecurityProvider()

        if (isDebuggable) {
            Lumber.plantDebugTreeIfNeeded()
            httpConfig.enableLogging(HttpLogLevel.HEADERS)
            WebView.setWebContentsDebuggingEnabled(true)
        }

        navigationDeps = object : NavigationDependencies() {
            override val application = this@CommonApplication
            override val defaultScreenOrientation = Orientation.PORTRAIT
            override val animationConfig = DefaultNavigationAnimationConfig(250)
        }
        androidDeps = object : AndroidDependencies() {
            private val activityNavigator = ActivityNavigator()

            override val application: Application = this@CommonApplication

            override fun currentActivityOrNull(): FragmentActivity? =
                activityNavigator.getCurrentActivityOrNull() as? FragmentActivity
        }
        sdkDeps = object : SdkDependencies() {
            override val application: Application = this@CommonApplication
        }
    }

    public fun start() {
        sdkDeps.validator.updateTrustedCerts(sdkDeps.dscRepository.dscList.value.toTrustedCerts())

        val tag = "dscListWorker"
        val dscListWorker: PeriodicWorkRequest =
            PeriodicWorkRequest.Builder(DscListUpdater::class.java, DSC_UPDATE_INTERVAL_HOURS, TimeUnit.HOURS)
                .addTag(tag)
                .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            tag,
            ExistingPeriodicWorkPolicy.KEEP,
            dscListWorker,
        )
    }
}
