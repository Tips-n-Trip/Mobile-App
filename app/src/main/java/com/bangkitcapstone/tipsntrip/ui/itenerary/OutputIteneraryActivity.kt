package com.bangkitcapstone.tipsntrip.ui.itenerary

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2
import com.bangkitcapstone.tipsntrip.R
import com.bangkitcapstone.tipsntrip.adapter.viewpager.DaySectionPagerAdapter
import com.bangkitcapstone.tipsntrip.data.lib.itenerary.Agenda
import com.bangkitcapstone.tipsntrip.data.lib.itenerary.Itenerary
import com.bangkitcapstone.tipsntrip.databinding.ActivityOutputIteneraryBinding
import com.bangkitcapstone.tipsntrip.ui.main.MainActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OutputIteneraryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOutputIteneraryBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var sectionsPagerAdapter: DaySectionPagerAdapter
    private val iteneraryViewModel by viewModels<IteneraryViewModel>()
    private var isBookmarked = false
    private var id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOutputIteneraryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val iteneraryData =
            intent.getParcelableExtra<Itenerary>("DATA") as Itenerary
        val tabCount = iteneraryData.duration
        id = iteneraryData.id
        isBookmarked = iteneraryData.isSaved
        if (isBookmarked == true) {
            binding.ivBookmark.setImageDrawable(resources.getDrawable(R.drawable.ic_bookmarked_white))
        }

        binding.ivBookmark.setOnClickListener {
            if (isBookmarked == false) {
                iteneraryViewModel.apply {
                    saveItenerarybyId(this@OutputIteneraryActivity, iteneraryData.id)
                    isLoading.observe(this@OutputIteneraryActivity, {
                        showLoading(it)
                    })
                    saveItenerary.observe(this@OutputIteneraryActivity, {
                        if (it.success == true) {
                            Toast.makeText(this@OutputIteneraryActivity,
                                resources.getString(R.string.itenenary_saved),
                                Toast.LENGTH_SHORT).show()
                            //make a function to save the itenenary
                            binding.ivBookmark.setImageDrawable(resources.getDrawable(R.drawable.ic_bookmarked_white))
                            isBookmarked = !isBookmarked
                        } else {
                            Toast.makeText(this@OutputIteneraryActivity,
                                "Gagal menyimpan rute",
                                Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            } else {
                deleteItenerarySaved(iteneraryData.name, iteneraryData.id)
            }
//            isBookmarked = !isBookmarked
        }
        binding.btnBack.setOnClickListener {
            //delete current output by viewmodel
            if (isBookmarked == false) {
                iteneraryViewModel.deleteItenerarybyId(this@OutputIteneraryActivity, id)
            }
            val intent = Intent(this@OutputIteneraryActivity, MainActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
        with(iteneraryData) {
            isBookmarked = isSaved
            binding.tvBannerTitle.text = name
            setViewPager(tabCount ?: 1, this.agendas)
        }
        Glide.with(this@OutputIteneraryActivity)
            .load(iteneraryData.destination.imagePath)
            .into(binding.ivBannerCity)
    }

    private fun setViewPager(tabCount: Int, listAgenda: ArrayList<Agenda>) {
        viewPager = binding.viewPagerDay
        sectionsPagerAdapter = DaySectionPagerAdapter(this, tabCount)
        sectionsPagerAdapter.listAgenda = listAgenda
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (isBookmarked == false) {
            iteneraryViewModel.deleteItenerarybyId(this@OutputIteneraryActivity, id)
        }
        val intent = Intent(this@OutputIteneraryActivity, MainActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
    }

    private fun deleteItenerarySaved(name: String, id: String) {
        val builder = AlertDialog.Builder(this@OutputIteneraryActivity)
        builder.setTitle(resources.getString(R.string.delete_itenenary_title))
        builder.setMessage(resources.getString(R.string.delete_itenenary_message, name))
        builder.setPositiveButton(resources.getString(R.string.next)) { _, _ ->
            iteneraryViewModel.apply {
                isLoading.observe(this@OutputIteneraryActivity, {
                    showLoading(it)
                })
                unsaveItenerarybyId(this@OutputIteneraryActivity, id)
                unsaveItenerary.observe(this@OutputIteneraryActivity, {
                    if (it.success == true) {
//                        Toast.makeText(this@OutputIteneraryActivity,
//                            resources.getString(R.string.itenenary_deleted),
//                            Toast.LENGTH_SHORT).show()
                        binding.ivBookmark.setImageDrawable(resources.getDrawable(R.drawable.ic_bookmark_white))
                        isBookmarked = !isBookmarked
                    } else {
                        Toast.makeText(this@OutputIteneraryActivity,
                            resources.getString(R.string.itenenary_deleted_error),
                            Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
        builder.setNegativeButton(resources.getString(R.string.cancel)) { _, _ ->
            isBookmarked = true
        }
        val dialog = builder.create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            .setTextColor(resources.getColor(R.color.red))
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(resources.getColor(R.color.blue_app))
        dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.day_one,
            R.string.day_two,
            R.string.day_three,
            R.string.day_four,
            R.string.day_five
        )

    }
}

