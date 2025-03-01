package com.codingkinetics.pet.procrastinationpanic.di

import com.codingkinetics.pet.procrastinationpanic.tasks.data.repository.TaskRepository
import com.codingkinetics.pet.procrastinationpanic.tasks.data.repository.TaskRepositoryImpl
import com.codingkinetics.pet.procrastinationpanic.util.CoroutineContextProvider
import com.codingkinetics.pet.procrastinationpanic.util.CoroutineContextProviderImpl
import com.codingkinetics.pet.procrastinationpanic.util.Logger
import com.codingkinetics.pet.procrastinationpanic.util.TimberLogger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Singleton
    @Binds
    abstract fun bindsCoroutineContextProvider(coroutineContextProvider: CoroutineContextProviderImpl): CoroutineContextProvider

    @Singleton
    @Binds
    abstract fun bindsLogger(logger: TimberLogger): Logger

    @Singleton
    @Binds
    abstract fun bindsTaskRepository(taskRepository: TaskRepositoryImpl): TaskRepository
}
