package ru.netology.aap.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import ru.netology.aap.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId: Long = 1L
    private var posts = mutableListOf(


        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология!",
            published = "21 мая в 18:36",
            likes = 21,
            shares = 4,
            views = 67,
            video = "https://rutube.ru/video/6550a91e7e523f9503bed47e4c46d0cb",
            shareByMe = false,
            likedByMe = false
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология!",
            published = "21 мая в 18:36",
            likes = 21,
            shares = 4,
            views = 67,
            video = "https://rutube.ru/video/6550a91e7e523f9503bed47e4c46d0cb",
            shareByMe = false,
            likedByMe = false
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология!",
            published = "21 мая в 18:36",
            likes = 21,
            shares = 4,
            views = 67,
            video = "https://rutube.ru/video/6550a91e7e523f9503bed47e4c46d0cb",
            shareByMe = false,
            likedByMe = false
        ),
    ).toMutableList()
    private val data = MutableLiveData(posts.toList()) // LiveData для наблюдения за списком

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                val i = if (post.likedByMe) -1 else 1
                post.copy(likes = post.likes + i, likedByMe = !post.likedByMe)
            } else {
                post
            }
        }.toMutableList()
        data.value = posts.toList()
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                shareByMe = !it.shareByMe,
                shares = it.shares + 1
            )
        }.toMutableList()
        data.value = posts.toList()
    }

    override fun removeById(id: Long) {
        posts.removeIf { it.id == id }
        data.value = posts.toList()
    }

    override fun save(post: String) {


        fun save(post: Post) {
            posts = (if (post.id == 0L) {
                val newPost = post.copy(id = nextId++, author = "Me", published = "now")
                mutableListOf(newPost) + posts
            } else {
                posts.map {
                    if (post.id == it.id) {
                        it.copy(content = post.content)
                    } else it
                }.toMutableList()
            }) as MutableList<Post>
        }
        data.value = posts.toList()
    }

    override fun save(post: Post) {
        posts = (if (post.id == 0L) {
            val newPost = post.copy(id = nextId++, author = "Me", published = "now")
            mutableListOf(newPost) + posts
        } else {
            posts.map {
                if (post.id == it.id) {
                    it.copy(content = post.content)
                } else it
            }.toMutableList()
        }) as MutableList<Post>

        data.value = posts.toList()
    }
}



