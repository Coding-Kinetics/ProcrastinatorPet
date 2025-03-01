package com.codingkinetics.pet.procrastinationpanic.tasks.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.codingkinetics.pet.procrastinationpanic.home.ui.HomeScreenViewModel
import com.codingkinetics.pet.procrastinationpanic.tasks.domain.Task
import com.codingkinetics.pet.procrastinationpanic.ui.theme.ButtonColor
import com.codingkinetics.pet.procrastinationpanic.util.Logger
import com.codingkinetics.pet.procrastinationpanic.util.toStringDate

private const val TAG = "TaskItem"

@Composable
fun TaskItem(
    viewModel: HomeScreenViewModel,
    task: Task,
    onTaskItemSelected: (Int) -> Unit,
    logger: Logger,
    upPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(shadowElevation = 6.dp) {
        ListItem(
            modifier =
                Modifier
                    .padding(PaddingValues(2.dp, 0.dp, 2.dp, 12.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .shadow(elevation = 10.dp)
                    .clickable(
                        onClick = { onTaskItemSelected(task.id.toInt()) },
                    ),
            colors =
                ListItemColors(
                    containerColor = Color.White,
                    headlineColor = Black,
                    overlineColor = ButtonColor,
                    supportingTextColor = DarkGray,
                    leadingIconColor = DarkGray,
                    trailingIconColor = Color.Unspecified,
                    disabledHeadlineColor = Color.Unspecified,
                    disabledLeadingIconColor = Color.Unspecified,
                    disabledTrailingIconColor = DarkGray,
                ),
            headlineContent = {
                Text(text = task.title)
            },
            supportingContent = {
                Text(text = task.description)
                task.dueDate?.toStringDate(TAG, logger)?.let {
                    Text(
                        text = "Due date: $it",
                        fontWeight = FontWeight.Bold,
                    )
                }
            },
            leadingContent = {
                Icon(Icons.Filled.MoreVert, "Move Task")
            },
            trailingContent = {
                Icon(Icons.Filled.Edit, "Edit", Modifier.size(18.dp))
            },
        )
    }
}
