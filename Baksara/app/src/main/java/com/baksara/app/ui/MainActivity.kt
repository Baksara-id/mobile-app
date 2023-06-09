package com.baksara.app.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.baksara.app.R
import com.baksara.app.utils.ViewModelFactory
import com.baksara.app.databinding.ActivityMainBinding
import com.baksara.app.ui.scanner.ScannerActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val viewModelFactory = ViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        bottomNavigationSetup()
        binding.fabScanner.setOnClickListener {
            startCameraX()
        }
    }

    private fun bottomNavigationSetup(){
        val navView: BottomNavigationView = binding.bottomNav

        navController = findNavController(R.id.nav_host_fragment)

        navView.menu.getItem(2).isEnabled = false
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_kelas, R.id.navigation_pustaka, R.id.navigation_scanner, R.id.navigation_artikel, R.id.navigation_profil
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun startCameraX() {
        val intent = Intent(this, ScannerActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object {
        const val PREF = "android_user"
        const val CURRENTLIMIT = "limit"//jumlah scan
        const val EXP = "exp"
        const val LEVEL = "level"
        const val AVATAR = "profilepic"
        const val UNIQUEID = "id"
        const val FULLNAME = "fullname"
        const val EMAIL = "email"
        const val PREMIUM = "langganan"
        const val MODUL = "modul"
        const val KELAS = "pelajaran"
        const val TOKEN = "tokenz"
        const val LIMITREACH = "limitreach"
    }
}