package ru.netology.aap.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.aap.dto.Post

class PostRepositoryInMemory : PostRepository {
    private var post = Post(
        1L,
        "Нетология.",
        "22.07.2023",
        "21 мая в 18:36",
        100,
        1_000,
        1,
        false,
        repostByMe = TODO(),
        viewByMe = TODO(),

        )
    private val data = MutableLiveData(post)
    override fun get(): LiveData<Post> = data

    override fun like() {
        post = if (post.likedByMe) {
            post.copy(likedByMe = false, likes = post.likes - 1)
        } else {
            post.copy(likedByMe = true, likes = post.likes + 1)
        }
        //post = post.copy(likedByMe = !post.likedByMe, like = post.like - 1)
        data.value = post
    }

    override fun shares() {
        post = post.copy(shares = post.shares + 1)
        data.value = post
    }
}