package com.bedirhandroid.spacexfan.ui.adapters.photo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bedirhandroid.spacexfan.databinding.PhotoRowBinding
import com.bedirhandroid.spacexfan.util.loadImage

class PhotoAdapter(private val data: List<String>): RecyclerView.Adapter<PhotoAdapter.PhotoVH>() {

    class PhotoVH(val binding: PhotoRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.ivRockets.loadImage(data)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoVH {
        val binding = PhotoRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoVH(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PhotoVH, position: Int) {
        holder.bind(data[position])
    }

}