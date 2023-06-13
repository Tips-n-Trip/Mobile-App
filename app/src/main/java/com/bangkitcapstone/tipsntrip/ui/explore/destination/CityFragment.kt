package com.bangkitcapstone.tipsntrip.ui.explore.destination

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.tipsntrip.adapter.explore.FragmentCityAdapter
import com.bangkitcapstone.tipsntrip.adapter.loading.LoadingStateAdapter
import com.bangkitcapstone.tipsntrip.data.remote.api.ApiConfig
import com.bangkitcapstone.tipsntrip.data.remote.repository.ExplorePagingRepository
import com.bangkitcapstone.tipsntrip.databinding.FragmentCityBinding
import com.bangkitcapstone.tipsntrip.ui.viewmodelfactory.PagingSourceFactory


class CityFragment : Fragment() {
    private lateinit var binding: FragmentCityBinding
    private lateinit var cityFragmentViewModel: CityDestinationViewModel
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
        setupData()
    }

    private fun recyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        binding.rvCity.layoutManager = layoutManager
    }

    private fun setupData() {
        val adapter = FragmentCityAdapter()
        binding.rvCity.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        val repository = ExplorePagingRepository(ApiConfig.getApiService(requireContext()))
        cityFragmentViewModel = ViewModelProvider(
            this@CityFragment,
            PagingSourceFactory(repository)
        )[CityDestinationViewModel::class.java]

        cityFragmentViewModel.cityDestination.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }


}