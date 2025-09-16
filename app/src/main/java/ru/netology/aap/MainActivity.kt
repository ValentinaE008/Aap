package ru.netology.aap

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
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
            content = "Привет, это новая Нетология! ...",
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
            countLike.text = Calc.intToText(post.likes)  // Changed here

            if (post.likedByMe) {
                like.setImageResource(R.drawable.liked_24)
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
                    if (post.likedByMe) R.drawable.liked_24 else R.drawable.ic_favorite_24
                )
                if (post.likedByMe) post.likes++ else post.likes--
                countLike.text = Calc.intToText(post.likes)  // Changed here
            }

            countShare.text = Calc.intToText(post.shares)  // Changed here
            if (post.repostByMe) {
                share.setImageResource(R.drawable.ic_share_24)
            }

            share.setOnClickListener {
                post.repostByMe = !post.repostByMe
                post.shares++
                countShare.text = Calc.intToText(post.shares)  // Changed here
            }

            countView.text = Calc.intToText(post.views.toInt())  // Changed here

            visability.setOnClickListener {
                post.viewByMe = !post.viewByMe
                post.views = (post.views.toInt() + 1).toString()
                countView.text = Calc.intToText(post.views.toInt())  // Changed here
            }
        }
    }
    object Calc {
        fun intToText(like: Int): String {
            when (like) {
                in 0..999 -> return like.toString()
                in 1000..1099 -> return "1K"
                in 1100..9999 -> return calcLike(like, 1) + "K"
                in 10000..999999 -> return calcLike(like, 0) + "K"
                in 1000000..999999999 -> return calcLike(like, 1) + "M"
                else -> return "Более 1 Billion"
            }
        }

        fun calcLike(like: Int, places: Int): String {

            val df: DecimalFormat
            if (places == 1) {
                df = DecimalFormat("###.#")
            } else {
                df = DecimalFormat("###")
            }
            val liked: Double
            liked = like.toDouble() / 1000
            return df.format(liked)
        }
    }
}