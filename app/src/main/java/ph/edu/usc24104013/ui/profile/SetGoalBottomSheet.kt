package ph.edu.usc24104013.ui.profile

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ph.edu.usc24104013.databinding.DialogSetGoalBinding

class SetGoalBottomSheet : BottomSheetDialogFragment() {

    private var _binding: DialogSetGoalBinding? = null
    private val binding get() = _binding!!

    // Config passed in from fragment
    private var title       = ""
    private var subtitle    = ""
    private var currentValue = 0
    private var increment   = 1
    private var onGoalSet: ((Int) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener {
            val bottomSheet = dialog
                .findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                // Set height to 88% of screen
                val screenHeight = resources.displayMetrics.heightPixels
                it.layoutParams.height = (screenHeight * 0.88).toInt()
                behavior.state      = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
            }
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogSetGoalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Populate
        binding.tvGoalTitle.text    = title
        binding.tvGoalSubtitle.text = subtitle
        binding.etGoalValue.setText(currentValue.toString())

        // Close
        binding.btnClose.setOnClickListener { dismiss() }

        // Minus
        binding.btnMinus.setOnClickListener {
            val current = binding.etGoalValue.text.toString().toIntOrNull() ?: currentValue
            val newVal  = (current - increment).coerceAtLeast(0)
            binding.etGoalValue.setText(newVal.toString())
        }

        // Plus
        binding.btnPlus.setOnClickListener {
            val current = binding.etGoalValue.text.toString().toIntOrNull() ?: currentValue
            binding.etGoalValue.setText((current + increment).toString())
        }

        // Set goal
        binding.btnSetGoal.setOnClickListener {
            val value = binding.etGoalValue.text.toString().toIntOrNull() ?: currentValue
            onGoalSet?.invoke(value)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(
            title: String,
            subtitle: String,
            currentValue: Int,
            increment: Int,
            onGoalSet: (Int) -> Unit
        ): SetGoalBottomSheet {
            return SetGoalBottomSheet().apply {
                this.title        = title
                this.subtitle     = subtitle
                this.currentValue = currentValue
                this.increment    = increment
                this.onGoalSet    = onGoalSet
            }
        }
    }
}