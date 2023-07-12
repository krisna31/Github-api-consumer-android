package com.example.krisna31.github_api_consumer.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.krisna31.github_api_consumer.data.database.FavoriteUser
import com.example.krisna31.github_api_consumer.data.database.FavoriteUserDao
import com.example.krisna31.github_api_consumer.data.database.FavoriteUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> =
        mFavoriteUserDao.getFavoriteUserByUsername(username)

    fun insert(user: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.insert(user) }
    }

    fun delete(user: FavoriteUser) {
        executorService.execute { mFavoriteUserDao.delete(user) }
    }
}