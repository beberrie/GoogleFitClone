package ph.edu.usc24104013.ui.insights

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ph.edu.usc24104013.data.database.FitDB
import ph.edu.usc24104013.repository.ActivityRepo
import ph.edu.usc24104013.utils.HealthCalculator

class InsightViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ActivityRepo(
        FitDB.getDatabase(application).activityDao()
    )

    private val _fatigueResult = MutableLiveData<HealthCalculator.FatigueResult>()
    val fatigueResult: LiveData<HealthCalculator.FatigueResult> = _fatigueResult

    init { loadFatiguePrediction() }

    fun loadFatiguePrediction() = viewModelScope.launch {
        val activities = repository.getActivitiesLastNDays(7)
        _fatigueResult.postValue(HealthCalculator.predictFatigue(activities))
    }
}