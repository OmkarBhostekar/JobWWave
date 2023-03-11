package com.omkarcodes.hackathonstarter.di

import android.content.Context
import android.content.SharedPreferences
import com.google.api.Http
import com.google.gson.Gson
import com.omkarcodes.hackathonstarter.common.Constants
import com.omkarcodes.hackathonstarter.data.network.MainApi
import com.omkarcodes.hackathonstarter.data.network.UspApi
import com.pluto.plugins.network.PlutoInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context) : SharedPreferences {
        return context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideRetrofitClient() : OkHttpClient = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(PlutoInterceptor())
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()


    @Provides
    @Singleton
    fun provideMainApi(retrofit: Retrofit): MainApi = retrofit.create(MainApi::class.java)

    @Provides
    @Singleton
    fun provideUspApi(okHttpClient: OkHttpClient): UspApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://playful-leeward-close.glitch.me")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(UspApi::class.java)
    }


}