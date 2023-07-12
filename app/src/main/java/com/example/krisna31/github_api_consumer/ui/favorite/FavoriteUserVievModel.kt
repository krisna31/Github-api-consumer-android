package com.example.krisna31.github_api_consumer.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.krisna31.github_api_consumer.data.database.FavoriteUser
import com.example.krisna31.github_api_consumer.data.repository.FavoriteUserRepository

class FavoriteUserVievModel(application: Application) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    fun getAllNotes(): LiveData<List<FavoriteUser>> {
        _isLoading.value = true
        val favUser = mFavoriteUserRepository.getAllFavoriteUser()
        _isLoading.value = false
        return favUser
    }

    companion object {
        private const val TAG = "FavoriteUserVievModel"
    }
}