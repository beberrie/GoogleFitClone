package ph.edu.usc24104013.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val type: String,
    val phase: String,
    val durationSeconds: Int,
    val description: String,
    val caloriesPerMinute: Float
)