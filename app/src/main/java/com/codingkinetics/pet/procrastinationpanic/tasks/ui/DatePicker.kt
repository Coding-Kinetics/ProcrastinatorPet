package com.codingkinetics.pet.procrastinationpanic.tasks.ui

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.codingkinetics.pet.procrastinationpanic.home.ui.HomeScreenViewModel
import com.codingkinetics.pet.procrastinationpanic.util.Logger
import com.codingkinetics.pet.procrastinationpanic.util.convertToLocalDate
import com.codingkinetics.pet.procrastinationpanic.util.toStringDate

@Composable
fun DatePickerFieldToModal(
    viewModel: HomeScreenViewModel,
    tag: String,
    logger: Logger,
    modifier: Modifier = Modifier,
) {
    var showModal by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = viewModel.task.dueDate?.toStringDate(tag, logger) ?: "",
        onValueChange = { date -> viewModel.updateDateDue(date.convertToLocalDate(tag, logger)) },
        label = { Text("Due Date") },
        placeholder = { Text("MM/DD/YYYY") },
        trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = "Select date")
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 18.dp, top = 10.dp, end = 18.dp)
            .pointerInput(viewModel.task.dueDate) {
                awaitEachGesture {
                    // Modifier.clickable doesn't work for text fields, so we use Modifier.pointerInput
                    // in the Initial pass to observe events before the text field consumes them
                    // in the Main pass.
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    waitForUpOrCancellation(pass = PointerEventPass.Initial)?.let {
                        showModal = true
                    }
                }
            },
    )

    if (showModal) {
        DatePickerModal(
            onDateSelected = { millis: Long? ->
                millis?.let { viewModel.updateDateDue(it.convertToLocalDate(tag, logger)) }
            },
            onDismiss = { showModal = false },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}
