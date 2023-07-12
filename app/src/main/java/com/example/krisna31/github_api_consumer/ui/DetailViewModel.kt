package com.example.krisna31.github_api_consumer.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.krisna31.github_api_consumer.data.database.FavoriteUser
import com.example.krisna31.github_api_consumer.data.repository.FavoriteUserRepository
import com.example.krisna31.github_api_consumer.data.response.DetailUserResponse
import com.example.krisna31.github_api_consumer.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    private val mFavoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    fun getFavoriteUserByUsername(username: String) {
        mFavoriteUserRepository.getFavoriteUserByUsername(username).observeForever {
            _isFavorite.value = it != null
        }
    }

    fun insertFavoriteUser(user: FavoriteUser) {
        mFavoriteUserRepository.insert(user)
    }

    fun deleteFavoriteUser(user: FavoriteUser) {
        mFavoriteUserRepository.delete(user)
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }

    init {

    }

    fun searchOneUser(username: String = "krisna31") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailUser.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

}