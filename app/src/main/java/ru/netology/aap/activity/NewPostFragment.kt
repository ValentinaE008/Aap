package ru.netology.aap.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.aap.databinding.FragmentNewPostBinding
import ru.netology.aap.viewmodel.PostViewModel


class NewPostFragment : Fragment() {

    private var _binding: FragmentNewPostBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PostViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val textArgs = arguments?.getString("textArgs") ?: ""
        binding.edit.setText(textArgs)


        viewModel.edited.observe(viewLifecycleOwner) { post ->

            if (post.id != 0L && binding.edit.text.toString() != post.content) {
                binding.edit.setText(post.content)
            }
        }

        binding.save.setOnClickListener {

            val content: String = binding.edit.text.toString()

            if (viewModel.edited.value?.id != 0L){

                val post = viewModel.edited.value?.copy(content = content)

                post?.let { viewModel.save(it.toString()) }
            } else {

                viewModel.save(content)
            }
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


