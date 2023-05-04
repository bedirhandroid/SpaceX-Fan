package com.bedirhandroid.spacexfan.ui.fragments.detail

import android.widget.Toast
import com.bedirhandroid.spacexfan.R
import com.bedirhandroid.spacexfan.base.BaseFragment
import com.bedirhandroid.spacexfan.base.ErrorMessages
import com.bedirhandroid.spacexfan.databinding.FragmentRocketDetailBinding
import com.bedirhandroid.spacexfan.ui.adapters.photo.PhotoAdapter
import com.bedirhandroid.spacexfan.util.Constant.ROCKET_ID
import com.bedirhandroid.spacexfan.util.fav
import com.bedirhandroid.spacexfan.util.isFavorite
import com.bedirhandroid.spacexfan.util.navigateTo
import com.bedirhandroid.spacexfan.util.unFav


class RocketDetailFragment : BaseFragment<FragmentRocketDetailBinding, RocketDetailViewModel>() {
    private var rocketId: String? = null
    private lateinit var photoAdapter: PhotoAdapter
    override fun initView() {
        getArgs()
        viewModel.getDataBaseList()
        viewModelScope {
            rocketId?.let(::getRocketDetails) ?: kotlin.run {
                Toast.makeText(requireContext(), getText(ErrorMessages.ERROR.id), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initListeners() {
        viewBindingScope {
            icFav.setOnClickListener {
                when (viewModel.auth.currentUser != null) {
                    true -> {
                        if ((viewModel.mutableFavList.value?.any { it.rocket_id == rocketId } == true)) {
                            viewModel.rocketDetailLiveData.value?.let { _data ->
                                viewModel.removeDataBaseOperation(_data)
                                icFav.unFav()
                            }
                        } else {
                            viewModel.rocketDetailLiveData.value?.let { _data ->
                                viewModel.addDataBaseOperation(_data)
                                icFav.fav()
                            }
                        }
                    }

                    else -> navigateTo(R.id.action_rocketDetailFragment_to_loginFragment)
                }
            }
        }
    }

    override fun initObservers() {
        viewModelScope {
            rocketDetailLiveData.observe(viewLifecycleOwner) {
                binding.apply {
                    photoAdapter = PhotoAdapter(it.flickr_images)
                    rvPhoto.adapter = photoAdapter
                    txtRocketName.text = it.rocket_name
                    txtRocketType.text = it.rocket_type
                    txtRocketDesc.text = it.description
                }
            }
            mutableFavList.observe(viewLifecycleOwner) {
                binding.icFav isFavorite (it?.any { _any -> _any.rocket_id == rocketId } == true)
            }
        }
    }

    private fun getArgs() {
        rocketId = arguments?.getString(ROCKET_ID)
    }

}