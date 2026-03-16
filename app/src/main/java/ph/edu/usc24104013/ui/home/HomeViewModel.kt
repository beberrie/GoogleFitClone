package ph.edu.usc24104013.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ph.edu.usc24104013.data.database.FitDB
import ph.edu.usc24104013.data.entity.ActivityEntity
import ph.edu.usc24104013.repository.ActivityRepo
import ph.edu.usc24104013.sensor.StepCounterManager
import ph.edu.usc24104013.utils.HealthCalculator
import java.text.SimpleDateFormat
import java.util.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val db         = FitDB.getDatabase(application)
    private val repository = ActivityRepo(db.activityDao())
    val stepCounter        = StepCounterManager(application)

    val allActivities: LiveData<List<ActivityEntity>> = repository.allActivities

    private val _stepGoal = MutableLiveData(10000)
    val stepGoal: LiveData<Int> = _stepGoal

    private var userWeightKg = 70f

    init {
        stepCounter.startListening()
        loadGoal()
    }

    private fun loadGoal() = viewModelScope.launch {
        val goal = db.goalDao().getGoalOnce()
        goal?.let {
            _stepGoal.postValue(it.dailyStepGoal)
            userWeightKg = it.weightKg
        }
    }

    fun saveStepsForToday(steps: Int) = viewModelScope.launch {
        val today    = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val calories = HealthCalculator.calculateCalories(steps, userWeightKg)
        val distance = HealthCalculator.calculateDistance(steps)
        repository.insertActivity(
            ActivityEntity(
                date          = today,
                steps         = steps,
                calories      = calories,
                distanceKm    = distance,
                activeMinutes = steps / 100
            )
        )
    }

    override fun onCleared() {
        super.onCleared()
        stepCounter.stopListening()
    }
}