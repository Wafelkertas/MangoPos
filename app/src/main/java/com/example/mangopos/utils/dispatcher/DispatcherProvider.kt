package com.example.mangopos.utils.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val main : CoroutineDispatcher
    val io : CoroutineDispatcher
    val default : CoroutineDispatcher
    val unconfined : CoroutineDispatcher
}