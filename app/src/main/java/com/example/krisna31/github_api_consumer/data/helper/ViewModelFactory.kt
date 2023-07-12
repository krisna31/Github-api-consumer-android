package com.example.krisna31.github_api_consumer.data.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.krisna31.github_api_consumer.data.datastore.SettingPreferences
import com.example.krisna31.github_api_consumer.ui.detail_user.DetailViewModel
import com.example.krisna31.github_api_consumer.ui.favorite.FavoriteUserVievModel
import com.example.krisna31.github_api_consumer.ui.settings.SettingsVievModel

class ViewModelFactory constructor(
    private val mApplication: Application? = null,
    private val pref: SettingPreferences? = null
) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(mApplication!!) as T
        } else if (modelClass.isAssignableFrom(FavoriteUserVievModel::class.java)) {
            return FavoriteUserVievModel(mApplication!!) as T
        } else if (modelClass.isAssignableFrom(SettingsVievModel::class.java)) {
            return SettingsVievModel(pref!!) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
