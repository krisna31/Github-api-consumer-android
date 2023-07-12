package com.example.krisna31.github_api_consumer.ui.detail_user

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.krisna31.github_api_consumer.R
import com.example.krisna31.github_api_consumer.data.database.FavoriteUser
import com.example.krisna31.github_api_consumer.data.helper.ViewModelFactory
import com.example.krisna31.github_api_consumer.data.response.DetailUserResponse
import com.example.krisna31.github_api_consumer.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel

    private var _activityDetailUserBinding: ActivityDetailUserBinding? = null
    private val binding get() = _activityDetailUserBinding
    private var favUser: FavoriteUser? = null

    companion object {
        const val EXTRA_USERNAME = "EXTRA_USERNAME"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityDetailUserBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[DetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityDetailUserBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()

        detailViewModel = obtainViewModel(this@DetailUserActivity)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        if (username != null) {
            detailViewModel.searchOneUser(username)
            detailViewModel.detailUser.observe(this) { detailUser ->
                setUserData(detailUser)
                detailViewModel.getFavoriteUserByUsername(username)
                detailViewModel.isFavorite.observe(this) { isFavorite ->
                    favUser = FavoriteUser(
                        username,
                        detailUser.avatarUrl,
                    )
                    if (isFavorite) {
                        binding?.fabFavorite?.setImageResource(R.drawable.ic_full_favorite)
                        binding?.fabFavorite?.setOnClickListener {
                            detailViewModel.deleteFavoriteUser(favUser as FavoriteUser)
                            Toast.makeText(
                                this@DetailUserActivity,
                                "User has been deleted from favorite",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        binding?.fabFavorite?.setImageResource(R.drawable.ic_favorite_border)
                        binding?.fabFavorite?.setOnClickListener {
                            detailViewModel.insertFavoriteUser(favUser as FavoriteUser)
                            Toast.makeText(
                                this@DetailUserActivity,
                                "User has been added to favorite",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
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
        binding?.let {
            Glide
                .with(this@DetailUserActivity)
                .load(detailUser.avatarUrl)
                .into(it.civUser)
            it.tvUsername.text = detailUser.login
            it.tvName.text = detailUser.name
            it.tvLocation.text = detailUser.location
            it.tvFollowing.text = "${detailUser.following} Following"
            it.tvFollowers.text = "${detailUser.followers} Followers"
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}