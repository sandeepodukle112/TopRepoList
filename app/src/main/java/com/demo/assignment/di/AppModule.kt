package com.demo.assignment.di

import android.content.Context
import com.demo.assignment.model.remote.Api
import com.demo.assignment.model.remote.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRepoApi(
        @ApplicationContext context: Context,
        remoteDataSource: RemoteDataSource
    ): Api = remoteDataSource.buildApi(context, Api::class.java)
}