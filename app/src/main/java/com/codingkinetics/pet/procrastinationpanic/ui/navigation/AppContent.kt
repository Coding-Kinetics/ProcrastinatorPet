package com.codingkinetics.pet.procrastinationpanic.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.codingkinetics.pet.procrastinationpanic.MainScreen
import com.codingkinetics.pet.procrastinationpanic.home.ui.HomeScreenViewModel
import com.codingkinetics.pet.procrastinationpanic.tasks.ui.AddNewTask
import com.codingkinetics.pet.procrastinationpanic.tasks.ui.TaskScreen
import com.codingkinetics.pet.procrastinationpanic.ui.navigation.DestinationArgs.TASK_ID
import com.codingkinetics.pet.procrastinationpanic.util.Logger

object DestinationArgs {
    const val TASK_ID = "id"
}

val topLevelRoutes =
    listOf(
        TopLevelRoute("Home", Destinations.Home, Icons.Filled.Home),
    )

sealed class Destinations(val route: String) {
    data object Home : Destinations("home")

    data object NewTaskDialog : Destinations("home/dialog_new_task")

    data object EditTaskDialog : Destinations("home/dialog_edit_task/task?id={$TASK_ID}")
}

data class TopLevelRoute(val name: String, val route: Destinations, val icon: ImageVector)

fun NavGraphBuilder.navGraph(
    navController: NavController,
    viewModel: HomeScreenViewModel,
    logger: Logger,
    addNewTask: (NavBackStackEntry) -> Unit,
    onTaskItemSelected: (Int, NavBackStackEntry) -> Unit,
    upPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    composable(route = Destinations.Home.route) { navStackBackEntry ->
        MainScreen(
            navController = navController,
            viewModel = viewModel,
            addNewTask = { addNewTask(navStackBackEntry) },
            onTaskItemSelected = { id -> onTaskItemSelected(id, navStackBackEntry) },
            logger = logger,
            upPress = upPress,
        )
    }
    composable(route = Destinations.NewTaskDialog.route) {
        TaskScreen(navController, viewModel, logger, upPress)
    }
    dialog(
        route = Destinations.EditTaskDialog.route,
        arguments =
            listOf(
                navArgument(TASK_ID) { type = NavType.StringType },
            ),
        dialogProperties =
            DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
            ),
    ) {
        AddNewTask(viewModel, logger, upPress, modifier)
    }
}
