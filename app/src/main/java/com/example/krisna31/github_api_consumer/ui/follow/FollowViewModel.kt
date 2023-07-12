package com.example.krisna31.github_api_consumer.ui.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.krisna31.github_api_consumer.data.response.SearchUserItem
import com.example.krisna31.github_api_consumer.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    companion object {
        private const val TAG = "FollowViewModel"
    }

    val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val _listFollow = MutableLiveData<ArrayList<SearchUserItem>>()
    val listFollow: LiveData<ArrayList<SearchUserItem>> = _listFollow

    fun getFollowers(username: String) {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        apiService.getFollowers(username).enqueue(object : Callback<List<SearchUserItem>> {
            override fun onResponse(
                call: Call<List<SearchUserItem>>,
                response: Response<List<SearchUserItem>>
            ) {
                if (response.isSuccessful) {
                    val followers = response.body()
                    if (followers != null) {
                        _listFollow.value = followers as ArrayList<SearchUserItem>?
                    }
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<List<SearchUserItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        apiService.getFollowing(username).enqueue(object : Callback<List<SearchUserItem>> {
            override fun onResponse(
                call: Call<List<SearchUserItem>>,
                response: Response<List<SearchUserItem>>
            ) {
                if (response.isSuccessful) {
                    val following = response.body()
                    if (following != null) {
                        _listFollow.value = following as ArrayList<SearchUserItem>?
                    }
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<List<SearchUserItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

}