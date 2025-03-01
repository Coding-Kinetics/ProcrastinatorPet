package com.codingkinetics.pet.procrastinationpanic.home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.codingkinetics.pet.procrastinationpanic.tasks.domain.TaskPriority
import com.codingkinetics.pet.procrastinationpanic.tasks.ui.TaskItem
import com.codingkinetics.pet.procrastinationpanic.util.Logger

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    addNewTask: () -> Unit,
    onTaskItemSelected: (Int) -> Unit,
    upPress: () -> Unit,
    logger: Logger,
    modifier: Modifier = Modifier,
) {
    val viewState by viewModel.viewState.observeAsState(HomeScreenViewState.Loading)

    when (viewState) {
        is HomeScreenViewState.Loading -> {
            CircularProgressIndicator()
        }

        is HomeScreenViewState.Content -> {
            val state = viewState as HomeScreenViewState.Content

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                stickyHeader {
                    HomePetAvatar(state, addNewTask)
                }
                item {
                    TitleText("This Week's Priorities")
                }
                itemsIndexed(
                    items = state.priorities.filter { it.priority == TaskPriority.Important },
                    contentType = { index, _ -> DraggableItem(index = index) },
                ) { _, task ->
                    TaskItem(viewModel, task, onTaskItemSelected, logger, upPress)
                }
                item {
                    TitleText("Backlog")
                }
                itemsIndexed(
                    items = state.priorities.filter { it.priority == TaskPriority.Normal },
                    contentType = { index, _ -> DraggableItem(index = index) },
                ) { _, task ->
                    TaskItem(viewModel, task, onTaskItemSelected, logger, upPress)
                }
            }
        }
        is HomeScreenViewState.Error -> {
            Text("Error!")
        }
    }
}

@Composable
fun TitleText(title: String) {
    Text(
        text = title,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(20.dp, 10.dp, 10.dp, 10.dp),
    )
}

data class DraggableItem(val index: Int)
