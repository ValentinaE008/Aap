package ru.netology.aap.activiti
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.aap.Calc
import ru.netology.aap.R
import ru.netology.aap.databinding.ActivityMainBinding
import ru.netology.aap.viewmodel.PostViewModel


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
                    if (post.likedByMe) R.drawable.ic_liked_24  else R.drawable.ic_favorite_24
                )
            }
        })
        binding.share.setOnClickListener {
            viewModel.saring()
        }
        binding.like.setOnClickListener {
            viewModel.like()
        }


        println("onCreate $this")
    }
    override fun onStart(){
        super.onStart()
        println("onStart $this")
    }

    override fun onStop() {
        super.onStop()
        println("onStop $this")
    }

    override fun onResume() {
        super.onResume()
        println("onResume $this")
    }

    override fun onPause() {
        super.onPause()
        println("onPause $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy $this")
    }

}

private fun PostViewModel.saring() {
    TODO("Not yet implemented")
}

private fun Any.observe(activity: MainActivity, function: Any) {}
