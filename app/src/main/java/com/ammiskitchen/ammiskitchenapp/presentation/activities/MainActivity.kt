package com.ammiskitchen.ammiskitchenapp.presentation.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ammiskitchen.ammiskitchenapp.R
import com.ammiskitchen.ammiskitchenapp.databinding.ActivityMainBinding
import com.ammiskitchen.ammiskitchenapp.presentation.fragments.AuthFragment
import com.ammiskitchen.ammiskitchenapp.presentation.fragments.BottomNavigationFragment
import com.ammiskitchen.ammiskitchenapp.util.DataState
import com.ammiskitchen.ammiskitchenapp.presentation.viewmodels.AuthStateEvent
import com.ammiskitchen.ammiskitchenapp.presentation.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.AppTheme)
        setContentView(binding.root)

//        authFragment = AuthFragment.newInstance()
//        bottomNavigationFragment = BottomNavigationFragment.newInstance()

        if(savedInstanceState == null) {
            checkUserStatus()
        }

    }

    private fun checkUserStatus() {
        authViewModel.setStateEvent(AuthStateEvent.GetUserEvent)
        authViewModel.currentUserState.observe(this,{
            when(it) {
                is DataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is DataState.Success -> {
                    makeReplaceFragmentTransaction(BottomNavigationFragment::class.java)
                    binding.progressBar.visibility = View.GONE
                }
                is DataState.Error -> {
                    makeReplaceFragmentTransaction(AuthFragment::class.java)
                    binding.progressBar.visibility = View.GONE

                }
            }
        })
    }

    private fun makeReplaceFragmentTransaction(fragmentClass: Class<out Fragment>) {
        supportFragmentManager
            .beginTransaction()
            .apply {
                replace(R.id.root_fragment_container, fragmentClass, null)
                commit()
            }
    }

}