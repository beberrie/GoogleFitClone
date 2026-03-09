package ph.edu.usc24104013.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ph.edu.usc24104013.data.database.FitDB
import ph.edu.usc24104013.data.entity.GoalEntity

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val db = FitDB.getDatabase(application)

    val goal: LiveData<GoalEntity?> = db.goalDao().getGoal()

    private val _saved = MutableLiveData(false)
    val saved: LiveData<Boolean> = _saved

    fun saveGoals(stepGoal: Int, calorieGoal: Float) = viewModelScope.launch {
        val existing = db.goalDao().getGoalOnce()
        if (existing != null) {
            db.goalDao().update(existing.copy(
                dailyStepGoal    = stepGoal,
                dailyCalorieGoal = calorieGoal
            ))
        } else {
            db.goalDao().insert(GoalEntity(
                dailyStepGoal    = stepGoal,
                dailyCalorieGoal = calorieGoal,
                currentStreak    = 0,
                longestStreak    = 0,
                lastGoalMetDate  = "",
                badges           = ""
            ))
        }
        _saved.postValue(true)
    }
}