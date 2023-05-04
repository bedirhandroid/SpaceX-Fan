package com.bedirhandroid.spacexfan.ui.fragments.favorite

import com.bedirhandroid.spacexfan.R
import com.bedirhandroid.spacexfan.base.BaseFragment
import com.bedirhandroid.spacexfan.databinding.FragmentFavoriteRocketsBinding
import com.bedirhandroid.spacexfan.network.response.rockets.SpaceXRocketsResponseItem
import com.bedirhandroid.spacexfan.ui.adapters.favorite.FavoriteRocketsAdapter
import com.bedirhandroid.spacexfan.util.gone
import com.bedirhandroid.spacexfan.util.navigateTo
import com.bedirhandroid.spacexfan.util.visible

class FavoriteRocketsFragment : BaseFragment<FragmentFavoriteRocketsBinding, FavoriteRocketsViewModel>() {
    private lateinit var favAdapter : FavoriteRocketsAdapter
    override fun initView() {
        when(viewModel.auth.currentUser) {
            null -> {
                viewBindingScope {
                    llLogin.visible()
                    rvFavRockets.gone()
                }
            }
            else -> {
                viewBindingScope {
                    viewModel.getDataBaseList()
                    rvFavRockets.visible()
                    llLogin.gone()
                }
            }
        }
    }

    override fun initListeners() {
        viewBindingScope {
            btnLogin.setOnClickListener {
                navigateTo(R.id.action_favoritesFragment_to_loginFragment)
            }
        }
    }

    override fun initObservers() {
        viewModelScope {
            mutableFavList.observe(viewLifecycleOwner) {
                favAdapter = FavoriteRocketsAdapter(it, ::clickFavItem, ::emptyClick)
                binding.rvFavRockets.adapter = favAdapter
            }
        }
    }

    private fun clickFavItem(data: SpaceXRocketsResponseItem) {
        viewModelScope {
            removeDataBaseOperation(data)
            getDataBaseList()
        }
    }

    private fun emptyClick() {
        navigateTo(R.id.action_favoritesFragment_to_loginFragment)
    }
}