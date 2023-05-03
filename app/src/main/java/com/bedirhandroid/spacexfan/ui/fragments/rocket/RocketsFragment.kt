package com.bedirhandroid.spacexfan.ui.fragments.rocket

import androidx.lifecycle.lifecycleScope
import com.bedirhandroid.spacexfan.base.BaseFragment
import com.bedirhandroid.spacexfan.databinding.FragmentRocketsBinding
import com.bedirhandroid.spacexfan.paging.LaunchesLoadingStateAdapter
import com.bedirhandroid.spacexfan.ui.adapters.rockets.RocketsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RocketsFragment : BaseFragment<FragmentRocketsBinding, RocketsViewModel>() {

    private lateinit var rocketsAdapter : RocketsAdapter
    override fun initView() {
        rocketsAdapter = RocketsAdapter {}
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

}