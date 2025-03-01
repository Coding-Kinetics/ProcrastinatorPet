package com.codingkinetics.pet.procrastinationpanic.pet.domain

import com.codingkinetics.pet.procrastinationpanic.R

sealed class PetState(
    val state: PetMood,
) {
    abstract fun getRawAnimation(): Int
    abstract fun getMessaging(name: String): String

    data object Happy : PetState(PetMood.Happy) {
        override fun getRawAnimation(): Int = R.raw.pet_waving
        override fun getMessaging(name: String): String = "$name is chilling today."
    }

    data object Elated : PetState(PetMood.Elated) {
        override fun getRawAnimation(): Int = R.raw.pet_stars
        override fun getMessaging(name: String): String = "$name is so proud of you!"
    }

    data object Panicking : PetState(PetMood.Panicking) {
        override fun getRawAnimation(): Int = R.raw.pet_panicking
        override fun getMessaging(name: String): String = "$name is panicking ahhhh!"
    }

    data object Sad : PetState(PetMood.Sad) {
        override fun getRawAnimation(): Int = R.raw.pet_crying
        override fun getMessaging(name: String): String = "$name feels neglected."
    }

    data object Loved : PetState(PetMood.Loved) {
        override fun getRawAnimation(): Int = R.raw.pet_love
        override fun getMessaging(name: String): String = "$name is getting the love."
    }

    fun getState(state: Int): PetMood = when (state) {
        0 -> PetMood.Happy
        1 -> PetMood.Elated
        2 -> PetMood.Panicking
        4 -> PetMood.Loved
        else -> PetMood.Sad
    }
}

enum class PetMood(
    val moodRate: Int,
) {
    Happy(0),
    Elated(1),
    Panicking(2),
    Sad(3),
    Loved(4),
}
