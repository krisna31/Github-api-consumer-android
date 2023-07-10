package com.example.krisna31.github_api_consumer.data.retrofit

import com.example.krisna31.github_api_consumer.data.response.SearchUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("search/users?q={username}")
    fun getUser(
        @Path("username") username: String
    ): Call<SearchUser>
}