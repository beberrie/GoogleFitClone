package ph.edu.usc24104013.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.*
import ph.edu.usc24104013.data.database.FitDB
import ph.edu.usc24104013.data.entity.ActivityEntity
import ph.edu.usc24104013.databinding.DialogLogActivityBinding
import ph.edu.usc24104013.repository.ActivityRepo
import java.text.SimpleDateFormat
import java.util.*

class LogActivityBottomSheet : BottomSheetDialogFragment() {

    private var _binding: DialogLogActivityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val sheet = (dialog as BottomSheetDialog)
                .behavior
            sheet.state         = BottomSheetBehavior.STATE_EXPANDED
            sheet.skipCollapsed = true
            sheet.peekHeight    = (resources.displayMetrics.heightPixels * 0.88).toInt()
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogLogActivityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCloseLog.setOnClickListener { dismiss() }

        binding.btnLogSave.setOnClickListener {
            val steps    = binding.etLogSteps.text.toString().toIntOrNull() ?: 0
            val calories = binding.etLogCalories.text.toString().toFloatOrNull() ?: 0f
            val distance = binding.etLogDistance.text.toString().toFloatOrNull() ?: 0f
            val minutes  = binding.etLogMinutes.text.toString().toIntOrNull() ?: 0

            if (steps == 0 && calories == 0f) {
                Toast.makeText(requireContext(),
                    "Please enter at least steps or calories.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val repo  = ActivityRepo(
                FitDB.getDatabase(requireContext()).activityDao()
            )

            CoroutineScope(Dispatchers.IO).launch {
                repo.insertActivity(ActivityEntity(
                    date          = today,
                    steps         = steps,
                    calories      = calories,
                    distanceKm    = distance,
                    activeMinutes = minutes
                ))
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(),
                        "Activity saved! ✅", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}