package ph.edu.usc24104013.ui.insights

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import ph.edu.usc24104013.R
import ph.edu.usc24104013.databinding.FragmentInsightBinding
import ph.edu.usc24104013.utils.HealthCalculator

class InsightFragment : Fragment(R.layout.fragment_insight) {

    private var _binding: FragmentInsightBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InsightViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentInsightBinding.bind(view)

        observeData()
    }

    private fun observeData() {
        viewModel.fatigueResult.observe(viewLifecycleOwner) { result ->
            binding.tvFatigueLevel.text    = result.level.label
            binding.tvFatigueScore.text    = "Score: ${result.score} / 100"
            binding.pbFatigueScore.progress = result.score
            binding.tvRecommendation.text  = result.recommendation

            // Color the progress bar
            val color = ContextCompat.getColor(requireContext(), result.level.colorRes)
            binding.pbFatigueScore.progressTintList = ColorStateList.valueOf(color)

            // Render chart
            if (result.trend.isNotEmpty()) renderTrendChart(result.trend)
        }
    }

    private fun renderTrendChart(stepData: List<Int>) {
        val entries = stepData.mapIndexed { i, steps ->
            Entry(i.toFloat(), steps.toFloat())
        }

        val dataSet = LineDataSet(entries, "Daily Steps").apply {
            color              = Color.parseColor("#4FC3F7")
            setCircleColor(Color.parseColor("#4FC3F7"))
            lineWidth          = 2.5f
            circleRadius       = 4f
            valueTextColor     = Color.WHITE
            valueTextSize      = 9f
            setDrawFilled(true)
            fillColor          = Color.parseColor("#1A4FC3F7")
        }

        binding.chartActivityTrend.apply {
            data = LineData(dataSet)
            description.isEnabled   = false
            legend.textColor        = Color.WHITE
            axisLeft.textColor      = Color.WHITE
            axisRight.isEnabled     = false
            xAxis.textColor         = Color.WHITE
            xAxis.setDrawGridLines(false)
            setBackgroundColor(Color.TRANSPARENT)
            animateX(800)
            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}