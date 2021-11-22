package com.example.mangopos.di

import android.content.Context
import android.content.SharedPreferences
import coil.ImageLoader
import com.example.mangopos.R
import com.example.mangopos.data.api.ktorHttpClient
import com.example.mangopos.data.repository.AuthRepository
import com.example.mangopos.data.repository.InvoicesRepository
import com.example.mangopos.data.repository.MenuRepository
import com.example.mangopos.data.repository.OrderRepository
import com.example.mangopos.utils.dispatcher.DispatcherImplt
import com.example.mangopos.utils.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

import io.ktor.client.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider{
        return DispatcherImplt()
    }


    @Singleton
    @Provides
    fun provideSharedPref(
        @ApplicationContext appContext : Context
    ): SharedPreferences {
        return appContext.getSharedPreferences(
            R.string.preference_file_key.toString(), Context.MODE_PRIVATE
        )
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        api:HttpClient
    ) = AuthRepository(api)

    @Singleton
    @Provides
    fun provideInvoicesRepository(
        api:HttpClient
    ) = InvoicesRepository(api)

    @Singleton
    @Provides
    fun provideMenuRepository(
        api:HttpClient
    ) = MenuRepository(api)

    @Singleton
    @Provides
    fun providePaymentRepository(
        api:HttpClient
    ) = OrderRepository(api)




    @Singleton
    @Provides
    fun provideApi(): HttpClient{
        return ktorHttpClient
    }



    @Singleton
    @Provides
    fun provideCoilImageLoader(
        @ApplicationContext appContext: Context
    ): ImageLoader {
        return ImageLoader.Builder(appContext)
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .build()
    }
}

