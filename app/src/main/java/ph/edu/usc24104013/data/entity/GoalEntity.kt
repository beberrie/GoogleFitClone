package ph.edu.usc24104013.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "goals")
data class GoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dailyStepGoal: Int,
    val dailyCalorieGoal: Float,
    val currentStreak: Int,
    val longestStreak: Int,
    val lastGoalMetDate: String,
    val badges: String,
    // New fields
    val bedtime: String,        // e.g. "10:00 PM"
    val wakeup: String,         // e.g. "6:00 AM"
    val bedtimeEnabled: Boolean,
    val weightKg: Float,        // used in calorie calculation
    val heightCm: Float         // stored only
)