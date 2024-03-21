package me.vaimon.healthtracker.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import me.vaimon.healthtracker.screens.home.HomeScreen
import me.vaimon.healthtracker.screens.home.HomeScreenDestination
import me.vaimon.healthtracker.screens.training_details.TrainingDetailsDestination
import me.vaimon.healthtracker.screens.training_details.TrainingDetailsScreen

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeScreenDestination.route
    ) {
        val springAnimationSpec = spring<IntOffset>(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessLow
        )

        composable(route = HomeScreenDestination.route) {
            HomeScreen(navController)
        }

        composable(
            route = TrainingDetailsDestination.route,
            arguments = listOf(
                navArgument(TrainingDetailsDestination.argName) {
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = springAnimationSpec
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = springAnimationSpec
                )
            },
        ) {
            TrainingDetailsScreen(navController)
        }
    }
}