package ru.netology.aap.repository

import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.aap.dto.Post

class PostRepositorySharedPrefsImpl(
    context: Context,
) : PostRepository {

    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private var nextId = 1L // Id для новых постов
    private var posts = emptyList<Post>() // список постов
        set(value) {
            field = value // сохраняем новый список
            sync() // синхронизируем (сохраняем) в SharedPreferences
        }
    private val data = MutableLiveData(posts) // LiveData для наблюдения за списком

    init {
        // при инициализации загружаем данные из SharedPreferences
        prefs.getString(KEY_POSTS, null)?.let { value ->
            posts = gson.fromJson(value, type) // десериализуем JSON в список
            nextId = (posts.maxOfOrNull { it.id } ?: 0) + 1 // вычисляем nextId
            data.value = posts // обновляем LiveData
        }
    }

    override fun getAll(): LiveData<List<Post>> = data // возвращаем LiveData

    override fun save(post: Post) {
        if (post.id == 0L) {
            // сохранение нового поста
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    likedByMe = false,
                    published = "now"
                )
            ) + posts // добавляем новый пост в начало списка
            data.value = posts // обновляем LiveData
            return
        }

        // Обновление существующего поста
        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content)
        }
        data.value = posts // обновляем LiveData
    }

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts // обновляем LiveData
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shares = it.shares +1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id } // удаляем пост по id
        data.value = posts // обновляем LiveData
    }

    override fun save(post: String) {
        TODO("Not yet implemented")
    }

    private fun sync() {
        prefs.edit { // используем extension function для SharedPreferences.Editor
            putString(KEY_POSTS, gson.toJson(posts)) // сериализуем в JSON и сохраняем
        }
    }

    companion object {
        private const val KEY_POSTS = "posts" // ключ для SharedPreferences

        private val gson = Gson() // Gson instance
        private val type = object : TypeToken<List<Post>>() {}.type
    }
}