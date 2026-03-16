package ph.edu.usc24104013.repository

import ph.edu.usc24104013.data.dao.ExerciseDao
import ph.edu.usc24104013.utils.WorkoutGenerator

class WorkoutRepo(private val exerciseDao: ExerciseDao) {

    suspend fun generateWorkout(
        type: String,
        totalMinutes: Int
    ): WorkoutGenerator.GeneratedWorkout {
        val (warmupCount, mainCount, cooldownCount) =
            WorkoutGenerator.calculateExerciseCount(totalMinutes)

        val warmups   = exerciseDao.getWarmupExercises(warmupCount)
        val mains     = exerciseDao.getMainExercises(type, mainCount)
        val cooldowns = exerciseDao.getCooldownExercises(cooldownCount)

        val allExercises = (warmups + mains + cooldowns).map { ex ->
            WorkoutGenerator.WorkoutExerciseItem(
                name        = ex.name,
                description = ex.description,
                duration    = WorkoutGenerator.formatDuration(ex.durationSeconds, ex.phase),
                phase       = ex.phase
            )
        }

        val totalCalories = (warmups + mains + cooldowns)
            .sumOf { (it.caloriesPerMinute * (it.durationSeconds / 60f)).toDouble() }
            .toFloat()

        return WorkoutGenerator.GeneratedWorkout(
            title             = "$totalMinutes-Min $type Workout",
            totalMinutes      = totalMinutes,
            estimatedCalories = totalCalories,
            exercises         = allExercises
        )
    }
}