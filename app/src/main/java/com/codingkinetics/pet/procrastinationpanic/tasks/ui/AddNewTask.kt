package com.codingkinetics.pet.procrastinationpanic.tasks.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codingkinetics.pet.procrastinationpanic.MainScaffold
import com.codingkinetics.pet.procrastinationpanic.home.ui.HomeScreenViewModel
import com.codingkinetics.pet.procrastinationpanic.ui.theme.LightBackground
import com.codingkinetics.pet.procrastinationpanic.util.Logger

const val TASK_SCREEN = "TaskScreen"

@Composable
fun TaskScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel,
    logger: Logger,
    upPress: () -> Unit,
) {
    MainScaffold(navController, viewModel) {
        AddNewTask(viewModel, logger, upPress)
    }
}

@Composable
fun AddNewTask(
    viewModel: HomeScreenViewModel,
    logger: Logger,
    upPress: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(LightBackground),
    ) {
        val textFieldModifier =
            Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 10.dp, end = 24.dp)
                .background(Color.White, RoundedCornerShape(5.dp))
        Row {
            IconButton(
                modifier = Modifier.offset(x = (-8).dp),
                onClick = { upPress() },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Navigate Back",
                    modifier = Modifier.size(24.dp),
                    tint = Color.DarkGray,
                )
            }
            Text(
                text = "Add new task",
                fontSize = 18.sp,
                fontWeight = Bold,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 10.dp, end = 24.dp),
            )
        }
        OutlinedTextField(
            value = viewModel.task.title,
            label = { Text("Title") },
            onValueChange = { title -> viewModel.updateTaskTitle(title) },
            modifier = textFieldModifier,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        )
        OutlinedTextField(
            modifier = textFieldModifier,
            shape = RoundedCornerShape(5.dp),
            label = { Text("Write task description here") },
            value = viewModel.task.description,
            onValueChange = { desc -> viewModel.updateTaskDescription(desc) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            maxLines = 3,
            textStyle = MaterialTheme.typography.displayMedium,
        )
        SegmentedPriorityButton(viewModel)
        DatePickerFieldToModal(viewModel, TASK_SCREEN, logger)
        Button(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 15.dp, top = 10.dp, end = 15.dp),
            onClick = {
                viewModel.createTask()
                upPress()
            },
        ) {
            Text(text = "Save")
        }
    }
}
