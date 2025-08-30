package ru.netology.aap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import ru.netology.aap.databinding.ActivityMainBinding
import ru.netology.aap.dto.Post


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb\"",
            published = "21 мая в 18:36",
            likes = 100,
            shares = 98,
            views = "999",
            likedByMe = false,
            repostByMe = false,
            viewByMe = false
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            countLike.text = formatNumber(post.likes)

            if (post.likedByMe) {
                like.setImageResource(R.drawable.ic_liked_24)
            }

            root.setOnClickListener {
                Log.d("stuff", "stuff")
            }

            imgAvatar.setOnClickListener {
                Log.d("stuff", "avatar")
            }

            like.setOnClickListener {
                Log.d("stuff", "like")
                post.likedByMe = !post.likedByMe
                like.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_favorite_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                countLike.text = formatNumber(post.likes)
            }

            countShare.text = formatNumber(post.shares)
            if (post.repostByMe) {
                share.setImageResource(R.drawable.ic_share_24)
            }

            share.setOnClickListener {
                post.repostByMe = !post.repostByMe
                post.shares++
                countShare.text = formatNumber(post.shares)
            }

            countView.text = formatNumber(post.views.toInt())

            visability.setOnClickListener {
                post.viewByMe = !post.viewByMe
                post.views = (post.views.toInt() + 1).toString()
                countView.text = formatNumber(post.views.toInt())
            }
        }
    }

    private fun formatNumber(number: Int): String {
        return when {
            number < 1000 -> number.toString()
            number < 10000 -> String.format("%.1fK", number / 1000.0)
            number < 1000000 -> "${number / 1000}K"
            else -> String.format("%.1fM", number / 1000000.0)
        }
    }
}