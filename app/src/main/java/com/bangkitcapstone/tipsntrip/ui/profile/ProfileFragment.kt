package com.bangkitcapstone.tipsntrip.ui.profile

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkitcapstone.tipsntrip.R
import com.bangkitcapstone.tipsntrip.databinding.FragmentLoginRequestBinding
import com.bangkitcapstone.tipsntrip.databinding.FragmentProfileBinding
import com.bangkitcapstone.tipsntrip.ui.login.LoginActivity
import com.bangkitcapstone.tipsntrip.ui.main.MainActivity
import com.bangkitcapstone.tipsntrip.ui.signUp.SignUpActivity
import com.bangkitcapstone.tipsntrip.utils.UserPreference

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var bindingLogin: FragmentLoginRequestBinding
    private val profileViewModel by viewModels<ProfileViewModel>()
    private lateinit var userPreference: UserPreference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
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
        (activity as AppCompatActivity).supportActionBar?.hide()
        binding.usernameProfile.text = userPreference.getName()
        binding.emailProfile.text = userPreference.getEmail()
        binding.countItinerary.text = userPreference.getTotalIteneraries()
        bindingLogin.btnLogin.setOnClickListener {
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
        }
        bindingLogin.btnSignup.setOnClickListener {
            startActivity(Intent(requireActivity(), SignUpActivity::class.java))
        }
        binding.btnLogoutProfile.setOnClickListener {
            showLogoutConfirmationDialog()
        }
        setupViewModel()

    }

    private fun setupViewModel() {
        profileViewModel.apply {
            getUserProfile(requireActivity())
            user.observe(viewLifecycleOwner, { data ->
                if (data.success == true) {
                    with(data.userdata) {
                        binding.usernameProfile.text = name
                        binding.countItinerary.text = totalIteneraries.toString()
                        userPreference.saveProfileData(email, totalIteneraries.toString())
                        binding.emailProfile.text = email
                    }
                }
            })
        }
        playAnimation()
    }

    private fun playAnimation() {
        binding.apply {
            val name = ObjectAnimator.ofFloat(usernameProfile, View.ALPHA, 1f).setDuration(300)
            val email = ObjectAnimator.ofFloat(emailProfile, View.ALPHA, 1f).setDuration(300)
            val itinenaries = ObjectAnimator.ofFloat(countItinerary, View.ALPHA, 1f).setDuration(300)
            val iteneraries_label = ObjectAnimator.ofFloat(tvLabelItinerary, View.ALPHA, 1f).setDuration(300)
            AnimatorSet().apply {
                playTogether(name, email, itinenaries, iteneraries_label)
                startDelay = 100
            }.start()
        }
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(resources.getString(R.string.logout))
        builder.setMessage(resources.getString(R.string.logout_message))
        builder.setPositiveButton(resources.getString(R.string.next)) { _, _ ->
            logout()
        }
        builder.setNegativeButton(resources.getString(R.string.cancel), null)
        val dialog = builder.create()
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.red))
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            .setTextColor(resources.getColor(R.color.blue_app))
        dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
    }

    private fun logout() {
        userPreference.logout()
        startActivity(Intent(requireActivity(), MainActivity::class.java))
        requireActivity().finish()
    }

}