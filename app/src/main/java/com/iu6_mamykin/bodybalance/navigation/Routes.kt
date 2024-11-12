package com.iu6_mamykin.bodybalance.navigation

object Routes {
    const val CREATE_UPDATE_TRAINING = "create_update_training/{trainingId}"
    const val FEEDBACK = "feedback"
    const val PROFILE_EDIT = "profile_edit"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"
    const val TRAINING_PROGRESS = "training_progress/{trainingId}"
    const val TRAINING_LIST = "training_list"
    const val TRAININGS_WORKOUT_NAMES_EDIT = "trainings_workout_names_edit/{isTraining}"

    fun trainingProgressWithArgs(trainingId: String) = "training_progress/$trainingId"
    fun trainingWorkoutNamesEditProgressWithArgs(isTraining: Boolean) = "trainings_workout_names_edit/$isTraining"
    fun createUpdateTrainingWithArgs(trainingId: String) = "create_update_training/$trainingId"
}