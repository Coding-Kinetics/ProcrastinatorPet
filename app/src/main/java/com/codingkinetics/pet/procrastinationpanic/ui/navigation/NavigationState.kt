package com.codingkinetics.pet.procrastinationpanic.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * Remembers and creates an instance of [NasaAppState]
 */
@Composable
fun rememberNavigationState(navController: NavHostController = rememberNavController()) =
    remember(navController) {
        NavigationState(navController)
    }

/**
 * Responsible for holding state related to [AppContent] and containing UI-related logic.
 */
@Stable
class NavigationState(
    val navController: NavHostController,
) {
    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToEditTaskDialog(
        taskId: Int,
        from: NavBackStackEntry,
    ) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate("${Destinations.EditTaskDialog.route}/task?id=$taskId")
        }
    }

    fun navigateToAddTaskDialog(from: NavBackStackEntry) {
        // In order to discard duplicated navigation events, we check the Lifecycle
        if (from.lifecycleIsResumed()) {
            navController.navigate(Destinations.NewTaskDialog.route)
        }
    }
}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() = this.lifecycle.currentState == Lifecycle.State.RESUMED

private val NavGraph.startDestination: NavDestination?
    get() = findNode(startDestinationId)
