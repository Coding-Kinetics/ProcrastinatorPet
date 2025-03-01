package com.codingkinetics.pet.procrastinationpanic.tasks.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codingkinetics.pet.procrastinationpanic.home.ui.HomeScreenViewModel
import com.codingkinetics.pet.procrastinationpanic.tasks.domain.TaskPriority

@Composable
fun SegmentedPriorityButton(viewModel: HomeScreenViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Choose an option:",
            style = MaterialTheme.typography.bodyLarge,
        )
        SingleChoiceSegmentedButtonRow {
            TaskPriority.entries.forEachIndexed { index, option ->
                SegmentedButton(
                    selected = viewModel.task.priority == option,
                    onClick = { viewModel.updatePriority(option) },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = TaskPriority.entries.size,
                    ),
                ) {
                    Text(text = option.name)
                }
            }
        }
    }
}
