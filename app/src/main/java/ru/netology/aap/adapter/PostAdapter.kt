package ru.netology.aap.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.aap.R
import ru.netology.aap.activity.Calc
import ru.netology.aap.databinding.CardPostBinding
import ru.netology.aap.dto.Post


interface OnInteractorListener {
    fun onLike(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onShare(post: Post)
    fun onPlayVideo(post: Post)
    fun onPostClicked(post: Long)
}

class PostsAdapter(
    private val onInteractionListener: OnInteractorListener
) : ListAdapter<Post, PostsAdapter.PostViewHolder>(PostDiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
    class PostViewHolder(
        val binding: CardPostBinding,
        private val onInteractorListener: OnInteractorListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) = with(binding) {
            author.text = post.author
            content.text = post.content
            published.text = post.published
            like.apply {
                isChecked = post.likedByMe
                text = Calc(post.likes)
                setOnClickListener {
                    onInteractorListener.onLike(post)
                }
            }
            share.apply {
                text = Calc(post.shares)
                setOnClickListener {
                    onInteractorListener.onShare(post)
                }
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.menu_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractorListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractorListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
            }
            if (!post.video.isNullOrBlank()) {
                playVideoGroup.visibility = View.VISIBLE
                backgroundVideo.visibility = View.VISIBLE
                play.visibility = View.VISIBLE
                val clickListener = View.OnClickListener {
                    onInteractorListener.onPlayVideo(post)
                }
                playVideoGroup.setOnClickListener(clickListener)
                backgroundVideo.setOnClickListener(clickListener)
                play.setOnClickListener(clickListener)
            } else {
                playVideoGroup.visibility = View.GONE
                backgroundVideo.visibility = View.GONE
                play.visibility = View.GONE
            }
            itemView.setOnClickListener {
                onInteractorListener.onPostClicked(post.id)
            }
        }
    }
    object PostDiffCallBack : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(
            oldItem: Post,
            newItem: Post
        ): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(
            oldItem: Post,
            newItem: Post
        ): Boolean {
            return oldItem == newItem
        }

    }
}
