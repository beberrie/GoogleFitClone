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
        setupSaveButton()
    }

    private fun observeData() {
        viewModel.goal.observe(viewLifecycleOwner) { goal ->
            goal ?: return@observe
            binding.etStepGoal.setText(goal.dailyStepGoal.toString())
            binding.etCalorieGoal.setText(goal.dailyCalorieGoal.toInt().toString())
            binding.tvStreak.text = "${goal.currentStreak} days"
        }

        viewModel.saved.observe(viewLifecycleOwner) { saved ->
            if (saved) Toast.makeText(requireContext(), "Goals saved! ✅", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupSaveButton() {
        binding.btnSaveGoals.setOnClickListener {
            val steps    = binding.etStepGoal.text.toString().toIntOrNull() ?: 10000
            val calories = binding.etCalorieGoal.text.toString().toFloatOrNull() ?: 500f
            viewModel.saveGoals(steps, calories)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}