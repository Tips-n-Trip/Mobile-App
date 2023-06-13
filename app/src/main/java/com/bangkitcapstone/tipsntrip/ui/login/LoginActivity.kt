package com.bangkitcapstone.tipsntrip.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkitcapstone.tipsntrip.R
import com.bangkitcapstone.tipsntrip.databinding.ActivityLoginBinding
import com.bangkitcapstone.tipsntrip.ui.main.MainActivity
import com.bangkitcapstone.tipsntrip.ui.signUp.SignUpActivity
import com.bangkitcapstone.tipsntrip.utils.UserPreference

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPreference: UserPreference
    private val loginViewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        userPreference = UserPreference(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAction()
    }

    private fun setupAction() {
        textWatcher()
        binding.apply {
            btnSignup.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            }
            btnGoogleLogin.setOnClickListener {
                Toast.makeText(
                    this@LoginActivity,
                    resources.getString(R.string.cooming_soon),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                val isEmailValid = email.isNotEmpty() && isEmailValid(email)
                val isPasswordValid = password.isNotEmpty() && isPasswordValid(password)
                if (isEmailValid && isPasswordValid) {
                    setupViewModel(email, password)
                }
                if (!isEmailValid) {
                    binding.etEmailLayout.errorIconDrawable = null
                    binding.etEmailLayout.error = resources.getString(R.string.email_invalid)
                }
                if (!isPasswordValid) {
                    binding.etPasswordLayout.errorIconDrawable = null
                    binding.etPasswordLayout.error = resources.getString(R.string.password_minimum)
                }

            }
        }

    }

    private fun textWatcher() {
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.etEmailLayout.isErrorEnabled = false

            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.etPasswordLayout.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun isEmailValid(input: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
    }

    private fun isPasswordValid(input: String): Boolean {
        return input.length >= 8
    }

    private fun setupViewModel(email: String, password: String) {
        loginViewModel.apply {
            login(this@LoginActivity, email, password)
            isLoading.observe(this@LoginActivity, {
                showLoading(it)
            })
            isLogin.observe(this@LoginActivity, { state ->
                if (state == true) {
                    user.observe(this@LoginActivity, { response ->
                        if (response.success == true) {
                            with(response) {
                                userPreference.saveUser(
                                    credentials.name,
                                    credentials.token,
                                    credentials.id
                                )
                            }
                        }
                    })
                    showDialog(
                        resources.getString(R.string.greeting, userPreference.getName()),
                        resources.getString(R.string.login_success),
                        resources.getString(R.string.next), state
                    )
                } else {
                    showDialog(
                        resources.getString(R.string.login_failed),
                        resources.getString(R.string.email_password_invalid),
                        resources.getString(R.string.retry), state
                    )
                }

            })
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showDialog(title: String, message: String, instruction: String, state: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(instruction) { _, _ ->
            if (state) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            } else {
                overridePendingTransition(0, 0)
                startActivity(intent)
                overridePendingTransition(0, 0)
                finish()
            }
        }
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(R.drawable.rounded_dialog)
    }

}