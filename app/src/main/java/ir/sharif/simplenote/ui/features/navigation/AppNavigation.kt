package ir.sharif.simplenote.ui.features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ir.sharif.simplenote.ui.features.notemanagement.NoteScreen
import ir.sharif.simplenote.ui.features.changepassword.ChangePasswordScreen
import ir.sharif.simplenote.ui.features.home.HomeScreen
import ir.sharif.simplenote.ui.features.login.LoginScreen
import ir.sharif.simplenote.ui.features.onboarding.OnBoardingScreen
import ir.sharif.simplenote.ui.features.register.RegisterScreen
import ir.sharif.simplenote.ui.features.settings.SettingsScreen
import ir.sharif.simplenote.ui.features.sync.SyncScreen


@Composable
fun AppNavigation() {

    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Graph.ROOT,
    ) {

        navigation(
            startDestination = Screen.Splash.route,
            route = Graph.ROOT,
        ) {
            composable(route = Screen.Splash.route) {
                SplashScreen(
                    onNavigateToHome = {
                        navController.navigate(Graph.MAIN) {
                            popUpTo(Graph.ROOT) {
                                inclusive = true
                            }
                        }
                    },
                    onNavigateToOnBoarding = {
                        navController.navigate(Graph.AUTH) {
                            popUpTo(Graph.ROOT) {
                                inclusive = true
                            }
                        }
                    },
                    onNavigateToSync = {
                        navController.navigate(Graph.SYNC) {
                            popUpTo(Graph.ROOT) {
                                inclusive = true
                            }
                        }
                    },
                )
            }
        }

        navigation(
            startDestination = Screen.Sync.route,
            route = Graph.SYNC,
        ) {
            composable(route = Screen.Sync.route) {
                SyncScreen(onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Graph.SYNC){ inclusive = true }
                    }
                })
            }
        }

        navigation(
            startDestination = Screen.OnBoarding.route,
            route = Graph.AUTH,
        ) {

            composable(route = Screen.OnBoarding.route) {
                OnBoardingScreen(
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route)
                    }
                )
            }

            composable(route = Screen.Login.route) {
                LoginScreen(
                    onNavigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    },
                    onNavigateToSync = {
                        navController.navigate(Graph.SYNC) {
                            popUpTo(Graph.AUTH) {
                                inclusive = true
                            }

                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(route = Screen.Register.route) {
                RegisterScreen(
                    onNavigateBackToLogin = {
                        navController.popBackStack()
                        if (navController.currentBackStackEntry?.destination?.route != Screen.Login.route) {
                            navController.navigate(Screen.Login.route)
                        }
                    }
                )
            }
        }

        navigation(
            startDestination = Screen.Home.route,
            route = Graph.MAIN
        ) {
            composable(route = Screen.Home.route) {
                HomeScreen(
                    onNavigateToCreateNote = {
                        navController.navigate(Screen.CreateNote.route)
                    },
                    onNavigateToSettings = {
                        navController.navigate(Screen.Settings.route)
                    },
                    onNoteClick = { noteId ->
                        navController.navigate(Screen.EditNote.createRouteWithId(noteId))
                    }
                )
            }

            composable(route = Screen.CreateNote.route) {
                NoteScreen(
                    onNavigateBackToHome = {
                        navController.popBackStack()
                        if (navController.currentBackStackEntry?.destination?.route != Screen.Home.route) {
                            navController.navigate(Screen.Home.route)
                        }
                    }
                )
            }

            composable(
                route = Screen.EditNote.BASE_ROUTE,
                arguments = listOf(
                    navArgument(Screen.EditNote.NOTE_ID_ARG) {
                        type = NavType.LongType

                    }
                )
            ) {
                NoteScreen(
                    onNavigateBackToHome = {
                        navController.popBackStack()
                        if (navController.currentBackStackEntry?.destination?.route != Screen.Home.route) {
                            navController.navigate(Screen.Home.route)
                        }
                    }
                )
            }

            composable(route = Screen.Settings.route) {
                SettingsScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateChangePassword = {
                        navController.navigate(Screen.ChangePassword.route)
                    },
                    onLogoutNavigation = {
                        navController.navigate(Graph.AUTH) {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(route= Screen.ChangePassword.route) {
                ChangePasswordScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

    }
}