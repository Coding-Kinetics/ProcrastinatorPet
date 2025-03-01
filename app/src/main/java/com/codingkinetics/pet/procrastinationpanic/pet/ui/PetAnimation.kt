package com.codingkinetics.pet.procrastinationpanic.pet.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.codingkinetics.pet.procrastinationpanic.pet.domain.PetState

@Composable
fun PetAnimation(petState: PetState) {
    val rawComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(petState.getRawAnimation()),
    )

    val progress by animateLottieCompositionAsState(composition = rawComposition)

    LottieAnimation(
        composition = rawComposition,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .width(300.dp)
            .height(200.dp),
    )
}
