package com.glucode.about_you.engineers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.glucode.about_you.EngineerViewmodel
import com.glucode.about_you.R
import com.glucode.about_you.databinding.FragmentEngineersBinding
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.engineers.models.QuickStatsEnum

class EngineersFragment : Fragment() {
    private lateinit var binding: FragmentEngineersBinding
    private val engineerViewModel: EngineerViewmodel by viewModels()

    companion object {
        const val ENGINEER_NAME_ARGUMENT = "name"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEngineersBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        engineerViewModel.engineers.observe(viewLifecycleOwner) {
            setUpEngineersList(it)
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_engineers, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_years -> engineerViewModel.sortEngineersByStat(QuickStatsEnum.YEARS)
            R.id.action_coffees -> engineerViewModel.sortEngineersByStat(QuickStatsEnum.COFFEES)
            R.id.action_bugs -> engineerViewModel.sortEngineersByStat(QuickStatsEnum.BUGS)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setUpEngineersList(engineers: List<Engineer>) {
        binding.list.adapter = EngineersRecyclerViewAdapter(engineers) {
            goToAbout(it)
        }
        if (binding.list.itemDecorationCount != 1) {
            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            binding.list.addItemDecoration(dividerItemDecoration)
        }
    }

    private fun goToAbout(engineer: Engineer) {
        val bundle = Bundle().apply {
            putString(ENGINEER_NAME_ARGUMENT, engineer.name)
        }
        findNavController().navigate(R.id.action_engineersFragment_to_aboutFragment, bundle)
    }
}