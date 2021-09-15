package com.ammiskitchen.ammiskitchenapp

import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.ammiskitchen.ammiskitchenapp.R
import com.ammiskitchen.ammiskitchenapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setTheme(R.style.AppTheme)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.main_host_fragment)

        setupActionBarWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.main_host_fragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}