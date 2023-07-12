package com.example.krisna31.github_api_consumer.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.krisna31.github_api_consumer.data.response.SearchUser
import com.example.krisna31.github_api_consumer.data.response.SearchUserItem
import com.example.krisna31.github_api_consumer.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _searchUserItem = MutableLiveData<List<SearchUserItem>>()
    val listSearchUser: LiveData<List<SearchUserItem>> = _searchUserItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        findUser()
    }

    fun findUser(username: String = "krisna31") {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(username)
        client.enqueue(object : Callback<SearchUser> {
            override fun onResponse(
                call: Call<SearchUser>,
                response: Response<SearchUser>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _searchUserItem.value = responseBody.users
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchUser>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}