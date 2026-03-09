package ph.edu.usc24104013.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ph.edu.usc24104013.data.dao.ActivityDao
import ph.edu.usc24104013.data.dao.ExerciseDao
import ph.edu.usc24104013.data.dao.GoalDao
import ph.edu.usc24104013.data.dao.WorkoutDao
import ph.edu.usc24104013.data.entity.ActivityEntity
import ph.edu.usc24104013.data.entity.ExerciseEntity
import ph.edu.usc24104013.data.entity.GoalEntity
import ph.edu.usc24104013.data.entity.WorkoutEntity
import ph.edu.usc24104013.utils.Constants

@Database(
    entities = [
        ActivityEntity::class,
        WorkoutEntity::class,
        GoalEntity::class,
        ExerciseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FitDB : RoomDatabase() {

    abstract fun activityDao(): ActivityDao
    abstract fun workoutDao(): WorkoutDao
    abstract fun goalDao(): GoalDao
    abstract fun exerciseDao(): ExerciseDao

    companion object {
        @Volatile private var INSTANCE: FitDB? = null

        fun getDatabase(context: Context): FitDB {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    FitDB::class.java,
                    Constants.DATABASE_NAME
                )
                    .addCallback(seedCallback)
                    .build()
                    .also { INSTANCE = it }
            }
        }

        private val seedCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    INSTANCE?.let { database ->
                        // Seed default goal
                        database.goalDao().insert(
                            GoalEntity(
                                dailyStepGoal    = Constants.DEFAULT_STEP_GOAL,
                                dailyCalorieGoal = Constants.DEFAULT_CALORIE_GOAL,
                                currentStreak    = 0,
                                longestStreak    = 0,
                                lastGoalMetDate  = "",
                                badges           = ""
                            )
                        )
                        // Seed exercise library
                        database.exerciseDao().insertAll(SeedData.exercises)
                    }
                }
            }
        }
    }
}