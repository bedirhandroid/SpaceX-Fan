package com.bedirhandroid.spacexfan.ui.adapters.rockets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bedirhandroid.spacexfan.R
import com.bedirhandroid.spacexfan.databinding.RocketsRowBinding
import com.bedirhandroid.spacexfan.network.response.rockets.SpaceXRocketsResponseItem
import com.bedirhandroid.spacexfan.ui.adapters.photo.PhotoAdapter
import com.bedirhandroid.spacexfan.util.isFavorite

class RocketsAdapter(private val clickItem: (String) -> Unit) :
    PagingDataAdapter<SpaceXRocketsResponseItem, RocketsAdapter.RocketsListVH>(
        ROCKETS_COMPARATOR
    ) {

    companion object {
        private val ROCKETS_COMPARATOR =
            object : DiffUtil.ItemCallback<SpaceXRocketsResponseItem>() {
                override fun areItemsTheSame(
                    oldItem: SpaceXRocketsResponseItem,
                    newItem: SpaceXRocketsResponseItem
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: SpaceXRocketsResponseItem,
                    newItem: SpaceXRocketsResponseItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    class RocketsListVH(val binding: RocketsRowBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var photoAdapter: PhotoAdapter
        fun bind(data: SpaceXRocketsResponseItem?) {
            data?.let {
                binding.apply {
                    photoAdapter = PhotoAdapter(it.flickr_images)
                    rvPhoto.adapter = photoAdapter
                    rvPhoto.setHasFixedSize(true)
                    icFav isFavorite it.isFavorite
                    rocketDetail.text = it.description
                    rocketName.text = it.rocket_name
                    rocketType.text = it.rocket_type
                }
            }
        }
    }

    override fun onBindViewHolder(holder: RocketsListVH, position: Int) {
        holder.apply {
            bind(getItem(position))
            binding.apply {
                root.setOnClickListener {
                    getItem(position)?.rocket_id?.let(clickItem::invoke)
                }
                icFav.setOnClickListener {

                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketsListVH {
        val binding =
            RocketsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RocketsListVH(binding)
    }
}