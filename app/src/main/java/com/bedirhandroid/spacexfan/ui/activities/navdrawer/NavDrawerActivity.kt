package com.bedirhandroid.spacexfan.ui.activities.navdrawer

import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.bedirhandroid.spacexfan.R
import com.bedirhandroid.spacexfan.base.BaseActivity
import com.bedirhandroid.spacexfan.databinding.ActivityNavDrawerBinding

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
    }

    override fun initListeners() {

    }

    override fun initObservers() {

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_nav_drawer)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}