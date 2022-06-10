package com.example.animelist.di.hilt

import com.example.animelist.api.AnimeRepository
import com.example.animelist.api.AnimeRepositoryImpl
import com.example.animelist.api.CrunchyRoll
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltModule {

    @Singleton
    @Provides
    fun provideCrunchyRoll(): CrunchyRoll =
        Retrofit.Builder()
            .baseUrl(CrunchyRoll.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
            .create(CrunchyRoll::class.java)

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
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

    @Singleton
    @Provides
    fun provideRepository(): AnimeRepository = AnimeRepositoryImpl(provideCrunchyRoll())

    @Singleton
    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}