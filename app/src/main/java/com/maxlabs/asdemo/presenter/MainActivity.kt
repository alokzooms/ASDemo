package com.maxlabs.asdemo.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.maxlabs.asdemo.R
import com.maxlabs.asdemo.util.UserPreferences
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userPreferences = UserPreferences(this)
        if (userPreferences.getUsername() != null) {
            val fragment = CategoryFragment()
            val bundle = Bundle()
            bundle.putString("username", userPreferences.getUsername())
            fragment.arguments = bundle
            navigateTodestinationragment(fragment)
        } else {
            navigateTodestinationragment(LoginFragment())
        }
    }

    /**
     * Loading the first fragment
     */
    private fun navigateTodestinationragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragment)
            .commit()
    }
}