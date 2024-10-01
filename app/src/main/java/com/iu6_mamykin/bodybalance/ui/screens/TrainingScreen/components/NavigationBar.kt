package com.iu6_mamykin.bodybalance.ui.screens.TrainingScreen.components

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
import com.iu6_mamykin.bodybalance.R

@Composable
fun MyNavigationBar() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Тренировки", "Профиль", "Тех. поддержка")
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
                onClick = { selectedItem = index }
            )
        }
    }
}