package com.bangkitcapstone.tipsntrip.ui.itenerary.day

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.tipsntrip.R
import com.bangkitcapstone.tipsntrip.adapter.itenerary.AgendaAdapter
import com.bangkitcapstone.tipsntrip.data.lib.itenerary.Agenda
import com.bangkitcapstone.tipsntrip.data.lib.itenerary.AttractionsItenerary
import com.bangkitcapstone.tipsntrip.databinding.FragmentDayFourBinding

class DayFourFragment(val agenda: Agenda) : Fragment() {
    private lateinit var binding: FragmentDayFourBinding
    private lateinit var adapter: AgendaAdapter
    private var requiredBudget: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDayFourBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.rvAttraction.layoutManager = layoutManager
        val adapter = AgendaAdapter(agenda.attractions)
        binding.rvAttraction.adapter = adapter
        binding.tvRequiredMoney.text =
            resources.getString(R.string.required_budget, countRequiredBudget(agenda.attractions))
    }

    private fun countRequiredBudget(attractions: ArrayList<AttractionsItenerary>): Int {
        for (attraction in attractions) {
            requiredBudget += attraction.attraction.htm
        }
        return requiredBudget
    }

}