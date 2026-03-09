package ph.edu.usc24104013.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ph.edu.usc24104013.data.database.FitDB
import ph.edu.usc24104013.data.entity.ActivityEntity
import ph.edu.usc24104013.repository.ActivityRepo
import ph.edu.usc24104013.utils.HealthCalculator
import java.text.SimpleDateFormat
import java.util.*

class DailyResetWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        return try {
            val db         = FitDB.getDatabase(applicationContext)
            val repository = ActivityRepo(db.activityDao())

            // Get today's date
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            // Check if we already saved today's activity
            val existing = repository.getTodayActivity()

            if (existing == null) {
                // Save a zero-step entry so the day is recorded
                repository.insertActivity(
                    ActivityEntity(
                        date          = today,
                        steps         = 0,
                        calories      = 0f,
                        distanceKm    = 0f,
                        activeMinutes = 0
                    )
                )
            }

            // Check if yesterday's goal was met and update streak
            updateStreak(db, repository)

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private suspend fun updateStreak(
        db: FitDB,
        repository: ActivityRepo
    ) {
        val goal = db.goalDao().getGoalOnce() ?: return

        // Get yesterday's date
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -1)
        val yesterday = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)

        val yesterdayActivity = db.activityDao().getActivityByDate(yesterday)

        if (yesterdayActivity != null && yesterdayActivity.steps >= goal.dailyStepGoal) {
            // Goal was met yesterday — increment streak
            val newStreak  = goal.currentStreak + 1
            val newLongest = maxOf(newStreak, goal.longestStreak)
            val newBadges  = buildBadges(newStreak)

            db.goalDao().update(
                goal.copy(
                    currentStreak   = newStreak,
                    longestStreak   = newLongest,
                    lastGoalMetDate = yesterday,
                    badges          = newBadges
                )
            )
        } else {
            // Goal was NOT met — reset streak
            db.goalDao().update(goal.copy(currentStreak = 0))
        }
    }

    private fun buildBadges(streak: Int): String {
        val badges = mutableListOf<String>()
        if (streak >= 3)  badges.add("3DAY")
        if (streak >= 7)  badges.add("7DAY")
        if (streak >= 30) badges.add("30DAY")
        return badges.joinToString(",")
    }
}