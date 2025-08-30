package ru.netology.aap.dto

data class Post(
    val id: Int,
    val author: String,
    val content: String,
    val published: String,
    var likes: Int,
    var shares: Int,
    var views: String,
    var likedByMe: Boolean,
    var repostByMe: Boolean,
    var viewByMe: Boolean
)