package com.kaleksandra.collector.di

import com.kaleksandra.collector.domain.CollectionInteractor
import com.kaleksandra.collector.domain.CollectionInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface CollectionModule {
    @Binds
    @ViewModelScoped
    fun provideCollectionInteractor(impl: CollectionInteractorImpl): CollectionInteractor
}