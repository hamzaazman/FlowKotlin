package com.hamzaazman.flowexample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hamzaazman.flowexample.data.model.Comment
import com.hamzaazman.flowexample.databinding.PostItemRowBinding

class CommentAdapter : ListAdapter<Comment, CommentAdapter.CommentViewHolder>(DiffUtilComment) {
    object DiffUtilComment : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem == newItem
        }

    }

    class CommentViewHolder(private val binding: PostItemRowBinding) : ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            with(binding) {
                postTitleTextView.text = comment.email
                postDescTextView.text = comment.body
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            PostItemRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val currentComment = currentList[position]
        holder.bind(currentComment)
    }
}