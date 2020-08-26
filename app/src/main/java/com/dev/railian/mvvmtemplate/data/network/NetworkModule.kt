package com.dev.railian.mvvmtemplate.data.network

import com.dev.railian.mvvmtemplate.BuildConfig
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    private const val TIMEOUT_TIME = 10L

    private fun provideHttpClient(
        networkFlipperPlugin: NetworkFlipperPlugin
    ): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_TIME, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor())
            .addNetworkInterceptor(FlipperOkhttpInterceptor(networkFlipperPlugin))

        return httpClientBuilder.build()
    }

    private fun provideRetrofit(client: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.Hostname)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun newInstance(networkFlipperPlugin: NetworkFlipperPlugin): Module {
        return module {
            factory { provideHttpClient(networkFlipperPlugin) }
            factory { provideRetrofit(get()) }
            // YOUR RETROFIT INTERFACE
        }
    }
}