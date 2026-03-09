package ph.edu.usc24104013.ui.browse

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.usc24104013.R
import ph.edu.usc24104013.databinding.FragmentBrowseBinding
import ph.edu.usc24104013.ui.browse.adapter.ExerciseAdapter

class BrowseFragment : Fragment(R.layout.fragment_browse) {

    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!
    private val viewModel: BrowseViewModel by viewModels()
    private lateinit var exerciseAdapter: ExerciseAdapter

    // Maps SeekBar position → minutes
    private val timeOptions = listOf(5, 10, 20, 30, 45, 60)
    private var selectedMinutes = 30
    private var selectedType = "Cardio"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBrowseBinding.bind(view)

        setupRecyclerView()
        setupSeekBar()
        setupChips()
        setupGenerateButton()
        observeData()
    }

    private fun setupRecyclerView() {
        exerciseAdapter = ExerciseAdapter()
        binding.rvExercises.layoutManager = LinearLayoutManager(requireContext())
        binding.rvExercises.adapter = exerciseAdapter
    }

    private fun setupSeekBar() {
        binding.seekbarTime.max = timeOptions.size - 1
        binding.seekbarTime.progress = timeOptions.indexOf(30)
        binding.tvTimeLabel.text = "30 min"

        binding.seekbarTime.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                selectedMinutes = timeOptions[progress]
                binding.tvTimeLabel.text = "$selectedMinutes min"
            }
            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })
    }

    private fun setupChips() {
        binding.chipCardio.isChecked = true
        binding.chipGroupType.setOnCheckedStateChangeListener { _, checkedIds ->
            selectedType = when {
                checkedIds.contains(R.id.chip_cardio)      -> "Cardio"
                checkedIds.contains(R.id.chip_strength)    -> "Strength"
                checkedIds.contains(R.id.chip_flexibility) -> "Flexibility"
                checkedIds.contains(R.id.chip_fullbody)    -> "Full Body"
                else -> "Cardio"
            }
        }
    }

    private fun setupGenerateButton() {
        binding.btnGenerate.setOnClickListener {
            viewModel.generateWorkout(selectedType, selectedMinutes)
        }
    }

    private fun observeData() {
        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.pbLoading.visibility  = if (loading) View.VISIBLE else View.GONE
            binding.btnGenerate.isEnabled = !loading
        }

        viewModel.generatedWorkout.observe(viewLifecycleOwner) { workout ->
            workout ?: return@observe
            binding.cardResult.visibility       = View.VISIBLE
            binding.tvWorkoutTitle.text         = workout.title
            binding.tvEstimatedCalories.text    =
                "🔥 Estimated: %.0f kcal".format(workout.estimatedCalories)
            exerciseAdapter.submitList(workout.exercises)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}