package com.maxlabs.asdemo.presenter

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.maxlabs.asdemo.databinding.FragmentLoginBinding

import com.maxlabs.asdemo.R
import com.maxlabs.asdemo.presenter.viewModels.LoginViewModel
import com.maxlabs.asdemo.presenter.viewModels.LoginViewModelFactory
import com.maxlabs.asdemo.util.Constants
import com.maxlabs.asdemo.util.UserPreferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class LoginFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: LoginViewModelFactory

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private lateinit var userPreferences: UserPreferences
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this, viewModelFactory)
            .get(LoginViewModel::class.java)
        val usernameEditText = binding.username
        val passwordEditText = binding.password
        val loginButton = binding.login
        val loadingProgressBar = binding.loading

        loginViewModel.loginFormState.observe(viewLifecycleOwner,
            Observer { loginFormState ->
                if (loginFormState == null) {
                    return@Observer
                }
                loginButton.isEnabled = loginFormState.isDataValid
                loginFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                loginFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
            })

        loginViewModel.loginResult.observe(viewLifecycleOwner,
            Observer { loginResult ->
                loginResult ?: return@Observer
                loadingProgressBar.visibility = View.GONE
                if (loginResult.code == 200) {
                    userPreferences = UserPreferences(requireContext())
                    userPreferences.saveUserCredentials(
                        usernameEditText.text.toString(),
                        passwordEditText.text.toString())
                    showToastMessage((getString(R.string.login_success)))
                    navigateToInspectionScreen()
                } else {
                    loginResult.message?.let { showToastMessage(it) }
                }
            })

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                loginViewModel.loginDataChanged(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        loginButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            loginViewModel.login(
                usernameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }
    }



    private fun navigateToInspectionScreen() {
        val bundle = Bundle()
        bundle.putBoolean(Constants.IS_USER_LOGGEDIN, true)
        val fragment = CategoryFragment()
        fragment.arguments = bundle
         parentFragmentManager.beginTransaction()
             .replace(R.id.fragment_container, fragment)
            .addToBackStack(null) // Optional: Add to back stack
            .commit()
    }

    private fun showToastMessage(msgString: String) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, msgString, Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}