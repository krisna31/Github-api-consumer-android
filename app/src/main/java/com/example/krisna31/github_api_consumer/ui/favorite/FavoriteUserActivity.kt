package com.example.krisna31.github_api_consumer.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.krisna31.github_api_consumer.R
import com.example.krisna31.github_api_consumer.data.helper.ViewModelFactory
import com.example.krisna31.github_api_consumer.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {
    private var _activityFavoriteUserBinding: ActivityFavoriteUserBinding? = null
    private val binding get() = _activityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteUserBinding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = getString(R.string.favorite_user)
        
        val favoriteUserVievModel = obtainViewModel(this@FavoriteUserActivity)
        favoriteUserVievModel.getAllNotes().observe(this) { listUser ->
            if (listUser != null) {
                binding?.rvUser?.layoutManager = LinearLayoutManager(this)
                binding?.rvUser?.setHasFixedSize(true)
                val adapter = FavoriteUserAdapter()
                adapter.submitList(listUser)
                binding?.rvUser?.adapter = adapter
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserVievModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteUserVievModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteUserBinding = null
    }
}