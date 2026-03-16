package ph.edu.usc24104013.ui.insights

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import ph.edu.usc24104013.R
import ph.edu.usc24104013.databinding.FragmentInsightsBinding
import ph.edu.usc24104013.ui.browse.adapter.ExerciseAdapter

class InsightsFragment : Fragment(R.layout.fragment_insights) {

    private var _binding: FragmentInsightsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InsightsViewModel by viewModels()
    private lateinit var exerciseAdapter: ExerciseAdapter

    private val timeOptions = listOf(5, 10, 20, 30, 45, 60)
    private var selectedMinutes = 30
    private var selectedType = "Cardio"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentInsightsBinding.bind(view)

        setupRecyclerView()
        setupSeekBar()
        setupChips()
        setupGenerateButton()
        observeData()
    }

    // Fatigue

    private fun observeData() {
        viewModel.fatigueResult.observe(viewLifecycleOwner) { result ->
            binding.tvFatigueLevel.text     = result.level.label
            binding.tvFatigueScore.text     = "Score: ${result.score} / 100"
            binding.pbFatigueScore.progress = result.score
            binding.tvRecommendation.text   = result.recommendation

            val color = ContextCompat.getColor(requireContext(), result.level.colorRes)
            binding.pbFatigueScore.progressTintList = ColorStateList.valueOf(color)

            if (result.trend.isNotEmpty()) renderTrendChart(result.trend)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.pbLoading.visibility  = if (loading) View.VISIBLE else View.GONE
            binding.btnGenerate.isEnabled = !loading
        }

        viewModel.generatedWorkout.observe(viewLifecycleOwner) { workout ->
            workout ?: return@observe
            binding.cardResult.visibility    = View.VISIBLE
            binding.tvWorkoutTitle.text      = workout.title
            binding.tvEstimatedCalories.text =
                "Estimated: %.0f kcal".format(workout.estimatedCalories)
            exerciseAdapter.submitList(workout.exercises)
        }
    }

    private fun renderTrendChart(stepData: List<Int>) {
        val entries = stepData.mapIndexed { i, steps ->
            Entry(i.toFloat(), steps.toFloat())
        }

        val dataSet = LineDataSet(entries, "Daily Steps").apply {
            color          = Color.parseColor("#6E9AE3")
            setCircleColor(Color.parseColor("#6E9AE3"))
            lineWidth      = 2.5f
            circleRadius   = 4f
            valueTextColor = Color.WHITE
            valueTextSize  = 9f
            setDrawFilled(true)
            fillColor      = Color.parseColor("#1A6E9AE3")
        }

        binding.chartActivityTrend.apply {
            data                  = LineData(dataSet)
            description.isEnabled = false
            legend.textColor      = Color.WHITE
            axisLeft.textColor    = Color.WHITE
            axisRight.isEnabled   = false
            xAxis.textColor       = Color.WHITE
            xAxis.setDrawGridLines(false)
            setBackgroundColor(Color.TRANSPARENT)
            animateX(800)
            invalidate()
        }
    }

    // Smart Workout Generator

    private fun setupRecyclerView() {
        exerciseAdapter = ExerciseAdapter()
        binding.rvExercises.layoutManager = LinearLayoutManager(requireContext())
        binding.rvExercises.adapter = exerciseAdapter
    }

    private fun setupSeekBar() {
        binding.seekbarTime.max      = timeOptions.size - 1
        binding.seekbarTime.progress = timeOptions.indexOf(30)
        binding.tvTimeLabel.text     = "30 min"

        binding.seekbarTime.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                    selectedMinutes      = timeOptions[progress]
                    binding.tvTimeLabel.text = "$selectedMinutes min"
                }
                override fun onStartTrackingTouch(sb: SeekBar?) {}
                override fun onStopTrackingTouch(sb: SeekBar?) {}
            }
        )
    }

    private fun setupChips() {
        binding.chipCardio.isChecked = true
        binding.chipGroupType.setOnCheckedStateChangeListener { _, checkedIds ->
            selectedType = when {
                checkedIds.contains(R.id.chip_cardio)      -> "Cardio"
                checkedIds.contains(R.id.chip_strength)    -> "Strength"
                checkedIds.contains(R.id.chip_flexibility) -> "Flexibility"
                checkedIds.contains(R.id.chip_fullbody)    -> "Full Body"
                else                                       -> "Cardio"
            }
        }
    }

    private fun setupGenerateButton() {
        binding.btnGenerate.setOnClickListener {
            viewModel.generateWorkout(selectedType, selectedMinutes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}