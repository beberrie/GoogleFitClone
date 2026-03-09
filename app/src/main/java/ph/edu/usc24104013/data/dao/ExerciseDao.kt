package ph.edu.usc24104013.data.dao

import androidx.room.*
import ph.edu.usc24104013.data.entity.ExerciseEntity

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(exercises: List<ExerciseEntity>)

    @Query("SELECT * FROM exercises WHERE type = :type AND phase = 'main' ORDER BY RANDOM() LIMIT :limit")
    suspend fun getMainExercises(type: String, limit: Int): List<ExerciseEntity>

    @Query("SELECT * FROM exercises WHERE phase = 'warmup' ORDER BY RANDOM() LIMIT :limit")
    suspend fun getWarmupExercises(limit: Int): List<ExerciseEntity>

    @Query("SELECT * FROM exercises WHERE phase = 'cooldown' ORDER BY RANDOM() LIMIT :limit")
    suspend fun getCooldownExercises(limit: Int): List<ExerciseEntity>

    @Query("SELECT COUNT(*) FROM exercises")
    suspend fun getCount(): Int
}