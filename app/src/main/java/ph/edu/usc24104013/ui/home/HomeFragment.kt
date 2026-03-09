package ph.edu.usc24104013.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ph.edu.usc24104013.R
import ph.edu.usc24104013.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        setGreeting()
        observeData()
    }

    private fun setGreeting() {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        binding.tvGreeting.text = when {
            hour < 12 -> "Good Morning 👋"
            hour < 17 -> "Good Afternoon 👋"
            else      -> "Good Evening 👋"
        }
        binding.tvDate.text =
            SimpleDateFormat("EEEE, MMMM d yyyy", Locale.getDefault()).format(Date())
    }

    private fun observeData() {
        // Steps
        viewModel.stepCounter.steps.observe(viewLifecycleOwner) { steps ->
            binding.tvSteps.text = steps.toString()
            binding.pbSteps.progress = steps
            binding.tvActiveMinutes.text = "${steps / 100} min"
        }

        // Step goal
        viewModel.stepGoal.observe(viewLifecycleOwner) { goal ->
            binding.pbSteps.max = goal
            binding.tvStepGoal.text = "Goal: ${"%,d".format(goal)} steps"
        }

        // Calories
        viewModel.calories.observe(viewLifecycleOwner) { cal ->
            binding.tvCalories.text = "%.0f".format(cal)
        }

        // Distance
        viewModel.distance.observe(viewLifecycleOwner) { km ->
            binding.tvDistance.text = "%.2f".format(km)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}