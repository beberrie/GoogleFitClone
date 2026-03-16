package ph.edu.usc24104013.ui.home

import android.app.Dialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ph.edu.usc24104013.databinding.DialogLogSleepBinding

class LogSleepBottomSheet : BottomSheetDialogFragment() {

    private var _binding: DialogLogSleepBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val sheet = dialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet)
            sheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                it.layoutParams.height = (resources.displayMetrics.heightPixels * 0.75).toInt()
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
        _binding = DialogLogSleepBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCloseSleep.setOnClickListener { dismiss() }

        binding.btnSaveSleep.setOnClickListener {
            val bedtime  = binding.etSleepBedtime.text.toString().ifBlank { "" }
            val wakeup   = binding.etSleepWakeup.text.toString().ifBlank { "" }
            val duration = binding.etSleepDuration.text.toString()

            if (bedtime.isEmpty() && wakeup.isEmpty() && duration.isEmpty()) {
                Toast.makeText(requireContext(),
                    "Please fill in at least one field.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Visual only for now — just dismiss with confirmation
            Toast.makeText(requireContext(), "Sleep logged! ✅", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}