package com.codingkinetics.pet.procrastinationpanic.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.codingkinetics.pet.procrastinationpanic.pet.ui.PetAnimation
import com.codingkinetics.pet.procrastinationpanic.ui.theme.Purple80
import com.codingkinetics.pet.procrastinationpanic.ui.theme.PurpleGrey80

@Composable
fun HomePetAvatar(
    state: HomeScreenViewState.Content,
    addNewTask: () -> Unit,
) {
    TitleText("Hi, Amanda. Blake is chilling today.")
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth(),
    ) {
        PetAnimation(state.petState)
    }
    Text(
        text = "Way to be on top of your tasks!",
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(10.dp, 10.dp, 20.dp, 20.dp)
            .fillMaxWidth(),
    )
    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Button(
            onClick = { addNewTask() },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonColors(
                containerColor = Purple80,
                contentColor = Color.Black,
                disabledContentColor = PurpleGrey80,
                disabledContainerColor = Color.LightGray,
            ),
            modifier = Modifier.padding(5.dp),
        ) { Text("Add New Task") }
    }
}
