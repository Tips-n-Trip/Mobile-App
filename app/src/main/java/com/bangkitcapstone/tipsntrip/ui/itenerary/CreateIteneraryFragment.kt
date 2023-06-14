package com.bangkitcapstone.tipsntrip.ui.itenerary

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkitcapstone.tipsntrip.R
import com.bangkitcapstone.tipsntrip.databinding.FragmentCreateItenenaryBinding
import com.bangkitcapstone.tipsntrip.databinding.FragmentLoginRequestBinding
import com.bangkitcapstone.tipsntrip.ui.login.LoginActivity
import com.bangkitcapstone.tipsntrip.ui.signUp.SignUpActivity
import com.bangkitcapstone.tipsntrip.utils.UserPreference

class CreateIteneraryFragment : Fragment() {
    private lateinit var binding: FragmentCreateItenenaryBinding
    private lateinit var bindingLogin: FragmentLoginRequestBinding
    private lateinit var userPreference: UserPreference
    private val iteneraryViewModel by viewModels<IteneraryViewModel>()
    private val minimumBudget = 100000
    private var currentBudget = 0
    private var duration: Int = 1
    private var destinationSelected: String = "Yogyakarta"
    private var listDestination = listOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        userPreference = UserPreference(requireActivity())
        binding = FragmentCreateItenenaryBinding.inflate(layoutInflater)
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
        updateCreateButtonState(currentBudget)

        setupViewModel()

        val choices = resources.getStringArray(R.array.day_choices)
        val adapter =
            ArrayAdapter(requireActivity(), R.layout.day_spinner_item, choices)
        adapter.setDropDownViewResource(R.layout.day_spinner_item)
        binding.btnSpinnerDay.adapter = adapter
        binding.btnSpinnerDay.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    duration = choices[position].toInt()
                    updateCreateButtonState(currentBudget)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    duration = choices[0].toInt()
                    updateCreateButtonState(currentBudget)
                }
            }
        bindingLogin.btnLogin.setOnClickListener {
            startActivity(Intent(requireActivity(), LoginActivity::class.java))
        }
        bindingLogin.btnSignup.setOnClickListener {
            startActivity(Intent(requireActivity(), SignUpActivity::class.java))
        }

        val stringArray = resources.getStringArray(R.array.disclaimer_array_value)
        val text = stringArray.joinToString("\n")
        binding.tvDisclaimerValue.text = text

        binding.etBudget.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length < 6) {
                    binding.btnCreateItenenary.isEnabled = false
                    binding.btnCreateItenenary.setBackgroundColor(resources.getColor(R.color.grey_button))
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.length >= 1) {
                    currentBudget = s.toString().toInt()
                    if (currentBudget < minimumBudget * duration) {
                        binding.etBudget.error =
                            "Budget minimal ${duration * minimumBudget}"
                    }
                    updateCreateButtonState(currentBudget)
                }
            }
        })

    }

    private fun setupViewModel() {
        iteneraryViewModel.apply {
            getDestinationChoices(requireActivity())
            isLoading.observe(viewLifecycleOwner, {
                showLoading(it)
            })
            destinationChoices.observe(viewLifecycleOwner, { choices ->
                listDestination = choices.destinations.map { it.name }
                val adapter =
                    ArrayAdapter(requireActivity(), R.layout.city_spinner_item, listDestination)
                adapter.setDropDownViewResource(R.layout.city_spinner_item)
                binding.spinnerDestination.adapter = adapter
                binding.spinnerDestination.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long,
                        ) {
                            destinationSelected = listDestination[position]
                            updateCreateButtonState(currentBudget)
                            // Do something with the selected destination
                            // For example, you can store it in a variable or pass it to a function
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            destinationSelected = "Malang"
                            updateCreateButtonState(currentBudget)
                        }
                    }
            })
            binding.btnCreateItenenary.setOnClickListener {
                if (currentBudget >= minimumBudget * duration) {
                    iteneraryViewModel.postItenerary(requireContext(),
                        destinationSelected,
                        duration,
                        currentBudget)
                    itenerary.observe(requireActivity(), {
                        if (it.success) {
                            val intent =
                                Intent(requireActivity(), OutputIteneraryActivity::class.java)
                            intent.putExtra("DATA", it.itenerary)
                            startActivity(intent)
                            requireActivity().finish()
                        }
                    })
                } else {
                    binding.etBudget.error = "Budget minimal ${duration * minimumBudget}"
                }
            }
        }
    }

    private fun updateCreateButtonState(budget: Int) {
        if (destinationSelected.isNullOrEmpty() || binding.etBudget.text.isEmpty()) {
            binding.btnCreateItenenary.isEnabled = false
            binding.btnCreateItenenary.setBackgroundColor(resources.getColor(R.color.grey_button))
        } else if (!destinationSelected.isNullOrEmpty() && binding.etBudget.text.isNotEmpty() && budget >= minimumBudget * duration) {
            binding.btnCreateItenenary.isEnabled = true
            binding.btnCreateItenenary.setBackgroundColor(resources.getColor(R.color.blue_app_darker1))
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }


}