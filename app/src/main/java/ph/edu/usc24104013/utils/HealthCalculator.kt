package ph.edu.usc24104013.utils

import ph.edu.usc24104013.R
import ph.edu.usc24104013.data.entity.ActivityEntity

object HealthCalculator {

    enum class FatigueLevel(val label: String, val emoji: String, val colorRes: Int) {
        RESTED("Well Rested", "😊", R.color.fatigue_green),
        MODERATE("Moderately Active", "🙂", R.color.fatigue_yellow),
        TIRED("Tired", "😓", R.color.fatigue_orange),
        OVEREXERTED("Overexerted", "🚨", R.color.fatigue_red)
    }

    data class FatigueResult(
        val score: Int,
        val level: FatigueLevel,
        val recommendation: String,
        val trend: List<Int>
    )

    fun predictFatigue(activities: List<ActivityEntity>): FatigueResult {
        if (activities.isEmpty()) {
            return FatigueResult(
                score = 0,
                level = FatigueLevel.RESTED,
                recommendation = "No data yet. Start tracking your activity!",
                trend = emptyList()
            )
        }

        val sorted   = activities.sortedBy { it.date }
        val stepList = sorted.map { it.steps }
        val avg      = stepList.average()

        val highDays    = stepList.reversed().takeWhile { it >= 8000 }.count()
        val spikeRatio  = if (avg > 0) stepList.last() / avg else 1.0
        val recentLoad  = stepList.takeLast(3).sum()

        var score = 0
        score += (highDays * 15).coerceAtMost(45)
        score += ((spikeRatio - 1.0) * 25).toInt().coerceIn(0, 25)
        score += when {
            recentLoad > 30000 -> 30
            recentLoad > 20000 -> 20
            recentLoad > 10000 -> 10
            else               -> 0
        }
        score = score.coerceIn(0, 100)

        val level = when {
            score >= 75 -> FatigueLevel.OVEREXERTED
            score >= 50 -> FatigueLevel.TIRED
            score >= 25 -> FatigueLevel.MODERATE
            else        -> FatigueLevel.RESTED
        }

        val recommendation = when (level) {
            FatigueLevel.RESTED      -> "You're well recovered. Great time for a workout! 💪"
            FatigueLevel.MODERATE    -> "You're active and doing well. Maintain your pace. 👟"
            FatigueLevel.TIRED       -> "Your body is working hard. Consider a light walk or stretching today. 🧘"
            FatigueLevel.OVEREXERTED -> "High activity detected for several days. Rest today to avoid injury! 🛑"
        }

        return FatigueResult(score, level, recommendation, stepList)
    }

    fun calculateCalories(steps: Int, weightKg: Float = 70f): Float =
        steps * Constants.CALORIES_PER_STEP * (weightKg / 70f)

    fun calculateDistance(steps: Int): Float =
        steps * Constants.AVG_STRIDE_METERS
}