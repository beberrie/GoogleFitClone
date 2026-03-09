package ph.edu.usc24104013.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ph.edu.usc24104013.data.entity.ActivityEntity

@Dao
interface ActivityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(activity: ActivityEntity)

    @Query("SELECT * FROM activities ORDER BY date DESC")
    fun getAllActivities(): LiveData<List<ActivityEntity>>

    @Query("SELECT * FROM activities WHERE date = :date LIMIT 1")
    suspend fun getActivityByDate(date: String): ActivityEntity?

    @Query("SELECT * FROM activities WHERE date >= :startDate ORDER BY date ASC")
    suspend fun getActivitiesSince(startDate: String): List<ActivityEntity>
}