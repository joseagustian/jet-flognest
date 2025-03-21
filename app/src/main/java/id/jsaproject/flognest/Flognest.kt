package id.jsaproject.flognest

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.rounded.ModeEdit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument

import id.jsaproject.flognest.ui.navigation.Screen
import id.jsaproject.flognest.ui.navigation.navItems
import id.jsaproject.flognest.ui.screen.about.AboutScreen
import id.jsaproject.flognest.ui.screen.add.AddContentDialog
import id.jsaproject.flognest.ui.screen.detail.DetailScreen
import id.jsaproject.flognest.ui.screen.edit.EditContentDialog
import id.jsaproject.flognest.ui.screen.home.HomeScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import id.jsaproject.flognest.ui.screen.poster.TheMovieDBWebViewScreen

@Composable
fun Flognest(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var contentState by remember { mutableStateOf(ContentState()) }
    var contentTitle by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier
            .background(Color.White),
        topBar = {
            if (currentRoute != Screen.TMDBWebViewScreen.route) {
                AppTopBar(
                    currentRoute = currentRoute,
                    contentTitle = contentTitle,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            }
        },
        bottomBar = {
            if (currentRoute in listOf(Screen.Home.route, Screen.About.route)) {
                BottomBar(navController = navController)
            }
        },
        floatingActionButton = {
            FloatingActionButtonByRoute(
                currentRoute = currentRoute,
                onAddClick = { contentState = contentState.copy(isAddDialogOpen = true) },
                onEditClick = { contentState = contentState.copy(isEditDialogOpen = true) }
            )
        }
    ) { innerPadding ->
        when {
            contentState.isAddDialogOpen -> {
                AddContentDialog(
                    onDismissRequest = { contentState = contentState.copy(isAddDialogOpen = false) },
                    onContentAdded = {
                        contentState = contentState.copy(
                            shouldRefreshData = true
                        )
                    },
                    navigateToTMDBPage = {
                        navController.navigate(Screen.TMDBWebViewScreen.route)
                    }
                )
            }
            contentState.isEditDialogOpen -> {
                EditContentDialog(
                    onContentUpdate = {
                        contentState = contentState.copy(
                            shouldRefreshDetail = true,
                            shouldRefreshData = true
                        )
                    },
                    onDismissRequest = { contentState = contentState.copy(isEditDialogOpen = false) },
                    contentState = contentState
                )
            }
        }
        AppNavHost(
            navController = navController,
            modifier = modifier.padding(innerPadding),
            onTitleChange = { contentTitle = it },
            onContentStateChange = { contentState = it },
            shouldRefreshData = contentState.shouldRefreshData,
            shouldRefreshDetail = contentState.shouldRefreshDetail
        )
    }
}

data class ContentState(
    val title: String = "",
    val id: String = "",
    val isWatchlist: Boolean = false,
    val isAddDialogOpen: Boolean = false,
    val isEditDialogOpen: Boolean = false,
    val shouldRefreshData: Boolean = false,
    val shouldRefreshDetail: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    currentRoute: String?,
    contentTitle: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = when (currentRoute) {
                    Screen.Home.route -> "Flognest"
                    Screen.About.route -> "About Application"
                    Screen.ContentDetail.route -> contentTitle
                    else -> ""
                },
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                maxLines = 1,
                textAlign = if (currentRoute == Screen.ContentDetail.route || currentRoute == Screen.Home.route) TextAlign.Start else TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                modifier = modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            if (currentRoute != Screen.Home.route && currentRoute != Screen.About.route) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onBackClick()
                    }
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun FloatingActionButtonByRoute(
    currentRoute: String?,
    onAddClick: () -> Unit,
    onEditClick: () -> Unit
) {
    val fabContent = when (currentRoute) {
        Screen.Home.route -> {
            FabContent.Add(onAddClick)
        }
        Screen.ContentDetail.route -> {
            FabContent.Edit(onEditClick)
        }
        else -> null
    }

    fabContent?.let {
        FloatingActionButton(
            containerColor = Color(0xFF000000),
            contentColor = Color(0xFFFFFFFF),
            onClick = it.onClick
        ) {
            Icon(
                imageVector = it.icon,
                contentDescription = it.contentDescription
            )
        }
    }
}

sealed class FabContent(val icon: ImageVector, val contentDescription: String, val onClick: () -> Unit) {
    class Add(onClick: () -> Unit) : FabContent(Icons.Default.Add, "Add Movie", onClick)
    class Edit(onClick: () -> Unit) : FabContent(Icons.Rounded.ModeEdit, "Edit Movie", onClick)
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onContentStateChange: (ContentState) -> Unit,
    onTitleChange: (String) -> Unit,
    shouldRefreshData: Boolean,
    shouldRefreshDetail: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 500)
            ) + fadeIn(animationSpec = tween(durationMillis = 300))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis = 500)
            ) + fadeOut(animationSpec = tween(durationMillis = 300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis = 500)
            ) + fadeIn(animationSpec = tween(durationMillis = 300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = 500)
            ) + fadeOut(animationSpec = tween(durationMillis = 300))
        }
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToDetail = { contentId, contentTitle ->
                    onTitleChange(contentTitle)
                    onContentStateChange(ContentState(id = contentId))
                    navController.navigate(Screen.ContentDetail.createRoute(contentId, contentTitle))
                },
                openEditDialog = { contentId, title, argsIsWatchlist ->
                    onContentStateChange(ContentState(id = contentId, title = title, isWatchlist = argsIsWatchlist, isEditDialogOpen = true))
                },
                shouldRefreshData = shouldRefreshData
            )
        }
        composable(Screen.About.route) {
            AboutScreen()
        }
        composable(
            Screen.ContentDetail.route,
            arguments = listOf(navArgument("contentId") { type = NavType.StringType })
        ) {
            val contentId = it.arguments?.getString("contentId") ?: ""
            onContentStateChange(ContentState(id = contentId))
            DetailScreen(
                contentId = contentId,
                shouldRefreshDetail = shouldRefreshDetail
            )
        }
        composable(Screen.TMDBWebViewScreen.route) {
            TheMovieDBWebViewScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(modifier = modifier) {
        navItems.forEach { item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xDAFFFFFF),
                    selectedTextColor = Color(0xFF000000),
                    indicatorColor = Color(0xFF000000)
                ),
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}