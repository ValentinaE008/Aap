package ru.netology.aap.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int,
    val shares: Int,
    val views: Int,
    val video: String? = null,
    val likedByMe: Boolean,
    val shareByMe: Boolean

)