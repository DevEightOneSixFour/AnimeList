package com.example.animelist.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.animelist.api.AnimeRepositoryImpl
import com.example.animelist.api.CrunchyRoll
import com.example.animelist.viewmodel.AnimeViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object DI {
    private val service: CrunchyRoll =
        Retrofit.Builder()
            .baseUrl(CrunchyRoll.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
            .create(CrunchyRoll::class.java)

    private fun provideRepository() = AnimeRepositoryImpl(service)

    fun provideViewModel(owner: ViewModelStoreOwner): AnimeViewModel {
       return ViewModelProvider(owner, object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AnimeViewModel(provideRepository(), providesIODispatcher()) as T
            }
        })[AnimeViewModel::class.java]
    }

    // Singleton -> to create and only have one instance of a class/object
    // Factory -> to dynamically produce classes or object of different types
    /*
        FreeUser vs PremiumUser
        data class FreeUser(val freeStuff: FreeStuff, val hinder: Hinder)
        data class PremiumUser(val daGoodStuff: GoodStuff)

        class Factory(val userType: UserType) {
            fun createUser(userType): User {
                when(userType)
                    is free -> return FreeUser()
                    is premium -> return PremiumUser()
            }
        }
     */

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                Interceptor { chain ->
                    val builder = chain.request().newBuilder()
                    builder.header(CrunchyRoll.CLIENT_KEY, CrunchyRoll.KEY_VALUE)
                    return@Interceptor chain.proceed(builder.build())
                }
            )
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    /*
    Network Status Codes
        200 -> Ok, call was successful
        300 -> Redirect, endpoint goes to another endpoint
        400 -> Client-side error
            data not matching the backend
            not authorized
            endpoint is incorrect
        500 -> Server-side error
            Server is down
     */

}