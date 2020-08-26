package com.dev.railian.mvvmtemplate

import android.app.Application
import com.dev.railian.mvvmtemplate.architecture.prefs.ContextKeeper
import com.dev.railian.mvvmtemplate.data.network.NetworkModule
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader
import com.google.firebase.crashlytics.FirebaseCrashlytics
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ApplicationDelegate : Application() {
    override fun onCreate() {
        super.onCreate()
//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        ContextKeeper.appContext = this
        SoLoader.init(this, false)
        val networkFlipperPlugin = NetworkFlipperPlugin()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ApplicationDelegate)

            modules(
                NetworkModule.newInstance(networkFlipperPlugin)
            )
        }

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            AndroidFlipperClient.getInstance(this).apply {
                addPlugin(
                    InspectorFlipperPlugin(
                        this@ApplicationDelegate,
                        DescriptorMapping.withDefaults()
                    )
                )
                addPlugin(SharedPreferencesFlipperPlugin(this@ApplicationDelegate))
                addPlugin(CrashReporterPlugin.getInstance())
                addPlugin(networkFlipperPlugin)
                addPlugin(DatabasesFlipperPlugin(this@ApplicationDelegate))
            }.start()
        }
    }
}