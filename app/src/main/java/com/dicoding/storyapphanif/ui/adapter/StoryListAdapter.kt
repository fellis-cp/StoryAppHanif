package com.dicoding.storyapphanif.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.storyapphanif.data.retrofit.response.ListStoryItem
import com.dicoding.storyapphanif.databinding.LayoutBinding

class StoryListAdapter(private val storyList : List<ListStoryItem>) : RecyclerView.Adapter<StoryListAdapter.StoryListViewHolder>() {

        inner class  StoryListViewHolder(private val binding : LayoutBinding) : RecyclerView.ViewHolder(binding.root){
            fun bindView (storyItem: ListStoryItem) {
                binding.tvDescription.text = storyItem.description
                binding.tvName.text = storyItem.name

                Glide
                    .with(itemView.context)
                    .load(storyItem.photoUrl)
                    .fitCenter()
                    .into(binding.ivStoryImage)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryListViewHolder {
            return StoryListViewHolder(
                LayoutBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
            )
    }

    override fun getItemCount(): Int {
       return storyList.size
    }

    override fun onBindViewHolder(holder: StoryListViewHolder, position: Int) {
        val storyItem = storyList[position]
        holder.bindView(storyItem)
    }

}