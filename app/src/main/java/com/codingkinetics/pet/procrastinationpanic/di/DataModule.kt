package com.codingkinetics.pet.procrastinationpanic.di

import com.codingkinetics.pet.procrastinationpanic.pet.data.sources.PetDataLocalSource
import com.codingkinetics.pet.procrastinationpanic.pet.data.sources.PetLocalSource
import com.codingkinetics.pet.procrastinationpanic.tasks.data.sources.TaskDataLocalSource
import com.codingkinetics.pet.procrastinationpanic.tasks.data.sources.TaskLocalSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun provideTaskLocalDataSource(
        taskLocalSource: TaskDataLocalSource
    ): TaskLocalSource

    @Binds
    @Singleton
    abstract fun providePetLocalDataSource(
        petLocalSource: PetDataLocalSource
    ): PetLocalSource
}