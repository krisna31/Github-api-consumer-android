package com.example.krisna31.github_api_consumer.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.krisna31.github_api_consumer.data.response.SearchUserItem
import com.example.krisna31.github_api_consumer.databinding.FragmentFollowBinding

/**
 * A simple [Fragment] subclass.
 * Use the [FollowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowFragment : Fragment() {
    private var param1: Int? = null
    private var param2: String? = null

    private var position: Int = 0
    private var username: String = ""

    private lateinit var binding: FragmentFollowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_POSITION)
            param2 = it.getString(ARG_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        val view = binding.root
//        val view = inflater.inflate(R.layout.fragment_follow, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: ""
        }

        val followViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[FollowViewModel::class.java]

        if (position == 1) {
            followViewModel.getFollowers(username)
            followViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                showLoading(isLoading)
            }
            followViewModel.listFollow.observe(viewLifecycleOwner) { followers ->
                setUserData(followers)
            }
        } else {
            followViewModel.getFollowing(username)
            followViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                showLoading(isLoading)
            }
            followViewModel.listFollow.observe(viewLifecycleOwner) { following ->
                setUserData(following)
            }
        }
    }

    private fun setUserData(searchUserItems: List<SearchUserItem>) {
        val listUserAdapter = ListUserAdapter()
        listUserAdapter.submitList(searchUserItems)
        binding.rvFollow.adapter = listUserAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "ARG_POSITION"
        const val ARG_USERNAME = "ARG_USERNAME"
    }
}