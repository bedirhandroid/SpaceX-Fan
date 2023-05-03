package com.bedirhandroid.spacexfan.ui.fragments.rocket

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bedirhandroid.spacexfan.R
import com.bedirhandroid.spacexfan.base.BaseFragment
import com.bedirhandroid.spacexfan.databinding.FragmentRocketsBinding
import com.bedirhandroid.spacexfan.paging.LaunchesLoadingStateAdapter
import com.bedirhandroid.spacexfan.ui.adapters.rockets.RocketsAdapter
import com.bedirhandroid.spacexfan.util.navigateWithBundleTo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RocketsFragment : BaseFragment<FragmentRocketsBinding, RocketsViewModel>() {

    private lateinit var rocketsAdapter : RocketsAdapter
    override fun initView() {
        rocketsAdapter = RocketsAdapter(::onRocketItemClick)
        binding.rvRockets.adapter = rocketsAdapter.withLoadStateFooter(
            footer = LaunchesLoadingStateAdapter(rocketsAdapter)
        )
    }

    override fun initListeners() {

    }

    override fun initObservers() {
        viewModelScope {
            viewLifecycleOwner.lifecycleScope.launch {
                getRocketsList.collectLatest {
                    rocketsAdapter.submitData(it)
                }
            }
        }
    }

    private fun onRocketItemClick(data : String) {
        Bundle().apply {
            putString("rocket_id", data)
        }.also { navigateWithBundleTo(R.id.action_nav_home_to_rocketDetailFragment, it) }
    }

}