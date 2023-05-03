package com.bedirhandroid.spacexfan.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bedirhandroid.spacexfan.R
import com.bedirhandroid.spacexfan.databinding.ItemNetworkStateBinding
import com.bedirhandroid.spacexfan.ui.adapters.rockets.RocketsAdapter
import com.bedirhandroid.spacexfan.util.visibleIf

class LaunchesLoadingStateAdapter(
    private val adapter: RocketsAdapter
) : LoadStateAdapter<LaunchesLoadingStateAdapter.NetworkStateItemViewHolder>() {

    class NetworkStateItemViewHolder(
        private val binding: ItemNetworkStateBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton.setOnClickListener {
                retryCallback.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar visibleIf  (loadState is LoadState.Loading)
                retryButton visibleIf  (loadState is LoadState.Error)
                errorMsg visibleIf
                        !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                errorMsg.text = (loadState as? LoadState.Error)?.error?.message
            }
        }
    }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        NetworkStateItemViewHolder(
            ItemNetworkStateBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_network_state, parent, false)
            )
        ) { adapter.retry() }
}