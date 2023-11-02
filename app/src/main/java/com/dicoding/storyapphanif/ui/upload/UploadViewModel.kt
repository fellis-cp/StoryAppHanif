package com.dicoding.storyapphanif.ui.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.storyapphanif.data.Result
import com.dicoding.storyapphanif.data.UserRepository
import com.dicoding.storyapphanif.data.pref.UserModel
import com.dicoding.storyapphanif.data.retrofit.response.UploadStoryResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UploadViewModel (private val repository: UserRepository) : ViewModel(){

    private val _responseUploadStory = MediatorLiveData<Result<UploadStoryResponse>> ()
    val responseUploadStory : LiveData<Result<UploadStoryResponse>> = _responseUploadStory


    fun uploadStory (token: String, file: MultipartBody.Part, description: RequestBody) {
        val liveData = repository.uploadStory(token, file, description)
        _responseUploadStory.addSource(liveData){result -> _responseUploadStory.value = result}
    }

    fun getSession() : LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

 }