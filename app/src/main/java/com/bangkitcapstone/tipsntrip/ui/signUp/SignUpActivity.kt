package com.bangkitcapstone.tipsntrip.ui.signUp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkitcapstone.tipsntrip.R
import com.bangkitcapstone.tipsntrip.databinding.ActivitySignUpBinding
import com.bangkitcapstone.tipsntrip.ui.login.LoginActivity


class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val signUpViewModel by viewModels<SignUpViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAction()
    }

    private fun setupAction() {
        textWatcher()
        binding.apply {
            btnLogin.setOnClickListener {
                startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
            }
            btnGoogleSignup.setOnClickListener {
                Toast.makeText(
                    this@SignUpActivity,
                    resources.getString(R.string.coming_soon),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            btnSignup.setOnClickListener {
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val isNameValid = name.isNotEmpty() && isNameValid(name)
                val isEmailValid = email.isNotEmpty() && isEmailValid(email)
                val isPasswordValid = password.isNotEmpty() && isPasswordValid(password)
                if (isNameValid && isEmailValid && isPasswordValid)
                    setupViewModel(
                        name,
                        email,
                        password
                    )
                if (!isNameValid) {
                    etNameLayout.errorIconDrawable = null
                    etNameLayout.error = resources.getString(R.string.name_minimum)
                }
                if (!isEmailValid) {
                    etEmailLayout.errorIconDrawable = null
                    etEmailLayout.error = resources.getString(R.string.email_invalid)
                }
                if (!isPasswordValid) {
                    etPasswordLayout.errorIconDrawable = null
                    etPasswordLayout.error = resources.getString(R.string.password_minimum)
                }
            }
        }
    }

    private fun isNameValid(input: String): Boolean {
        return input.length >= 2
    }

    private fun isEmailValid(input: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
    }

    private fun isPasswordValid(input: String): Boolean {
        return input.length >= 8
    }

    private fun textWatcher() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.etNameLayout.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {}

        })
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

    private fun setupViewModel(name: String, email: String, password: String) {
        signUpViewModel.apply {
            signUp(this@SignUpActivity, name, email, password)
            isLoading.observe(this@SignUpActivity) {
                showLoading(it)
            }
            isLogin.observe(this@SignUpActivity, { state ->
                if (state == true) {
                    user.observe(this@SignUpActivity, { data ->
                        if (data.success == true) {
                            showDialog(resources.getString(R.string.signup_success_title),
                                resources.getString(R.string.signup_success_message),
                                resources.getString(R.string.next),
                                state)
                        } else {
                            Log.e("SignUpActivity", data.message)
                        }
                    })
                } else {
                    showDialog(resources.getString(R.string.signup_failed_title),
                        resources.getString(R.string.signup_failed_message),
                        resources.getString(R.string.retry),
                        state)
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
                val intent = Intent(this, LoginActivity::class.java)
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