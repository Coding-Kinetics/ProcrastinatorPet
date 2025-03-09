package com.codingkinetics.pet.procrastinationpanic.pet.data.sources

import com.codingkinetics.pet.procrastinationpanic.Database
import com.codingkinetics.pet.procrastinationpanic.PetEntity
import com.codingkinetics.pet.procrastinationpanic.tasks.data.sources.TAG
import com.codingkinetics.pet.procrastinationpanic.util.Logger
import com.codingkinetics.pet.procrastinationpanic.util.Result
import javax.inject.Inject
import javax.inject.Named

interface PetLocalSource {
    suspend fun getPet(id: Int): Result<PetEntity>

    suspend fun registerNewPet(pet: PetEntity)

    suspend fun deletePet(id: Int): Result<Boolean>
}

class PetDataLocalSource @Inject constructor(
    @Named("PetDatabase") private val database: Database,
    private val logger: Logger,
): PetLocalSource {
    override suspend fun getPet(id: Int): Result<PetEntity> =
        try {
            val result = database.petQueries.findById(id.toLong()).executeAsOne()
            Result.Success(result)
        } catch (e: Exception) {
            logger.logError(TAG, "Unable to insert task. Cause: $e")
            Result.Failure(e)
        }

    override suspend fun registerNewPet(pet: PetEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePet(id: Int): Result<Boolean> {
        TODO("Not yet implemented")
    }
}