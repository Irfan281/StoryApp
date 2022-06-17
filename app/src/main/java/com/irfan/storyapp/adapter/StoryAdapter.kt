package com.irfan.storyapp.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.irfan.storyapp.data.api.ListStoryItem
import com.irfan.storyapp.databinding.ItemStoryBinding
import com.irfan.storyapp.view.detail.DetailActivity


class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemStoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class ViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem) {
            Glide.with(itemView).load(data.photoUrl).into(binding.imgStory)
            binding.apply {
                tvUser.text = data.name
                tvDescription.text = data.description
                tvDate.text = data.createdAt
            }

            itemView.setOnClickListener {
                val toDetail = Intent(itemView.context, DetailActivity::class.java)
                toDetail.putExtra(STORY, data)

                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.imgStory, "story"),
                        Pair(binding.tvUser, "name"),
                        Pair(binding.tvDescription, "description"),
                    )

                itemView.context.startActivity(toDetail, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        const val STORY = "story"

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListStoryItem,
                newItem: ListStoryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}