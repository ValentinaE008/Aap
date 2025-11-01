package ru.netology.aap.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.aap.dto.Post
import java.io.File
import java.io.FileReader
import java.io.FileWriter


class PostRepositoryInMemoryImpl(
    private val context: Context,
    private val filename: String = "posts.json"
) : PostRepository {

    private val gson = Gson()

    private val type = object : TypeToken<List<Post>>() {}.type
    private var posts: MutableList<Post> = loadPosts().toMutableList()
    private val data = MutableLiveData(posts.toList())
    private var nextId = posts.maxOfOrNull { it.id }?.plus(1) ?: 1L


    private fun loadPosts(): List<Post> {
        val file = File(context.filesDir, filename)
        return if (file.exists()) {
            try {
                val reader = FileReader(file)
                gson.fromJson(reader, type) ?: emptyList()
            } catch (e: Exception) {
                emptyList()
            }
        } else {

            getInitialPosts().reversed().toMutableList()
        }
    }

    private fun getInitialPosts(): List<Post> {
        var nextId = 1L
        return listOf(

            Post(
                id = nextId++,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                published = "21 мая в 18:36",
                likes = 21,
                shares = 4,
                views = 67,
                video = "https://rutube.ru/video/6550a91e7e523f9503bed47e4c46d0cb",
                shareByMe = false,
                likedByMe = false
            ),
            Post(
                id = nextId++,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных приложений, учимся рассказывать истории и составлять PR-стратегию прямо на бесплатных занятиях \uD83D\uDC47",
                published = "18 сентября в 10:12",
                likes = 11,
                shares = 22,
                views = 33,
                video = null,
                shareByMe = false,
                likedByMe = false
            ),
            Post(
                id = nextId++,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Языков программирования много, и выбрать какой-то один бывает нелегко. Собрали подборку статей, которая поможет вам начать, если вы остановили свой выбор на JavaScript.",
                published = "19 сентября в 10:24",
                likes = (nextId - 1).toInt() * 10,
                shares = 1,
                views = 2,
                video = null,
                shareByMe = false,
                likedByMe = false
            ),
            Post(
                id = nextId++,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Большая афиша мероприятий осени: конференции, выставки и хакатоны для жителей Москвы, Ульяновска и Новосибирска \uD83D\uDE09",
                published = "19 сентября в 14:12",
                likes = (nextId - 1).toInt() * 10,
                shares = 2,
                views = 3,
                video = "https://rutube.ru/video/6550a91e7e523f9503bed47e4c46d0cb",
                shareByMe = false,
                likedByMe = false
            ),
            Post(
                id = nextId++,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Диджитал давно стал частью нашей жизни: мы общаемся в социальных сетях и мессенджерах, заказываем еду, такси и оплачиваем счета через приложения.",
                published = "20 сентября в 10:14",
                likes = (nextId - 1).toInt() * 10,
                shares = 3,
                views = 4,
                video = null,
                shareByMe = false,
                likedByMe = false
            ),
            Post(
                id = nextId++,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "\uD83D\uDE80 24 сентября стартует новый поток бесплатного курса «Диджитал-старт: первый шаг к востребованной профессии» — за две недели вы попробуете себя в разных профессиях и определите, что подходит именно вам → http://netolo.gy/fQ",
                published = "21 сентября в 10:12",
                likes = (nextId - 1).toInt() * 10,
                shares = 5,
                views = 5,
                video = "https://rutube.ru/video/6550a91e7e523f9503bed47e4c46d0cb",
                shareByMe = false,
                likedByMe = false
            ),
            Post(
                id = nextId++,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Таймбоксинг — отличный способ навести порядок в своём календаре и разобраться с делами, которые долго откладывали на потом. Его главный принцип — на каждое дело заранее выделяется определённый отрезок времени. В это время вы работаете только над одной задачей, не переключаясь на другие. Собрали советы, которые помогут внедрить таймбоксинг \uD83D\uDC47\uD83C\uDFFB",
                published = "22 сентября в 10:12",
                likes = (nextId - 1).toInt() * 10,
                shares = 5,
                views = 6,
                shareByMe = false,
                likedByMe = false
            ),
            Post(
                id = nextId++,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Делиться впечатлениями о любимых фильмах легко, а что если рассказать так, чтобы все заскучали \uD83D\uDE34\n",
                published = "22 сентября в 10:14",
                likes = (nextId - 1).toInt() * 10,
                shares = 7,
                views = 7,
                video = "https://rutube.ru/video/6550a91e7e523f9503bed47e4c46d0cb",
                shareByMe = false,
                likedByMe = false
            ),
            Post(
                id = nextId++,
                author = "Нетология. Университет интернет-профессий будущего",
                content = "Освоение новой профессии — это не только открывающиеся возможности и перспективы, но и настоящий вызов самому себе. Приходится выходить из зоны комфорта и перестраивать привычный образ жизни: менять распорядок дня, искать время для занятий, быть готовым к возможным неудачам в начале пути. В блоге рассказали, как избежать стресса на курсах профпереподготовки → http://netolo.gy/fPD",
                published = "23 сентября в 10:12",
                likes = (nextId - 1).toInt() * 10,
                shares = 8,
                views = 6,
                video = null,
                shareByMe = false,
                likedByMe = false
            ),
        )
    }


    private fun savePosts() {
        try {
            val writer = FileWriter(File(context.filesDir, filename))
            gson.toJson(posts, type, writer) // Записываем в файл
            writer.close()
        } catch (e: Exception) {

        }
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id == id) it.copy(
                likedByMe = !it.likedByMe,
                likes = it.likes + (if (it.likedByMe) -1 else 1)
            ) else it
        }.toMutableList()
        saveAndNotify()
    }

    override fun shareById(id: Long) {
        posts =
            posts.map { if (it.id == id) it.copy(shares = it.shares + 1) else it }.toMutableList()
        saveAndNotify()
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            val newPost = post.copy(
                id = nextId++,
                author = "Local",
                published = "Now",
                shares = 0,
                likes = 0,
                views = 0
            )
            posts.add(0, newPost)
        } else {
            posts = posts.map { if (it.id == post.id) it.copy(content = post.content) else it }
                .toMutableList()
        }
        saveAndNotify()
    }

    override fun removeById(id: Long) {
        posts.removeIf { it.id == id }
        saveAndNotify()
    }


    private fun saveAndNotify() {
        data.value = posts.toList()
        savePosts()
    }
}