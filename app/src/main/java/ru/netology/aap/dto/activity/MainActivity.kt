package ru.netology.aap.dto.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.aap.R
import ru.netology.aap.databinding.ActivityMainBinding
import ru.netology.aap.dto.dto.Post
import ru.netology.aap.dto.viewmodel.PostViewModel
import kotlin.getValue

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this, { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                countView.text = post.views.toString()
                countLike.text = Calc.intToText(post.likes)
                countShare.text = post.shares.toString()
                like.setImageResource(
                    if (post.likedByMe) R.drawable.liked_24 else R.drawable.ic_favorite_24
                )
            }
        })
        binding.share.setOnClickListener {
            viewModel.sharing()
        }
        binding.like.setOnClickListener {
            viewModel.like()
        }
    }
}