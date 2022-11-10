package com.demo.assignment.model.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.demo.assignment.model.remote.Api
import com.demo.assignment.model.remote.ApiCallback
import com.demo.assignment.model.remote.SafeApiCall
import com.demo.assignment.model.remote.response.RepoResponse
import com.demo.assignment.util.Coroutines
import javax.inject.Inject

class ItemRepository @Inject constructor(
    private val api: Api
) : SafeApiCall {
    val repoResponse = MutableLiveData<RepoResponse>()

    companion object {
        const val TAG = "APP_TAG_REPO"
    }

    fun getRepositories() {
        Coroutines.io {
            fetchRepositories()
        }
    }

    private suspend fun fetchRepositories() {
        val resp = safeApiCall { api.getRepositories() }
        when (resp) {
            is ApiCallback.Success -> {
                val _repoResponse = RepoResponse()
                _repoResponse.status = "success"
                _repoResponse.repositories = resp.value
                repoResponse.postValue(_repoResponse)
                Log.d(TAG, "Repositories successfully fetched from remote")
            }
            is ApiCallback.Failure -> {
                val _repoResponse = RepoResponse()
                if (resp.isNetworkError) {
                    _repoResponse.status = "networkError"
                    Log.d(TAG, "Network error in fetching repositories from remote")
                } else {
                    _repoResponse.status = "apiError"
                    _repoResponse.statusCode = resp.errorCode
                    Log.d(TAG, "API error in fetching repositories from remote with error code: ${resp.errorCode}")
                }
                repoResponse.postValue(_repoResponse)
            }
        }
    }
}