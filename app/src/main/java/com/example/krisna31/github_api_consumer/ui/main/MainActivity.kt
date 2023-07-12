package com.example.krisna31.github_api_consumer.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.krisna31.github_api_consumer.data.DataStore.SettingPreferences
import com.example.krisna31.github_api_consumer.data.DataStore.dataStore
import com.example.krisna31.github_api_consumer.data.helper.ViewModelFactory
import com.example.krisna31.github_api_consumer.data.response.SearchUserItem
import com.example.krisna31.github_api_consumer.databinding.ActivityMainBinding
import com.example.krisna31.github_api_consumer.ui.adapter.UserAdapter
import com.example.krisna31.github_api_consumer.ui.favorite.FavoriteUserActivity
import com.example.krisna31.github_api_consumer.ui.settings.SettingsActivity
import com.example.krisna31.github_api_consumer.ui.settings.SettingsVievModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingsViewModel = ViewModelProvider(
            this,
            ViewModelFactory(null, pref)
        )[SettingsVievModel::class.java]
        settingsViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    val username = textView.text.toString()
                    if (username.isNotEmpty()) {
                        val mainViewModel = ViewModelProvider(
                            this@MainActivity,
                            ViewModelProvider.NewInstanceFactory()
                        )[MainViewModel::class.java]
                        mainViewModel.findUser(username)
                        mainViewModel.listSearchUser.observe(this@MainActivity) { searchUserItems ->
                            setUserData(searchUserItems)
                        }
                        mainViewModel.isLoading.observe(this@MainActivity) { isLoading ->
                            showLoading(isLoading)
                        }
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Please input username",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    false
                }
        }

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        mainViewModel.listSearchUser.observe(this) { searchUserItems ->
            setUserData(searchUserItems)
        }
        mainViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        binding.civFavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteUserActivity::class.java))
        }

        binding.civSetting.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun setUserData(searchUserItems: List<SearchUserItem>) {
        val adapter = UserAdapter()
        adapter.submitList(searchUserItems)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}