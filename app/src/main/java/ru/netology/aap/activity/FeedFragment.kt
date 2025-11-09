package ru.netology.aap.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.aap.R
import ru.netology.aap.adapter.OnInteractorListener
import ru.netology.aap.adapter.PostsAdapter
import ru.netology.aap.databinding.FragmentFeedBinding
import ru.netology.aap.dto.Post
import ru.netology.aap.viewmodel.PostViewModel

class FeedFragment : Fragment() {

    private lateinit var editPostLauncher: ActivityResultLauncher<String>
    private val viewModel: PostViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        editPostLauncher = registerForActivityResult(EditPostActivityContract()) { result ->
            result?.let { newContent ->
                viewModel.save(newContent)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = PostsAdapter(onInteractionListener = object : OnInteractorListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
                findNavController().navigate(
                    R.id.action_feedFragment_to_newPostFragment,
                    Bundle().apply {
                        putString("textArgs", post.content)
                    }
                )
            }

            override fun onLike(post: Post) {
                viewModel.like(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShare(post: Post) {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val chooserIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(chooserIntent)
                viewModel.share(post.id)
            }

            override fun onPlayVideo(post: Post) {
                post.video?.let { videoUri ->
                    val intent = Intent(Intent.ACTION_VIEW, videoUri.toUri())
                    startActivity(intent)
                }
            }

            override fun onPostClicked(post: Long) {
                findNavController().navigate(
                    R.id.action_feedFragment_to_singlePostFragment,
                    Bundle().apply { putLong("postId", post) })
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {

            viewModel.edit(
                Post(
                    id = 0,
                    author = "Me",
                    content = "",
                    published = "now",
                    likes = 0,
                    shares = 0,
                    views = 0,
                    video = null,
                    likedByMe = false,
                    shareByMe = false
                )
            )
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}