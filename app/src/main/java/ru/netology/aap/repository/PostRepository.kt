package ru.netology.aap.repository


import androidx.lifecycle.LiveData
import ru.netology.aap.dto.Post


interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
    fun shares()

}