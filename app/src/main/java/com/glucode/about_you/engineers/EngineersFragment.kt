package com.glucode.about_you.engineers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.glucode.about_you.R
import com.glucode.about_you.databinding.FragmentEngineersBinding
import com.glucode.about_you.engineers.models.Engineer
import com.glucode.about_you.engineers.models.QuickStatsEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EngineersFragment : Fragment() {
    private lateinit var binding: FragmentEngineersBinding
    private val engineerViewModel: EngineerViewmodel by activityViewModels()

    companion object {
        const val ENGINEER_NAME_ARGUMENT = "name"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEngineersBinding.inflate(inflater, container, false)

        engineerViewModel.loadEngineers()
        engineerViewModel.engineers.observe(viewLifecycleOwner) {
            setUpEngineersList(it)
        }

        setUpMenu()
        return binding.root
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

    private fun setUpMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_engineers, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_years -> {
                        engineerViewModel.sortEngineersByStat(QuickStatsEnum.YEARS)
                        true
                    }

                    R.id.action_coffees -> {
                        engineerViewModel.sortEngineersByStat(QuickStatsEnum.COFFEES)
                        true
                    }

                    R.id.action_bugs -> {
                        engineerViewModel.sortEngineersByStat(QuickStatsEnum.BUGS)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}