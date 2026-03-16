package ph.edu.usc24104013.ui.insights

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ph.edu.usc24104013.data.database.FitDB
import ph.edu.usc24104013.repository.ActivityRepo
import ph.edu.usc24104013.repository.WorkoutRepo
import ph.edu.usc24104013.utils.HealthCalculator
import ph.edu.usc24104013.utils.WorkoutGenerator

class InsightsViewModel(application: Application) : AndroidViewModel(application) {

    private val db = FitDB.getDatabase(application)

    // ── Fatigue Predictor ────────────────────────────────────
    private val activityRepository = ActivityRepo(db.activityDao())

    private val _fatigueResult = MutableLiveData<HealthCalculator.FatigueResult>()
    val fatigueResult: LiveData<HealthCalculator.FatigueResult> = _fatigueResult

    // ── Smart Workout Generator ──────────────────────────────
    private val workoutRepository = WorkoutRepo(db.exerciseDao())

    private val _generatedWorkout = MutableLiveData<WorkoutGenerator.GeneratedWorkout?>()
    val generatedWorkout: LiveData<WorkoutGenerator.GeneratedWorkout?> = _generatedWorkout

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadFatiguePrediction()
    }

    fun loadFatiguePrediction() = viewModelScope.launch {
        val activities = activityRepository.getActivitiesLastNDays(7)
        _fatigueResult.postValue(HealthCalculator.predictFatigue(activities))
    }

    fun generateWorkout(type: String, minutes: Int) = viewModelScope.launch {
        _isLoading.postValue(true)
        val result = workoutRepository.generateWorkout(type, minutes)
        _generatedWorkout.postValue(result)
        _isLoading.postValue(false)
    }
}