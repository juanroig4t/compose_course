package com.juanroig.composecourse.di

import com.juanroig.composecourse.domain.model.core.CustomDispatchers
import com.juanroig.composecourse.domain.model.core.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Dispatcher(CustomDispatchers.IO)
    fun provideIODispatcher() = Dispatchers.IO

    @Provides
    @Dispatcher(CustomDispatchers.DEFAULT)
    fun provideDefaultDispatcher() = Dispatchers.Default

    @Provides
    @Dispatcher(CustomDispatchers.MAIN)
    fun provideMainDispatcher() = Dispatchers.Main

    @Provides
    @Dispatcher(CustomDispatchers.UNCONFINED)
    fun provideUnconfinedDispatcher() = Dispatchers.Unconfined


}