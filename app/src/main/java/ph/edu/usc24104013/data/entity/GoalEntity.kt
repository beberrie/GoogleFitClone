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
    val badges: String
)