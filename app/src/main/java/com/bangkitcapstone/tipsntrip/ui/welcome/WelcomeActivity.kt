package com.bangkitcapstone.tipsntrip.ui.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bangkitcapstone.tipsntrip.databinding.ActivityWelcomeBinding
import com.bangkitcapstone.tipsntrip.ui.login.LoginActivity
import com.bangkitcapstone.tipsntrip.ui.signUp.SignUpActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playAnimationOn()
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    private fun playAnimationOn() {
        binding.apply {
            val title = ObjectAnimator.ofFloat(tvWelcomeTitle, View.ALPHA, 1f).setDuration(300)
            val login = ObjectAnimator.ofFloat(btnLogin, View.ALPHA, 1f).setDuration(300)
            val signUp = ObjectAnimator.ofFloat(btnSignup, View.ALPHA, 1f).setDuration(300)

            AnimatorSet().apply {
                playSequentially(title, login, signUp)
                startDelay = 100
            }.start()
        }
    }

}