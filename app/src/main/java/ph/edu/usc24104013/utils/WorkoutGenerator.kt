package ph.edu.usc24104013.utils

object WorkoutGenerator {

    data class GeneratedWorkout(
        val title: String,
        val totalMinutes: Int,
        val estimatedCalories: Float,
        val exercises: List<WorkoutExerciseItem>
    )

    data class WorkoutExerciseItem(
        val name: String,
        val description: String,
        val duration: String,
        val phase: String,
        val emoji: String
    )

    // Returns Triple(warmupCount, mainCount, cooldownCount)
    fun calculateExerciseCount(totalMinutes: Int): Triple<Int, Int, Int> = when {
        totalMinutes <= 10 -> Triple(2, 2, 1)
        totalMinutes <= 20 -> Triple(3, 4, 2)
        totalMinutes <= 30 -> Triple(3, 6, 3)
        totalMinutes <= 45 -> Triple(4, 9, 3)
        else               -> Triple(4, 12, 4)
    }

    fun phaseEmoji(phase: String): String = when (phase) {
        "warmup"   -> "🔥"
        "main"     -> "💪"
        "cooldown" -> "🧊"
        else       -> "✅"
    }

    fun formatDuration(seconds: Int, phase: String): String =
        if (phase == "main") "3 sets × ${seconds / 3} reps"
        else "$seconds seconds"
}