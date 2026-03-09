package ph.edu.usc24104013.ui.journal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ph.edu.usc24104013.data.database.FitDB
import ph.edu.usc24104013.repository.ActivityRepo

class JournalViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ActivityRepo(
        FitDB.getDatabase(application).activityDao()
    )
    val allActivities = repository.allActivities
}