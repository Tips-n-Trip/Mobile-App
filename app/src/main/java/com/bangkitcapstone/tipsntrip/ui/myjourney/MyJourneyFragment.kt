package com.bangkitcapstone.tipsntrip.ui.myjourney

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.tipsntrip.R
import com.bangkitcapstone.tipsntrip.adapter.itenerary.ListMyJourneyAdapter
import com.bangkitcapstone.tipsntrip.databinding.FragmentLoginRequestBinding
import com.bangkitcapstone.tipsntrip.databinding.FragmentMyJourneyBinding
import com.bangkitcapstone.tipsntrip.ui.itenerary.IteneraryViewModel
import com.bangkitcapstone.tipsntrip.ui.login.LoginActivity
import com.bangkitcapstone.tipsntrip.ui.signUp.SignUpActivity
import com.bangkitcapstone.tipsntrip.utils.UserPreference

class MyJourneyFragment : Fragment() {
    private lateinit var binding: FragmentMyJourneyBinding
    private lateinit var userPreference: UserPreference
    private lateinit var bindingLogin: FragmentLoginRequestBinding
    private val iteneraryViewModel by viewModels<IteneraryViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMyJourneyBinding.inflate(layoutInflater)
        (activity as AppCompatActivity).supportActionBar?.hide()
        userPreference = UserPreference(requireActivity())
        bindingLogin = FragmentLoginRequestBinding.inflate(layoutInflater)
        if (userPreference.isLogin()) {
            return binding.root
        } else {
            return bindingLogin.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingLogin.btnLogin.setOnClickListener {
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
        }
        bindingLogin.btnSignup.setOnClickListener {
            startActivity(Intent(requireActivity(), SignUpActivity::class.java))
        }
        recyclerView()
    }

    private fun recyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewMyJourneyFragment.layoutManager = layoutManager

        iteneraryViewModel.apply {
            getSavedItenerary(requireActivity())
            listItenerary.observe(viewLifecycleOwner, {
                if (it.success == true) {
                    val adapter = ListMyJourneyAdapter(it.iteneraries)
                    binding.recyclerViewMyJourneyFragment.adapter = adapter
                } else {
                    Toast.makeText(requireActivity(),
                        resources.getString(R.string.itenenary_error),
                        Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

}