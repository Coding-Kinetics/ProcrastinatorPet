package com.codingkinetics.pet.procrastinationpanic.pet.domain.usecase

import com.codingkinetics.pet.procrastinationpanic.pet.domain.PetState
import com.codingkinetics.pet.procrastinationpanic.tasks.domain.Task
import com.codingkinetics.pet.procrastinationpanic.util.Result
import javax.inject.Inject

interface PetStatusUseCase {
    operator fun invoke(tasks: List<Task>): PetState
}

/**
 * Go through tasks to see if any tasks are overdue or tasks are
 * close to being overdue, lots of timer functions to work with here
 */
class CalculatePetStatusUseCase @Inject constructor() : PetStatusUseCase {
    override fun invoke(tasks: List<Task>): PetState = calculate(tasks)

    private fun calculate(tasks: List<Task>): PetState {
        // TODO calculate based on
        return PetState.Happy
    }
}
