package com.codingkinetics.pet.procrastinationpanic.home.ui

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codingkinetics.pet.procrastinationpanic.pet.domain.PetState
import com.codingkinetics.pet.procrastinationpanic.pet.domain.usecase.PetStatusUseCase
import com.codingkinetics.pet.procrastinationpanic.tasks.data.repository.TaskRepository
import com.codingkinetics.pet.procrastinationpanic.tasks.domain.Task
import com.codingkinetics.pet.procrastinationpanic.tasks.domain.TaskPriority
import com.codingkinetics.pet.procrastinationpanic.tasks.domain.TaskState
import com.codingkinetics.pet.procrastinationpanic.util.CoroutineContextProvider
import com.codingkinetics.pet.procrastinationpanic.util.Logger
import com.codingkinetics.pet.procrastinationpanic.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@Immutable
sealed class HomeScreenViewState {
    data object Loading : HomeScreenViewState()

    data class Content(
        val petState: PetState,
        val priorities: List<Task>,
    ) : HomeScreenViewState()

    data class Error(val message: String) : HomeScreenViewState()
}

/**
 * TODO list
 * - implement drag + drop functionality
 * - create a notification screen
 * - input values from "Add New Task" and save to DB
 * - create background service timer function to evaluate whether the pet should panic or nah
 */

const val HOME_SCREEN_VIEWMODEL = "HomeScreenViewModel"

@HiltViewModel
class HomeScreenViewModel
    @Inject
    constructor(
        private val repository: TaskRepository,
        private val calculatePetStatusUseCase: PetStatusUseCase,
        private val logger: Logger,
        private val contextPool: CoroutineContextProvider,
    ) : ViewModel() {
        private val _viewState = MutableLiveData<HomeScreenViewState>(HomeScreenViewState.Loading)
        val viewState: LiveData<HomeScreenViewState> = _viewState

        var task by mutableStateOf(Task())
            private set

        init {
            _viewState.value =
                HomeScreenViewState.Content(
                    petState = PetState.Happy,
                    priorities =
                        listOf(
                            Task(
                                title = "write out complete android app as refresher",
                                description = "write out procrastination panic with the following features",
                                state = TaskState.IN_PROGRESS,
                                priority = TaskPriority.Important,
                                dueDate = LocalDate.now().plusDays(3),
                            ),
                            Task(
                                title = "Droidcon Academy EBook writing",
                                description = "I have 80 days!",
                                state = TaskState.NOT_STARTED,
                                priority = TaskPriority.Normal,
                                dueDate = LocalDate.now().plusDays(80),
                            ),
                        ),
                )
        }

        // TODO for drag-and-drop functionality
        fun moveListItem(
            list: List<Task>,
            fromIndex: Int,
            toIndex: Int,
        ) {
            val res = list.toMutableList().apply { add(toIndex, removeAt(fromIndex)) }
            _viewState.value =
                HomeScreenViewState.Content(
                    petState = PetState.Happy,
                    priorities = res,
                )
        }

        fun updateTaskTitle(input: String) {
            task = task.copy(title = input)
        }

        fun updateTaskDescription(input: String) {
            task = task.copy(description = input)
        }

        fun updateDateDue(input: LocalDate?) {
            task = task.copy(dueDate = input)
        }

        fun updatePriority(input: TaskPriority) {
            task = task.copy(priority = input)
        }

        /** Iterate the progress value */
        fun createTask() {
            viewModelScope.launch(contextPool.ioDispatcher) {
                when (val createTask = repository.createTask(task)) {
                    is Result.Success -> {
                        updatePetStatus(createTask.data) // find out the current state of the pet
                    }
                    is Result.Failure -> {
                        val error = createTask.error?.message
                        logger.logError(
                            HOME_SCREEN_VIEWMODEL,
                            "Unable to update pet status. Cause of error: $error",
                        )
                        withContext(contextPool.mainDispatcher) {
                            _viewState.value = HomeScreenViewState.Error(error.toString())
                        }
                    }
                }
            }
        }

        private suspend fun updatePetStatus(tasks: List<Task>) {
            when (val petStatus = calculatePetStatusUseCase(tasks)) {
                is Result.Success ->
                    withContext(contextPool.mainDispatcher) {
                        _viewState.value =
                            HomeScreenViewState.Content(
                                petState = petStatus.data,
                                priorities = tasks,
                            )
                    }
                is Result.Failure ->
                    withContext(contextPool.mainDispatcher) {
                        logger.logError(
                            HOME_SCREEN_VIEWMODEL,
                            "Unable to update pet status. Cause of error: ${petStatus.error}",
                        )
                        _viewState.value = HomeScreenViewState.Error(petStatus.error.toString())
                    }
            }
        }
    }
