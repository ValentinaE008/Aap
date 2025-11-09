package ru.netology.aap.repository

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.aap.dto.Post
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class PostRepositoryFileImpl(private val context: Context) : PostRepository {

    private var index: Long = 1L
    private var posts = emptyList<Post>()
        set(value) {
            field = value
            data.value = value
            sync()
        }

    private val data = MutableLiveData(posts)

    init {
        val file = context.filesDir.resolve(FILE_NAME)

        if (file.exists()) {
            file.bufferedReader().use { reader ->
                posts = gson.fromJson(reader, type)
                index = (posts.maxOfOrNull { post -> post.id } ?: 0) + 1

            }
        } else {
            sync()
        }
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                val i = if (post.likedByMe) -1 else 1
                post.copy(likes = post.likes + i, likedByMe = !post.likedByMe)
            } else {
                post
            }
        }
    }

    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(shares = post.shares + 1)
            } else post
        }

    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
    }

    override fun save(post: String) {
        TODO("Not yet implemented")
    }


    override fun save(post: Post) {
        posts = if (post.id == 0L) {

            listOf(post.copy(id = index++, author = "Me", published = "now")) + posts
        } else {

            posts.map { existingPost ->
                if (existingPost.id == post.id) {

                    post
                } else {

                    existingPost
                }
            }.toMutableList().also { mutableList ->

                val index = mutableList.indexOfFirst { it.id == post.id }

                if(index != -1){
                    mutableList[index] = post
                }

                posts = mutableList.toList()
            }
        }
        data.value = posts
        sync()
    }


    private fun sync() {
        context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use { writer ->
            writer.write(gson.toJson(posts, type))
        }
    }

    companion object {
        private const val FILE_NAME = "posts.json"

        private val gson = Gson()
        private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type

    }
}

object StringArg : ReadWriteProperty<Bundle, String?> {
    override fun getValue(
        thisRef: Bundle,
        property: KProperty<*>
    ): String? {
        return thisRef.getString(property.name)
    }

    override fun setValue(
        thisRef: Bundle,
        property: KProperty<*>,
        value: String?
    ) {
        thisRef.putString(property.name, value)
    }
}