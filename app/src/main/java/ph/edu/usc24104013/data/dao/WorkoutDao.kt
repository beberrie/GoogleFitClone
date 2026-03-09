package ph.edu.usc24104013.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ph.edu.usc24104013.data.entity.WorkoutEntity

@Dao
interface WorkoutDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(workout: WorkoutEntity)

    @Query("SELECT * FROM workouts ORDER BY date DESC")
    fun getAllWorkouts(): LiveData<List<WorkoutEntity>>
}