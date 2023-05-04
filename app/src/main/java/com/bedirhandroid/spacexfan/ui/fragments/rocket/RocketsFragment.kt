package com.bedirhandroid.spacexfan.ui.fragments.rocket

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bedirhandroid.spacexfan.R
import com.bedirhandroid.spacexfan.base.BaseFragment
import com.bedirhandroid.spacexfan.databinding.FragmentRocketsBinding
import com.bedirhandroid.spacexfan.network.response.rockets.SpaceXRocketsResponseItem
import com.bedirhandroid.spacexfan.paging.LaunchesLoadingStateAdapter
import com.bedirhandroid.spacexfan.ui.adapters.rockets.RocketsAdapter
import com.bedirhandroid.spacexfan.util.navigateWithBundleTo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RocketsFragment : BaseFragment<FragmentRocketsBinding, RocketsViewModel>() {
    private lateinit var rocketsAdapter: RocketsAdapter
    override fun initView() {
        viewModel.getDataBaseList()
        rocketsAdapter = RocketsAdapter(
            arrayListOf(),
            ::onRocketItemClick,
            ::onFavItemClick
        )
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

            mutableFavList.observe(viewLifecycleOwner) {
                rocketsAdapter.setFavoriteData(it)
                rocketsAdapter.notifyDataSetChanged()
            }

        }
    }

    private fun onRocketItemClick(data: String) {
        Bundle().apply {
            putString("rocket_id", data)
        }.also { navigateWithBundleTo(R.id.action_nav_home_to_rocketDetailFragment, it) }
    }

    private fun onFavItemClick(data: SpaceXRocketsResponseItem) {
        viewModelScope {
            if (mutableFavList.value?.none { it.id == data.id } == true) {
                addDataBaseOperation(data)
            } else {
                removeDataBaseOperation(data)
            }
            getDataBaseList()
        }
    }

}