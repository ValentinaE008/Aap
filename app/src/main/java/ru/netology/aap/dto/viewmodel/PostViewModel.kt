package ru.netology.aap.dto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.netology.aap.dto.dto.Post
import ru.netology.aap.dto.repository.PostRepository
import ru.netology.aap.dto.repository.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data: LiveData<Post> = repository.get()
    fun like() = repository.like()
    fun sharing() = repository.sharing()
}