package com.example.decathlon.di

import com.example.data.data.mappers.DecathlonSKUMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideSkuItemMapper(): DecathlonSKUMapper {
        return DecathlonSKUMapper()
    }
}