package com.bedirhandroid.spacexfan.ui.adapters.rockets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bedirhandroid.spacexfan.databinding.RocketsRowBinding
import com.bedirhandroid.spacexfan.network.response.rockets.SpaceXRocketsResponseItem

class RocketsAdapter(private val clickItem: (Int) -> Unit) :
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
        fun bind(data: SpaceXRocketsResponseItem?) {
            data?.let {
                binding.apply {

                }
            }
        }
    }

    override fun onBindViewHolder(holder: RocketsListVH, position: Int) {
        holder.bind(getItem(position))
        holder.binding.root.setOnClickListener { clickItem.invoke(position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketsListVH {
        val binding =
            RocketsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RocketsListVH(binding)
    }
}