package com.bedirhandroid.spacexfan.ui.fragments.detail

import android.widget.Toast
import com.bedirhandroid.spacexfan.base.BaseFragment
import com.bedirhandroid.spacexfan.databinding.FragmentRocketDetailBinding
import com.bedirhandroid.spacexfan.ui.adapters.photo.PhotoAdapter


class RocketDetailFragment : BaseFragment<FragmentRocketDetailBinding, RocketDetailViewModel>() {
    private var rocketId: String? = null
    private lateinit var photoAdapter: PhotoAdapter
    override fun initView() {
        getArgs()
        viewModelScope {
            rocketId?.let(::getRocketDetails) ?: kotlin.run {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun initListeners() {

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
        }
    }

    private fun getArgs() {
        rocketId = arguments?.getString("rocket_id")
    }

}