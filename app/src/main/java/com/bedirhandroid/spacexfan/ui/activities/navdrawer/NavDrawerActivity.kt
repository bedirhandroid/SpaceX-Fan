package com.bedirhandroid.spacexfan.ui.activities.navdrawer

import android.content.Intent
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bedirhandroid.spacexfan.R
import com.bedirhandroid.spacexfan.base.BaseActivity
import com.bedirhandroid.spacexfan.databinding.ActivityNavDrawerBinding
import com.bedirhandroid.spacexfan.util.visibleIf
import com.google.android.material.navigation.NavigationView

class NavDrawerActivity : BaseActivity<ActivityNavDrawerBinding, NavDrawerViewModel>() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun initView() {
        setSupportActionBar(binding.appBarNavDrawer.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_nav_drawer)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.rocketsFragment, R.id.favoritesFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        binding.appBarNavDrawer.btnSignOut visibleIf (viewModel.auth.currentUser != null)
    }

    override fun initListeners() {
        binding.appBarNavDrawer.btnSignOut.setOnClickListener {
            viewModel.signOut()
            Intent(this@NavDrawerActivity, NavDrawerActivity::class.java).also { _intent ->
                startActivity(_intent)
                finish()
            }
        }
    }
    override fun initObservers() {}


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_nav_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}