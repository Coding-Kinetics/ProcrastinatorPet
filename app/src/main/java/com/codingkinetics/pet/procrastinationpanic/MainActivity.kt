package com.codingkinetics.pet.procrastinationpanic

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.codingkinetics.pet.procrastinationpanic.home.ui.HomeScreen
import com.codingkinetics.pet.procrastinationpanic.home.ui.HomeScreenViewModel
import com.codingkinetics.pet.procrastinationpanic.home.ui.HomeScreenViewState
import com.codingkinetics.pet.procrastinationpanic.ui.navigation.Destinations
import com.codingkinetics.pet.procrastinationpanic.ui.navigation.navGraph
import com.codingkinetics.pet.procrastinationpanic.ui.navigation.rememberNavigationState
import com.codingkinetics.pet.procrastinationpanic.ui.navigation.topLevelRoutes
import com.codingkinetics.pet.procrastinationpanic.ui.theme.LightBackground
import com.codingkinetics.pet.procrastinationpanic.ui.theme.ProcrastinationPanicTheme
import com.codingkinetics.pet.procrastinationpanic.ui.theme.Purple80
import com.codingkinetics.pet.procrastinationpanic.util.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var logger: Logger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel = hiltViewModel<HomeScreenViewModel>()
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            ProcrastinationPanicTheme {
                val navigationState = rememberNavigationState()

                NavHost(
                    navController = navigationState.navController,
                    startDestination = Destinations.Home.route,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    navGraph(
                        navController = navigationState.navController,
                        viewModel = viewModel,
                        logger = logger,
                        addNewTask = navigationState::navigateToAddTaskDialog,
                        onTaskItemSelected = navigationState::navigateToEditTaskDialog,
                        upPress = navigationState::upPress,
                        drawerState = drawerState,
                        scope = scope,
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel,
    addNewTask: () -> Unit,
    onTaskItemSelected: (Int) -> Unit,
    logger: Logger,
    drawerState: DrawerState,
    scope: CoroutineScope,
    upPress: () -> Unit,
) {
    MainScaffold(navController, viewModel, drawerState, scope) {
        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Text("Menu")
                    HorizontalDivider()
                    NavigationDrawerItem(
                        label = { Text("Notification Settings") },
                        selected = false,
                        onClick = { /* TODO */ }
                    )
                }
            },
            drawerState = drawerState
        ) {
            HomeScreen(viewModel, addNewTask, onTaskItemSelected, upPress, logger)
        }
    }
}

@SuppressLint("RestrictedApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    navController: NavController,
    viewModel: HomeScreenViewModel,
    drawerState: DrawerState,
    scope: CoroutineScope,
    content: @Composable (PaddingValues) -> Unit,
) {
    val viewState by viewModel.viewState.observeAsState(HomeScreenViewState.Loading)

    Scaffold(
        containerColor = LightBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text("Procrastination Panic")
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(5.dp, 10.dp),
                        onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        },
                    ) {
                        Icon(Icons.Filled.Menu, "Menu", Modifier.size(24.dp))
                    }
                },
                colors = TopAppBarColors(
                    containerColor = Color.White,
                    scrolledContainerColor = Color.Unspecified,
                    navigationIconContentColor = Color.Unspecified,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Unspecified,
                ),
            )
        },
        content = { paddingValues -> // intended for content padding to offset top and bottom bars
            Box(
                modifier =
                    Modifier.padding(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding(),
                        start = 10.dp,
                        end = 10.dp,
                    ),
            ) {
                content(paddingValues)
            }
        },
        bottomBar = {
            BottomNavigation(
                backgroundColor = Purple80,
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                topLevelRoutes.forEach { route ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                route.icon,
                                contentDescription = route.name,
                            )
                        },
                        label = { Text(route.name) },
                        selected = currentDestination?.hierarchy?.any { it.hasRoute(route.route.route, null) } == true,
                        onClick = {
                            navController.navigate(route.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        },
                    )
                }
            }
        },
    )
}
