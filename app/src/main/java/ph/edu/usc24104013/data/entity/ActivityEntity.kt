package ph.edu.usc24104013.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val steps: Int,
    val calories: Float,
    val distanceKm: Float,
    val activeMinutes: Int
)