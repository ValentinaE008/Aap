package ru.netology.aap.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int = 10,
    var shares: Int = 3,
    var views: Int,
    val likedByMe: Boolean,
    var repostByMe: Boolean,
    var viewByMe: Boolean,

    )