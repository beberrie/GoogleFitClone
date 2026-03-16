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

        setWeekRange()
        observeData()
    }

    private fun setWeekRange() {
        val cal   = Calendar.getInstance()
        val end   = SimpleDateFormat("MMM d", Locale.getDefault()).format(cal.time)
        cal.add(Calendar.DAY_OF_YEAR, -6)
        val start = SimpleDateFormat("MMM d", Locale.getDefault()).format(cal.time)
        binding.textView4b.text = "$start - $end"
    }

    private fun observeData() {
        // Steps + rings
        viewModel.stepCounter.steps.observe(viewLifecycleOwner) { steps ->
            val goalSteps = viewModel.stepGoal.value ?: 10000

            // Center labels inside rings
            binding.nSteps.text = "%,d".format(steps)
            binding.nHeart.text = "${steps / 100}"

            // Ring progress
            binding.circularProgress.stepsProgress = steps / goalSteps.toFloat()
            binding.circularProgress.heartProgress = (steps / 100f / 150f).coerceAtMost(1f)
        }

        // Daily goals card — goals achieved this week
        viewModel.allActivities.observe(viewLifecycleOwner) { activities ->
            val goalsThisWeek = activities.take(7).count {
                it.steps >= (viewModel.stepGoal.value ?: 10000)
            }
            binding.tvSteps.text = "$goalsThisWeek/7"

            // Weekly target card
            val weeklySteps = activities.take(7).sumOf { it.steps }
            binding.tvStepsWeekly.text = "$weeklySteps of 150"
            binding.pbSteps.progress   = weeklySteps
        }

        viewModel.stepGoal.observe(viewLifecycleOwner) { goal ->
            binding.pbSteps.max = goal * 7
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}