package com.example.krisna31.github_api_consumer.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.krisna31.github_api_consumer.data.database.FavoriteUser
import com.example.krisna31.github_api_consumer.data.repository.FavoriteUserRepository
import com.example.krisna31.github_api_consumer.data.response.DetailUserResponse

class FavoriteUserVievModel(application: Application) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> {
        return mFavoriteUserRepository.getFavoriteUserByUsername(username)
    }

    companion object {
        private const val TAG = "FavoriteUserVievModel"
    }
}