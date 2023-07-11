package com.example.krisna31.github_api_consumer.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.krisna31.github_api_consumer.R
import com.example.krisna31.github_api_consumer.data.response.DetailUserResponse
import com.example.krisna31.github_api_consumer.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        const val EXTRA_USERNAME = "EXTRA_USERNAME"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username != null) {
            detailViewModel.searchOneUser(username)
            detailViewModel.detailUser.observe(this) { detailUser ->
                setUserData(detailUser)
            }
            detailViewModel.isLoading.observe(this) { isLoading ->
                showLoading(isLoading)
            }

            val sectionsPagerAdapter = SectionsFollowPagerAdapter(this)
            sectionsPagerAdapter.setUsername(username)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabs)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
                viewPager.adapter = sectionsPagerAdapter
            }.attach()
            supportActionBar?.elevation = 0f
        }
    }

    private fun setUserData(detailUser: DetailUserResponse) {
        Glide
            .with(this)
            .load(detailUser.avatarUrl)
            .into(binding.civUser)
        binding.tvUsername.text = detailUser.login
        binding.tvName.text = detailUser.name
        binding.tvLocation.text = detailUser.location
        binding.tvFollowing.text = "${detailUser.following} Following"
        binding.tvFollowers.text = "${detailUser.followers} Followers"
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}