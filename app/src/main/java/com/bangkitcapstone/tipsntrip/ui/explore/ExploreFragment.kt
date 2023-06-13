package com.bangkitcapstone.tipsntrip.ui.explore

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bangkitcapstone.tipsntrip.R
import com.bangkitcapstone.tipsntrip.adapter.SectionsPagerAdapter
import com.bangkitcapstone.tipsntrip.databinding.FragmentExploreBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var tabs: TabLayout
    private var viewPagerPosition: Int = 0
    private var viewPagerDataListener: ViewPagerDataListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentExploreBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

        arguments?.let {
            viewPagerPosition = it.getInt(ARG_VIEW_PAGER_POSITION, 0)
        }

        viewPagerDataListener = object : ViewPagerDataListener {
            override fun onPageChanged(position: Int) {
                viewPager.currentItem = position
            }
        }

        viewPager = binding.viewPager
        sectionsPagerAdapter =
            SectionsPagerAdapter(activity as AppCompatActivity, viewPagerDataListener!!)
        viewPager.adapter = sectionsPagerAdapter

        tabs = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        Handler(Looper.getMainLooper()).postDelayed({
            setViewpagerPosition(viewPagerPosition)
        }, 100)
    }

    fun setViewpagerPosition(position: Int) {
        if (position >= 0 && position < sectionsPagerAdapter.itemCount) {
            viewPager.setCurrentItem(position, true)
            viewPagerPosition = position
            viewPagerDataListener?.onPageChanged(position)
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.city_title,
            R.string.attraction_title,
            R.string.souvenir_title
        )
        private const val ARG_VIEW_PAGER_POSITION = "view_pager_position"
        fun newInstance(viewPagerPosition: Int): ExploreFragment {
            val fragment = ExploreFragment()
            val args = Bundle().apply {
                putInt(ARG_VIEW_PAGER_POSITION, viewPagerPosition)
            }
            fragment.arguments = args
            return fragment
        }

    }
}
