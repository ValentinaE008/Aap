package ru.netology.aap.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.aap.dto.Post
import ru.netology.aap.repository.PostRepodtoryFileslmpl
import ru.netology.aap.repository.PostRepository
import ru.netology.aap.repository.PostRepositoryInMemoryImpl
import ru.netology.aap.repository.PostRepositorySharedPrefslmpl

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

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryInMemoryImpl(application)
    val data: LiveData<List<Post>> = repository.getAll()
    val edited = MutableLiveData(empty)
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)

    fun saveEdit(content: String?) {
        if (content != null) {
            edited.value?.let {
                repository.save(it.copy(content = content))
            }
        }
        resetEdit()
    }

    fun resetEdit() {
        edited.value = empty
    }


    fun edit(post: Post) {
        edited.value = post
    }


}



