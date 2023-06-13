package com.bangkitcapstone.tipsntrip.adapter.viewpager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkitcapstone.tipsntrip.data.lib.itenerary.Agenda
import com.bangkitcapstone.tipsntrip.ui.itenerary.day.*

class DaySectionPagerAdapter(activity: AppCompatActivity, val tabCount : Int) : FragmentStateAdapter(activity) {
    var listAgenda = ArrayList<Agenda>()
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DayOneFragment(listAgenda[0])
            1 -> DayTwoFragment(listAgenda[1])
            2 -> DayThreeFragment(listAgenda[2])
            3 -> DayFourFragment(listAgenda[3])
            else -> DayFiveFragment(listAgenda[4])
        }
    }

    override fun getItemCount(): Int {
        return tabCount
    }

    override fun getItemId(position: Int): Long {
        // Return a unique identifier for each fragment position
        return when (position) {
            0 -> 0
            1 -> 1
            2 -> 2
            3 -> 3
            else -> 4
        }
    }
}