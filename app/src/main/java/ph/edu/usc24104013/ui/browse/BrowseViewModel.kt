package ph.edu.usc24104013.ui.browse

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ph.edu.usc24104013.data.database.FitDB
import ph.edu.usc24104013.repository.WorkoutRepo
import ph.edu.usc24104013.utils.WorkoutGenerator

class BrowseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WorkoutRepo(
        FitDB.getDatabase(application).exerciseDao()
    )

    private val _generatedWorkout = MutableLiveData<WorkoutGenerator.GeneratedWorkout?>()
    val generatedWorkout: LiveData<WorkoutGenerator.GeneratedWorkout?> = _generatedWorkout

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun generateWorkout(type: String, minutes: Int) = viewModelScope.launch {
        _isLoading.postValue(true)
        val result = repository.generateWorkout(type, minutes)
        _generatedWorkout.postValue(result)
        _isLoading.postValue(false)
    }
}