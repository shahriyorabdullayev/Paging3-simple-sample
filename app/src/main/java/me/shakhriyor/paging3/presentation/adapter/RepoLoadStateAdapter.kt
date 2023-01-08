package me.shakhriyor.paging3.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import me.shakhriyor.paging3.databinding.LayoutLoadingFooterBinding

class RepoLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<RepoLoadStateAdapter.RepoLoadStateViewHolder>() {

    class RepoLoadStateViewHolder(val binding: LayoutLoadingFooterBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
            init {
                binding.btnRetry.setOnClickListener {
                    retry()
                }
            }

    }

    override fun onBindViewHolder(holder: RepoLoadStateViewHolder, loadState: LoadState) {
        if (loadState is LoadState.Error) {
            holder.binding.txtErrorMsg.text = loadState.error.localizedMessage
        }
        holder.binding.apply {
            progressBar.isVisible = loadState is LoadState.Loading
            btnRetry.isVisible = loadState is LoadState.Error
            txtErrorMsg.isVisible = loadState is LoadState.Error
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): RepoLoadStateViewHolder {
        return RepoLoadStateViewHolder(LayoutLoadingFooterBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false), retry)
    }
}