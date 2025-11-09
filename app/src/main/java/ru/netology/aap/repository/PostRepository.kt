package ru.netology.aap.repository

import androidx.lifecycle.LiveData
import ru.netology.aap.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>


    fun likeById(id: Long)
    fun shareById(id: Long)
    fun removeById(id: Long)
    fun save(post: String)
    fun save(post: Post)
}