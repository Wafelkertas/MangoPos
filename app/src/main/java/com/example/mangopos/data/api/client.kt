package com.example.mangopos.data.api

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.*


private const val TIME_OUT = 60_000L

val ktorHttpClient = HttpClient(OkHttp) {
    engine {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = Level.BODY
        addInterceptor(loggingInterceptor)
    }
    install(JsonFeature) {
        serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(HttpTimeout) {
        requestTimeoutMillis = TIME_OUT
        connectTimeoutMillis = TIME_OUT
        socketTimeoutMillis = TIME_OUT
    }

//    install(Logging) {
//        logger = object : Logger {
//            override fun log(message: String) {
//                Log.v("logger KTOR =>", message)
//            }
//        }
//        level = LogLevel.ALL
//    }
//
//
//    install(ResponseObserver) {
//        onResponse { httpResponse ->
//            Log.d("HTTP Status", "${httpResponse.status.value}")
//        }
//    }
//    install(DefaultRequest) {
////        header(HttpHeaders.ContentType, ContentType.Application.Json)
//    }
}
