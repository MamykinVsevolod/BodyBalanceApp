package com.iu6_mamykin.bodybalance.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.iu6_mamykin.bodybalance.R

@Composable
fun MyNavigationBar(navController: NavController, index: Int) {
    var selectedItem by remember { mutableIntStateOf(index) }
    val items = listOf("Тренировки", "Профиль", "Тех. поддержка")
    val routes = listOf(Routes.TRAINING_LIST, Routes.PROFILE, Routes.FEEDBACK)
    val selectedIcons = listOf(
        painterResource(R.drawable.directions_walk),
        painterResource(R.drawable.account_circle),
        painterResource(R.drawable.mail)
    )
    val unselectedIcons =
        listOf(
            painterResource(R.drawable.directions_walk),
            painterResource(R.drawable.account_circle),
            painterResource(R.drawable.mail)
        )

    NavigationBar(
        containerColor = Color(0xFF1F1F1F),
        contentColor = Color(0xFFC6C6C6)
    ) {

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedItem == index) selectedIcons[index] else unselectedIcons[index],
                        contentDescription = item
                    )
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = Color(0xFFE2E2E2),
                    selectedTextColor = Color(0xFFE2E2E2),
                    selectedIndicatorColor = Color(0xFF474747),
                    unselectedIconColor = Color(0xFFE2E2E2),
                    unselectedTextColor = Color(0xFFE2E2E2),
                    disabledIconColor = Color(0xFFE2E2E2),
                    disabledTextColor = Color(0xFFE2E2E2)
                ),
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    if (routes[index] == Routes.PROFILE) {
                        // Сначала очищаем весь стек, затем переходим к основному экрану профиля
                        navController.navigate(Routes.PROFILE) {
                            popUpTo(0) { inclusive = true } // Очищаем стек до самого начала
                            launchSingleTop = true          // Избегаем дублирования корневого экрана
                        }
                    } else {
                        // Для остальных вкладок обычный переход
                        navController.navigate(routes[index]) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    /*navController.navigate(routes[index]) {
                        // Эти параметры очищают стек до текущего экрана, предотвращая накопление.
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }*/
                }
            )
        }
    }
}
