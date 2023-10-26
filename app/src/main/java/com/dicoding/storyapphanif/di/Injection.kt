package com.dicoding.storyapphanif.di

import android.content.Context
import com.dicoding.storyapphanif.data.UserRepository
import com.dicoding.storyapphanif.data.pref.UserPreference
import com.dicoding.storyapphanif.data.pref.dataStore


object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}