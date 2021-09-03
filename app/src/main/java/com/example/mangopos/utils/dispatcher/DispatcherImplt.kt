package com.example.mangopos.utils.dispatcher


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers



class DispatcherImplt : DispatcherProvider {
    override val main: CoroutineDispatcher
        get() = Dispatchers.Main
    override val io: CoroutineDispatcher
        get() = Dispatchers.IO
    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
    override val unconfined: CoroutineDispatcher
        get() = Dispatchers.Unconfined

}