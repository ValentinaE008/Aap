package ru.netology.aap.dto.repository

import androidx.lifecycle.LiveData
import ru.netology.aap.dto.dto.Post

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun sharing()
}