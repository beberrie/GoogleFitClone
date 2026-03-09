package ph.edu.usc24104013.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

object WorkManagerScheduler {

    private const val WORK_NAME = "daily_reset_work"

    fun scheduleDailyReset(context: Context) {
        // Calculate delay until next midnight
        val now = Calendar.getInstance()
        val midnight = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.DAY_OF_YEAR, 1) // next midnight
        }
        val delayMillis = midnight.timeInMillis - now.timeInMillis

        val dailyWorkRequest =
            PeriodicWorkRequestBuilder<DailyResetWorker>(1, TimeUnit.DAYS)
                .setInitialDelay(delayMillis, TimeUnit.MILLISECONDS)
                .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP, // don't replace if already scheduled
            dailyWorkRequest
        )
    }

    fun cancelDailyReset(context: Context) {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }
}