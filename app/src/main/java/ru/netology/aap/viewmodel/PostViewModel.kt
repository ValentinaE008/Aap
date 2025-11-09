package ru.netology.aap.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.aap.dto.Post
import ru.netology.aap.repository.PostRepository
import ru.netology.aap.repository.PostRepositoryFileImpl

private val empty = Post(
    id = 0,
    author = "0",
    content = "",
    published = "",
    shares = 0,
    likes = 0,
    views = 0,
    video = null,
    shareByMe = false,
    likedByMe = false
)

class PostViewModel(application: Application): AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositoryFileImpl(application)
    val data: LiveData<List<Post>> = repository.getAll()
    val edited = MutableLiveData(empty)

    fun like(id: Long) = repository.likeById(id)
    fun share(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)
    fun save(newContent: String) {
        edited.value?.copy(content = newContent)?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post:Post){
        edited.value = post
    }
    fun getPostById(id: Long): Post? {
        return data.value?.find { it.id == id }
    }

}




