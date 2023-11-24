package com.example.decathlon.di

import com.example.data.data.repositoryImpl.DecathlonRepoImpl
import com.example.domain.domain.repository.DecathlonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {


    @Binds
    abstract fun bindDecathlonRepo(decathlonRepoImpl: DecathlonRepoImpl): DecathlonRepository

}