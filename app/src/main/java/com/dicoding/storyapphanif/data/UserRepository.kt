package com.dicoding.storyapphanif.data


import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.storyapphanif.data.pref.UserModel
import com.dicoding.storyapphanif.data.pref.UserPreference
import com.dicoding.storyapphanif.data.retrofit.ApiService
import com.dicoding.storyapphanif.data.retrofit.response.ListStoryItem
import com.dicoding.storyapphanif.data.retrofit.response.LoginResponse
import com.dicoding.storyapphanif.data.retrofit.response.RegisterResponse
import com.dicoding.storyapphanif.data.retrofit.response.UploadStoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService : ApiService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun registerStory (
        name: String ,
        email: String ,
        password : String
    ) : LiveData<Result<RegisterResponse>>  = liveData (Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val response = apiService.registerStory(name , email , password)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun loginStory(email: String, password: String): LiveData<Result<LoginResponse>> = liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.loginStory(email, password)
                val token = response.loginResult.token
                saveSession(UserModel(email, token))
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    fun uploadStory(token: String, file: MultipartBody.Part, description: RequestBody): LiveData<Result<UploadStoryResponse>> = liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.uploadStory("Bearer $token", file, description)
                emit(Result.Success(response))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    fun getStory(token: String): LiveData<Result<List<ListStoryItem>>> = liveData(Dispatchers.IO) {
            emit(Result.Loading)
            try {
                val response = apiService.getStory("Bearer $token")
                val storyList = response.listStory
                emit(Result.Success(storyList))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }



    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference , apiService)
            }.also { instance = it }
    }
}