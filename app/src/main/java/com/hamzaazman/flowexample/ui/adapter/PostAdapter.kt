package com.hamzaazman.flowexample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hamzaazman.flowexample.data.model.Post
import com.hamzaazman.flowexample.databinding.PostItemRowBinding

class PostAdapter : ListAdapter<Post, PostAdapter.PostViewHolder>(DiffUtilPost) {

    class PostViewHolder(private val binding: PostItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) = with(binding) {
            postTitleTextView.text = post.title
            postDescTextView.text = post.body
        }
    }


    object DiffUtilPost : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            PostItemRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentPost = currentList[position]
        holder.bind(currentPost)
    }
}