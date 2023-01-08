package me.shakhriyor.paging3.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.shakhriyor.paging3.data.model.GithubRepository
import me.shakhriyor.paging3.databinding.ItemRepoBinding

class RepoListAdapter: PagingDataAdapter<GithubRepository, RepoListAdapter.VH>(COMPARATOR) {


    inner class VH(private val binding: ItemRepoBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GithubRepository?) {
            binding.tvRepo.text = item?.full_name
        }

    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<GithubRepository>() {
            override fun areItemsTheSame(
                oldItem: GithubRepository,
                newItem: GithubRepository,
            ): Boolean {
                return oldItem.full_name == newItem.full_name
            }

            override fun areContentsTheSame(
                oldItem: GithubRepository,
                newItem: GithubRepository,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
}

