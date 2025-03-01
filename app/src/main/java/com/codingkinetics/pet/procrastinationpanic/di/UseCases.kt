package com.codingkinetics.pet.procrastinationpanic.di

import com.codingkinetics.pet.procrastinationpanic.pet.domain.usecase.CalculatePetStatusUseCase
import com.codingkinetics.pet.procrastinationpanic.pet.domain.usecase.PetStatusUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCases {
    @Binds
    abstract fun bindsCalculatePetStatusUseCase(calculatePetStatusUseCase: CalculatePetStatusUseCase): PetStatusUseCase
}
