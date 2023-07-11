package com.example.krisna31.github_api_consumer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.krisna31.github_api_consumer.R

/**
 * A simple [Fragment] subclass.
 * Use the [FollowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingView: ProgressBar

    private var position: Int = 0
    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_POSITION)
            param2 = it.getString(ARG_USERNAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_follow, container, false)

//        recyclerView = view.findViewById(R.id.rv_follow)
        loadingView = view.findViewById(R.id.progressBar)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME) ?: ""
        }

        // Tampilkan username pada TextView sebagai tes
        val textView: TextView = view.findViewById(R.id.testUsername)
        textView.text = if (position == 1) {
            "Get Follower $username"
        } else {
            "Get Following $username"
        }

        // Implementasikan logika untuk menampilkan data RecyclerView
        // Gunakan position dan username untuk mengambil data dari API
        // Tampilkan data menggunakan RecyclerView dengan adapter dan layout manager
    }

    companion object {
        const val ARG_POSITION = "ARG_POSITION"
        const val ARG_USERNAME = "ARG_USERNAME"
    }
}