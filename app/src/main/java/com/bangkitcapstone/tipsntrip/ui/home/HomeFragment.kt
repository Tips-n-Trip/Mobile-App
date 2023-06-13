package com.bangkitcapstone.tipsntrip.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.tipsntrip.R
import com.bangkitcapstone.tipsntrip.adapter.home.SmallAttractionAdapter
import com.bangkitcapstone.tipsntrip.adapter.home.SmallCityAdapter
import com.bangkitcapstone.tipsntrip.adapter.home.SmallSouvenirAdapter
import com.bangkitcapstone.tipsntrip.data.lib.city.Destination
import com.bangkitcapstone.tipsntrip.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeFragmentViewModel by viewModels<HomeFragmentViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCity.layoutManager = layoutManager
        val layoutManager2 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvAttraction.layoutManager = layoutManager2
        val layoutManager3 =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvSouvenir.layoutManager = layoutManager3
        setupViewModelAction()

        binding.btnMoreCity.setOnClickListener {
            val navController = NavHostFragment.findNavController(this)
            navController.navigate(R.id.navigation_explore)
            val navGraph = navController.navInflater.inflate(R.navigation.main_navigation)
            navGraph.setStartDestination(R.id.navigation_explore)
            navController.graph = navGraph
            val viewPagerPosition = 0
            navController.navigate(R.id.navigation_explore, Bundle().apply {
                putInt("view_pager_position", viewPagerPosition)
            })
        }

        binding.btnMoreAttraction.setOnClickListener {
            val navController = NavHostFragment.findNavController(this)
            navController.navigate(R.id.navigation_explore)
            val navGraph = navController.navInflater.inflate(R.navigation.main_navigation)
            navGraph.setStartDestination(R.id.navigation_explore)
            navController.graph = navGraph
            val viewPagerPosition = 1
            navController.navigate(R.id.navigation_explore, Bundle().apply {
                putInt("view_pager_position", viewPagerPosition)
            })
        }

        binding.btnMoreSouvenir.setOnClickListener {
            val navController = NavHostFragment.findNavController(this)
            navController.navigate(R.id.navigation_explore)
            val navGraph = navController.navInflater.inflate(R.navigation.main_navigation)
            navGraph.setStartDestination(R.id.navigation_explore)
            navController.graph = navGraph
            val viewPagerPosition = 2
            navController.navigate(R.id.navigation_explore, Bundle().apply {
                putInt("view_pager_position", viewPagerPosition)
            })
        }
    }

    private fun setupViewModelAction() {
        homeFragmentViewModel.apply {
            getAllDestination(requireActivity())
            getAllAttraction(requireActivity())
            getAllSouvenir(requireActivity())
            isLoading.observe(viewLifecycleOwner, {
                showLoading(it)
            })
            destination.observe(viewLifecycleOwner, {
                if (it.success && it != null) {
                    val destinationAdapter = SmallCityAdapter(it.destinations)
                    binding.rvCity.adapter = destinationAdapter
                }
            })
            attraction.observe(viewLifecycleOwner, {
                with(it.attractions) {
                    val attractionAdapter = SmallAttractionAdapter(this)
                    binding.rvAttraction.adapter = attractionAdapter
                }
            })
            souvenir.observe(viewLifecycleOwner, {
                with(it.souvenirs) {
                    val souvenir = SmallSouvenirAdapter(this)
                    binding.rvSouvenir.adapter = souvenir
                }
            })
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

}