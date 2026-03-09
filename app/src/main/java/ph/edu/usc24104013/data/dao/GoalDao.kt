package ph.edu.usc24104013.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ph.edu.usc24104013.data.entity.GoalEntity

@Dao
interface GoalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(goal: GoalEntity)

    @Update
    suspend fun update(goal: GoalEntity)

    @Query("SELECT * FROM goals LIMIT 1")
    fun getGoal(): LiveData<GoalEntity?>

    @Query("SELECT * FROM goals LIMIT 1")
    suspend fun getGoalOnce(): GoalEntity?
}