package com.devdroid.a7minutesworkout

object Constants {
    fun defaultExerciesList():ArrayList<ExerciseModel>{
        var exerciseList=ArrayList<ExerciseModel>()
        val exercies1=ExerciseModel(
            1,
            "Jumping Jacks",
            R.raw.jumpingjacks,
            false,
            false
        )
        exerciseList.add(exercies1)
        val wallSit = ExerciseModel(2, "Wall Sit", R.raw.wallsit, false, false)
        exerciseList.add(wallSit)

        val pushUp = ExerciseModel(3, "Push Up", R.raw.perfectpushup, false, false)
        exerciseList.add(pushUp)

        val abdominalCrunch =
            ExerciseModel(4, "Abdominal Crunch", R.raw.abdominal, false, false)
        exerciseList.add(abdominalCrunch)

        val stepUpOnChair =
            ExerciseModel(
                5,
                "Step-Up onto Chair",
                R.raw.chairsetup,
                false,
                false
            )
        exerciseList.add(stepUpOnChair)

        val squat = ExerciseModel(6, "Squat", R.raw.squard, false, false)
        exerciseList.add(squat)

        val tricepDipOnChair =
            ExerciseModel(
                7,
                "Tricep Dip On Chair",
                R.raw.tricepdip0nchair,
                false,
                false
            )
        exerciseList.add(tricepDipOnChair)

        val plank = ExerciseModel(8, "Plank", R.raw.plank, false, false)
        exerciseList.add(plank)

        val highKneesRunningInPlace =
            ExerciseModel(
                9, "High Knees Running In Place",
                R.drawable.hifhrunning,
                false,
                false
            )
        exerciseList.add(highKneesRunningInPlace)

        val lunges = ExerciseModel(10, "Lunges", R.raw.lunge, false, false)
        exerciseList.add(lunges)

        val pushupAndRotation =
            ExerciseModel(
                11,
                "Push up and Rotation",
                R.raw.pushuprotation,
                false,
                false
            )
        exerciseList.add(pushupAndRotation)

        val sidePlank = ExerciseModel(12, "Side Plank", R.raw.sideplank, false, false)
        exerciseList.add(sidePlank)
        return exerciseList
    }
}