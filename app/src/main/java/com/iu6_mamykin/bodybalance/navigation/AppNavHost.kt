package com.iu6_mamykin.bodybalance.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.iu6_mamykin.bodybalance.ui.screens.CreateUpdateTrainingScreen.CreateUpdateTrainingScreen
import com.iu6_mamykin.bodybalance.ui.screens.FeedbackScreen.FeedbackScreen
import com.iu6_mamykin.bodybalance.ui.screens.ProfileEditScreen.ProfileEditScreen
import com.iu6_mamykin.bodybalance.ui.screens.ProfileScreen.ProfileScreen
import com.iu6_mamykin.bodybalance.ui.screens.Settings.SettingsScreen
import com.iu6_mamykin.bodybalance.ui.screens.TrainingProgressScreen.TrainingProgressScreen
import com.iu6_mamykin.bodybalance.ui.screens.TrainingsWorkoutNamesEditScreen.TrainingsWorkoutNamesEditScreen
import ui.TrainingListScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.TRAINING_LIST
    ) {
        composable(Routes.CREATE_UPDATE_TRAINING) { CreateUpdateTrainingScreen(navController) }
        composable(Routes.FEEDBACK) { FeedbackScreen(navController) }
        composable(Routes.PROFILE_EDIT) { ProfileEditScreen(navController) }
        composable(Routes.PROFILE) { ProfileScreen(navController) }
        composable(Routes.SETTINGS) { SettingsScreen(navController) }

        composable(Routes.TRAINING_LIST) { TrainingListScreen(navController) }

        // Передаем аргумент trainingId на экран TrainingProgressScreen
        composable(Routes.TRAINING_PROGRESS) { backStackEntry ->
            val trainingId = backStackEntry.arguments?.getString("trainingId")?.toIntOrNull()
            if (trainingId != null) {
                TrainingProgressScreen(navController, trainingId)
            }
        }

        composable(Routes.TRAININGS_WORKOUT_NAMES_EDIT) { backStackEntry ->
            val isTraining = backStackEntry.arguments?.getString("isTraining")?.toBoolean() ?: false
            if (isTraining != null) {
                TrainingsWorkoutNamesEditScreen(navController, isTraining)
            }
        }
    }
}