package ph.edu.usc24104013.ui.journal

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ph.edu.usc24104013.R
import ph.edu.usc24104013.databinding.FragmentJournalBinding
import ph.edu.usc24104013.ui.journal.adapter.JournalAdapter

class JournalFragment : Fragment(R.layout.fragment_journal) {

    private var _binding: FragmentJournalBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JournalViewModel by viewModels()
    private lateinit var adapter: JournalAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentJournalBinding.bind(view)

        setupRecyclerView()
        observeData()
    }

    private fun setupRecyclerView() {
        adapter = JournalAdapter()
        binding.rvJournal.layoutManager = LinearLayoutManager(requireContext())
        binding.rvJournal.adapter = adapter
    }

    private fun observeData() {
        viewModel.allActivities.observe(viewLifecycleOwner) { activities ->
            adapter.submitList(activities)
            binding.tvEmptyJournal.visibility =
                if (activities.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}