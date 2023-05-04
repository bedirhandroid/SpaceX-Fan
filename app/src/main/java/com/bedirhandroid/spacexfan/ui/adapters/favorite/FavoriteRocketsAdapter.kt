package com.bedirhandroid.spacexfan.ui.adapters.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bedirhandroid.spacexfan.databinding.EmptyListRowBinding
import com.bedirhandroid.spacexfan.databinding.RocketsRowBinding
import com.bedirhandroid.spacexfan.network.response.rockets.SpaceXRocketsResponseItem
import com.bedirhandroid.spacexfan.ui.adapters.photo.PhotoAdapter
import com.bedirhandroid.spacexfan.util.fav

class FavoriteRocketsAdapter(
    private val favList: List<SpaceXRocketsResponseItem>,
    private val clickFavItem: (SpaceXRocketsResponseItem) -> Unit,
    private val emptyClick: () -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    class FavoriteVH( val binding: RocketsRowBinding) : ViewHolder(binding.root) {
        private lateinit var photoAdapter: PhotoAdapter
        fun bind(data: SpaceXRocketsResponseItem) {
            data.let {
                binding.apply {
                    photoAdapter = PhotoAdapter(it.flickr_images)
                    rvPhoto.adapter = photoAdapter
                    rvPhoto.setHasFixedSize(true)
                    rocketDetail.text = it.description
                    rocketName.text = it.rocket_name
                    rocketType.text = it.rocket_type
                    icFav.fav()
                }
            }
        }
    }

    class EmptyListVH( val binding: EmptyListRowBinding) : ViewHolder(binding.root) {
        fun bind() {
            binding.gifView.playAnimation()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (favList.isEmpty()) {
            val binding =EmptyListRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            EmptyListVH(binding)
        } else {
            val binding = RocketsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            FavoriteVH(binding)
        }

    }

    override fun getItemCount(): Int {
        return if (favList.isEmpty()) 1 else favList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (favList.isEmpty()) {
            val emptyHolder = holder as EmptyListVH
            emptyHolder.bind()
            emptyHolder.binding.txtEmpty.setOnClickListener {
                emptyClick.invoke()
            }
        } else {
            val favHolder = holder as FavoriteVH
            favHolder.bind(favList[position])
            favHolder.binding.icFav.setOnClickListener {
                clickFavItem.invoke(favList[position])
            }
        }
    }
}