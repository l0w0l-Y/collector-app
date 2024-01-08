package com.kaleksandra.coredata.network.repository

import com.kaleksandra.coredata.network.repository.CollectionRepository
import com.kaleksandra.coredata.network.repository.CollectionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    @ViewModelScoped
    fun provideCollectionRepository(impl: CollectionRepositoryImpl): CollectionRepository
}