package ru.netology.aap.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ru.netology.aap.dto.Post
import ru.netology.aap.repository.PostRepository
import ru.netology.aap.repository.PostRepositoryInMemory


class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemory()
    val data: LiveData<Post> = repository.get()
    fun like() = repository.like()
    fun shares() = repository.shares()
}