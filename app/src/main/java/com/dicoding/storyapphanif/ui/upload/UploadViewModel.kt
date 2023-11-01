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

    private val _responseUpload = MediatorLiveData<Result<UploadStoryResponse>> ()
    val responseUpload : LiveData<Result<UploadStoryResponse>> = _responseUpload


    fun uploadStory (token: String, file: MultipartBody.Part, description: RequestBody) {
        val liveData = repository.uploadStory(token, file, description)
        _responseUpload.addSource(liveData){result -> _responseUpload.value = result}
    }

    fun getSession() : LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

 }