package com.bangkitcapstone.tipsntrip.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bangkitcapstone.tipsntrip.ui.explore.attraction.AttractionFragment
import com.bangkitcapstone.tipsntrip.ui.explore.destination.CityFragment
import com.bangkitcapstone.tipsntrip.ui.explore.souvenir.SouvenirFragment
import com.bangkitcapstone.tipsntrip.ui.explore.ViewPagerDataListener

class SectionsPagerAdapter(activity: AppCompatActivity, private var viewPagerDataListener: ViewPagerDataListener,)
    : FragmentStateAdapter(activity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CityFragment().apply { setViewPagerDataListener(viewPagerDataListener) }
            1 -> AttractionFragment().apply { setViewPagerDataListener(viewPagerDataListener) }
            else -> SouvenirFragment().apply { setViewPagerDataListener(viewPagerDataListener) }
        }
    }
    fun setViewPagerDataListener(listener: ViewPagerDataListener?) {
        if (listener != null) {
            viewPagerDataListener = listener
        }
    }
    override fun getItemCount(): Int {
        return 3
    }

    override fun getItemId(position: Int): Long {
        // Return a unique identifier for each fragment position
        return when (position) {
            0 -> 0 // CityFragment identifier
            1 -> 1 // AttractionFragment identifier
            else -> 2 // SouvenirFragment identifier
        }
    }
}