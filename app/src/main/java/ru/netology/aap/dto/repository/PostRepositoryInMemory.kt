package ru.netology.aap.dto.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.aap.dto.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1,
        author = "Нетология.Университет",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "20 июля в 14:44",
        shares = 0,
        likes = 995,
        views = "10",
        repostByMe = false,
        viewByMe = false,
        likedByMe = false
    )
    private val data = MutableLiveData(post)
    override fun get(): LiveData<Post> = data
    override fun like() {
        if (post.likedByMe) {
            post = post.copy(likedByMe = !post.likedByMe, likes = post.likes - 1)
        } else {
            post = post.copy(likedByMe = !post.likedByMe, likes = post.likes + 1)
        }
        //post = post.copy(likedByMe = !post.likedByMe, like = post.like - 1)
        data.value = post
    }


    override fun sharing() {
        post = post.copy(shares = post.shares + 1)
        data.value = post
    }


}
