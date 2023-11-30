package com.juanroig.composecourse.di

import com.juanroig.composecourse.BuildConfig
import com.juanroig.composecourse.di.qualifiers.AppID
import com.juanroig.composecourse.di.qualifiers.AppVersionName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    @AppID
    fun provideApplicationName(): String {
        return BuildConfig.APPLICATION_ID
    }

    @Provides
    @Singleton
    @AppVersionName
    fun provideApplicationVersion(): String {
        return BuildConfig.VERSION_NAME
    }
}