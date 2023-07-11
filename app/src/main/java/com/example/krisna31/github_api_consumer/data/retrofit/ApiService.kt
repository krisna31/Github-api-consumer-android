package com.example.krisna31.github_api_consumer.data.retrofit

import com.example.krisna31.github_api_consumer.data.response.DetailUserResponse
import com.example.krisna31.github_api_consumer.data.response.SearchUser
import com.example.krisna31.github_api_consumer.data.response.SearchUserItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUser(
        @Query("q") username: String
    ): Call<SearchUser>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<SearchUserItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<SearchUserItem>>
}