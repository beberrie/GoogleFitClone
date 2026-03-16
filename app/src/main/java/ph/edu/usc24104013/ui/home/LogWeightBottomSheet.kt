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
import ph.edu.usc24104013.databinding.DialogLogWeightBinding

class LogWeightBottomSheet : BottomSheetDialogFragment() {

    private var _binding: DialogLogWeightBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val sheet = dialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet)
            sheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                it.layoutParams.height = (resources.displayMetrics.heightPixels * 0.5).toInt()
                behavior.state         = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogLogWeightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCloseWeight.setOnClickListener { dismiss() }

        binding.btnSaveWeight.setOnClickListener {
            val weight = binding.etWeightValue.text.toString().toFloatOrNull()

            if (weight == null || weight <= 0f) {
                Toast.makeText(requireContext(),
                    "Please enter a valid weight.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Update weight in GoalEntity
            val db = FitDB.getDatabase(requireContext())
            CoroutineScope(Dispatchers.IO).launch {
                val goal = db.goalDao().getGoalOnce()
                goal?.let { db.goalDao().update(it.copy(weightKg = weight)) }
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(),
                        "Weight saved! ✅", Toast.LENGTH_SHORT).show()
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