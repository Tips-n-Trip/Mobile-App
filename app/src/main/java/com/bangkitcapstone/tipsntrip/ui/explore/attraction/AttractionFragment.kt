package com.bangkitcapstone.tipsntrip.ui.explore.attraction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.tipsntrip.adapter.explore.FragmentAttractionAdapter
import com.bangkitcapstone.tipsntrip.adapter.loading.LoadingStateAdapter
import com.bangkitcapstone.tipsntrip.data.remote.api.ApiConfig
import com.bangkitcapstone.tipsntrip.data.remote.repository.ExplorePagingRepository
import com.bangkitcapstone.tipsntrip.databinding.FragmentAttractionBinding
import com.bangkitcapstone.tipsntrip.ui.viewmodelfactory.PagingSourceFactory

class AttractionFragment : Fragment() {

    private lateinit var binding: FragmentAttractionBinding
    private lateinit var attractionFragmentViewModel: AttractionFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAttractionBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView()
        setupData()
    }

    private fun recyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvAttraction.layoutManager = layoutManager
    }

    private fun setupData() {
        val adapter = FragmentAttractionAdapter()
        binding.rvAttraction.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        val repository = ExplorePagingRepository(ApiConfig.getApiService(requireContext()))
        attractionFragmentViewModel =
            ViewModelProvider(
                this@AttractionFragment,
                PagingSourceFactory(repository)
            )[AttractionFragmentViewModel::class.java]
        attractionFragmentViewModel.dataAttraction.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    companion object {
    }
}