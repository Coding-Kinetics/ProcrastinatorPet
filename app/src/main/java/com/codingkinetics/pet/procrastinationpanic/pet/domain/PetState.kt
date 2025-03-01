package com.codingkinetics.pet.procrastinationpanic.pet.domain

import com.codingkinetics.pet.procrastinationpanic.R

sealed class PetState(val state: PetMood) {
    abstract fun getRawAnimation(): Int

    abstract fun getMessaging(name: String): String

    data object Happy : PetState(PetMood.HAPPY) {
        override fun getRawAnimation(): Int = R.raw.pet_waving

        override fun getMessaging(name: String): String = "$name is chilling today."
    }

    data object Elated : PetState(PetMood.ELATED) {
        override fun getRawAnimation(): Int = R.raw.pet_stars

        override fun getMessaging(name: String): String = "$name is so proud of you!"
    }

    data object Panicking : PetState(PetMood.PANICKING) {
        override fun getRawAnimation(): Int = R.raw.pet_panicking

        override fun getMessaging(name: String): String = "$name is panicking ahhhh!"
    }

    data object Sad : PetState(PetMood.SAD) {
        override fun getRawAnimation(): Int = R.raw.pet_crying

        override fun getMessaging(name: String): String = "$name feels neglected."
    }

    data object Loved : PetState(PetMood.LOVED) {
        override fun getRawAnimation(): Int = R.raw.pet_love

        override fun getMessaging(name: String): String = "$name is getting the love."
    }

    fun getState(state: Int): PetMood {
        return when (state) {
            0 -> PetMood.HAPPY
            1 -> PetMood.ELATED
            2 -> PetMood.PANICKING
            4 -> PetMood.LOVED
            else -> PetMood.SAD
        }
    }
}

enum class PetMood(val moodRate: Int) {
    HAPPY(0),
    ELATED(1),
    PANICKING(2),
    SAD(3),
    LOVED(4),
}
