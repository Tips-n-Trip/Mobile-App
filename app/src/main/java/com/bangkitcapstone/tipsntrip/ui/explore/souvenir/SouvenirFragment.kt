package com.bangkitcapstone.tipsntrip.ui.explore.souvenir

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.tipsntrip.adapter.explore.FragmentSouvenirAdapter
import com.bangkitcapstone.tipsntrip.adapter.loading.LoadingStateAdapter
import com.bangkitcapstone.tipsntrip.data.remote.api.ApiConfig
import com.bangkitcapstone.tipsntrip.data.remote.repository.ExplorePagingRepository
import com.bangkitcapstone.tipsntrip.databinding.FragmentSouvenirBinding
import com.bangkitcapstone.tipsntrip.ui.viewmodelfactory.PagingSourceFactory

class SouvenirFragment : Fragment() {
    private lateinit var binding: FragmentSouvenirBinding
    private lateinit var souvenirFragmentViewModel: SouvenirFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSouvenirBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
        recyclerView()
        setupData()
    }

    private fun recyclerView() {
        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvSouvenir.layoutManager = layoutManager
    }

    private fun setupData() {
        val adapter = FragmentSouvenirAdapter()
        binding.rvSouvenir.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )
        val repository = ExplorePagingRepository(ApiConfig.getApiService(requireContext()))
        souvenirFragmentViewModel = ViewModelProvider(
            this@SouvenirFragment,
            PagingSourceFactory(repository)
        )[SouvenirFragmentViewModel::class.java]


        souvenirFragmentViewModel.dataSouvenir.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }


    companion object {

    }
}