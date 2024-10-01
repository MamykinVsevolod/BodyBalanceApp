package com.iu6_mamykin.bodybalance.ui.screens.TrainingScreen.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.iu6_mamykin.bodybalance.ui.theme.OutlinedColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegmentedButton() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Новые", "Выполненные")
    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                colors = SegmentedButtonColors(activeContainerColor = Color(0xFF474747),
                    activeContentColor = Color(0xFFE2E2E2),
                    activeBorderColor = Color(0xFF919191),
                    inactiveContainerColor = Color(0xFFFAF8FF),
                    //inactiveContentColor = Color(0xFFE2E2E2),
                    inactiveContentColor = OutlinedColor,
                    inactiveBorderColor = Color(0xFF919191),
                    disabledActiveContainerColor = Color(0xFF474747),
                    disabledActiveContentColor = Color(0xFFE2E2E2),
                    disabledActiveBorderColor = Color(0xFF919191),
                    disabledInactiveContainerColor = Color(0xFFFAF8FF),
                    disabledInactiveContentColor = Color(0xFFE2E2E2),
                    disabledInactiveBorderColor = Color(0xFF919191)
                ),
                onClick = { selectedIndex = index },
                selected = index == selectedIndex
            ) {
                Text(label)
            }
        }
    }
}