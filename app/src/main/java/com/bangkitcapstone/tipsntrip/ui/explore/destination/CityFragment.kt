package com.bangkitcapstone.tipsntrip.ui.explore.destination

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.tipsntrip.adapter.explore.FragmentCityAdapter
import com.bangkitcapstone.tipsntrip.data.lib.city.Destination
import com.bangkitcapstone.tipsntrip.databinding.FragmentCityBinding


class CityFragment : Fragment() {
    private lateinit var binding: FragmentCityBinding
    private val cityFragmentViewModel by viewModels<CityDestinationViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
        recyclerView()
        setupAction()
    }

    private fun recyclerView() {
        val orientation = resources.configuration.orientation
        val layoutManager = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager(activity)
        } else {
            GridLayoutManager(activity, 2)
        }
        binding.rvCity.layoutManager = layoutManager
    }

    private fun setupData(input: ArrayList<Destination>) {
        val data = ArrayList<Destination>()
        for (i in input) {
            data.add(i)
        }
        val adapter = FragmentCityAdapter(data)
        binding.rvCity.adapter = adapter
    }

    private fun setupAction() {
        cityFragmentViewModel.apply {
            getAllDataDestination(requireActivity())

            destination.observe(viewLifecycleOwner) {
                setupData(it.destinations)
            }
        }
    }


}
