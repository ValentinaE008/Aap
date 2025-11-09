package ru.netology.aap.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.aap.R
import ru.netology.aap.databinding.FragmentSinglePostBinding
import ru.netology.aap.util.StringArg
import ru.netology.aap.viewmodel.PostViewModel


class SinglePostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSinglePostBinding.inflate(inflater, container, false)

        val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

        val postId = arguments?.getLong("postId") ?: 0L


        viewModel.data.observe(viewLifecycleOwner) { posts ->

            val post = posts.find { it.id == postId }

            post?.let {

                with(binding.post) {
                    author.text = it.author
                    content.text = it.content
                    published.text = it.published
                    like.apply {
                        isChecked = it.likedByMe
                        text = Calc(it.likes)

                        setOnClickListener { viewModel.like(postId) }
                    }
                    share.apply {
                        text = Calc(it.shares)
                        setOnClickListener {

                            val intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, post.content)
                                type = "text/plain"
                            }

                            val intent2 =
                                Intent.createChooser(intent, getString(R.string.chooser_share_post))
                            startActivity(intent2)

                            viewModel.share(postId)
                        }
                    }

                    menu.setOnClickListener { menuView ->

                        PopupMenu(menuView.context, menuView).apply {
                            inflate(R.menu.menu_post)
                            setOnMenuItemClickListener { item ->
                                when (item.itemId) {
                                    R.id.remove -> {

                                        viewModel.removeById(postId)

                                        findNavController().popBackStack()
                                        true
                                    }

                                    R.id.edit -> {

                                        viewModel.edit(post)

                                        findNavController().navigate(
                                            R.id.action_feedFragment_to_newPostFragment,
                                            Bundle().apply { textArgs = post.content })
                                        true
                                    }

                                    else -> false
                                }
                            }
                        }.show()
                    }
                }
            }
        }
        return binding.root
    }

    companion object {

        var Bundle.textArgs by StringArg
    }
}