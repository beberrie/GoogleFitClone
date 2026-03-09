package ph.edu.usc24104013.repository

import ph.edu.usc24104013.data.dao.ActivityDao
import ph.edu.usc24104013.data.entity.ActivityEntity
import java.text.SimpleDateFormat
import java.util.*

class ActivityRepo(private val activityDao: ActivityDao) {

    val allActivities = activityDao.getAllActivities()

    suspend fun insertActivity(activity: ActivityEntity) {
        activityDao.insert(activity)
    }

    suspend fun getTodayActivity(): ActivityEntity? {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return activityDao.getActivityByDate(today)
    }

    suspend fun getActivitiesLastNDays(days: Int): List<ActivityEntity> {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -days)
        val startDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(cal.time)
        return activityDao.getActivitiesSince(startDate)
    }
}