package ph.edu.usc24104013.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ph.edu.usc24104013.R
import ph.edu.usc24104013.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        observeData()
        setupGoalEditors()
        setupSaveButton()
    }

    private fun observeData() {
        viewModel.goal.observe(viewLifecycleOwner) { goal ->
            goal ?: return@observe
            binding.etStepGoal.setText(goal.dailyStepGoal.toString())
            binding.etCalorieGoal.setText(goal.dailyCalorieGoal.toInt().toString())
            binding.etBedtime.setText(goal.bedtime)
            binding.etWakeup.setText(goal.wakeup)
            binding.switchBedtime.isChecked = goal.bedtimeEnabled
            binding.etWeight.setText(if (goal.weightKg > 0) goal.weightKg.toString() else "")
            binding.etHeight.setText(if (goal.heightCm > 0) goal.heightCm.toString() else "")
        }

        viewModel.saved.observe(viewLifecycleOwner) { saved ->
            if (saved) Toast.makeText(requireContext(), "Saved! ✅", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupGoalEditors() {
        // Steps edit button
        binding.btnEditSteps.setOnClickListener {
            val current = binding.etStepGoal.text.toString().toIntOrNull() ?: 10000
            SetGoalBottomSheet.newInstance(
                title        = "Steps",
                subtitle     = "Placeholder subtitle",
                currentValue = current,
                increment    = 500,
                onGoalSet    = { newValue ->
                    binding.etStepGoal.setText(newValue.toString())
                }
            ).show(parentFragmentManager, "SetStepsGoal")
        }

        // Heart Points edit button
        binding.btnEditHeart.setOnClickListener {
            val current = binding.etCalorieGoal.text.toString().toIntOrNull() ?: 150
            SetGoalBottomSheet.newInstance(
                title        = "Heart Points",
                subtitle     = "Placeholder subtitle",
                currentValue = current,
                increment    = 50,
                onGoalSet    = { newValue ->
                    binding.etCalorieGoal.setText(newValue.toString())
                }
            ).show(parentFragmentManager, "SetHeartGoal")
        }
    }

    private fun setupSaveButton() {
        binding.btnSaveGoals.setOnClickListener {
            viewModel.saveAll(
                stepGoal       = binding.etStepGoal.text.toString().toIntOrNull() ?: 10000,
                calorieGoal    = binding.etCalorieGoal.text.toString().toFloatOrNull() ?: 150f,
                bedtime        = binding.etBedtime.text.toString().ifBlank { "10:00 PM" },
                wakeup         = binding.etWakeup.text.toString().ifBlank { "6:00 AM" },
                bedtimeEnabled = binding.switchBedtime.isChecked,
                weightKg       = binding.etWeight.text.toString().toFloatOrNull() ?: 70f,
                heightCm       = binding.etHeight.text.toString().toFloatOrNull() ?: 170f
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}