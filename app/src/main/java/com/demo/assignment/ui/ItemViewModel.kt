package com.demo.assignment.ui

import android.util.Log
import androidx.lifecycle.*
import com.demo.assignment.model.pojo.Item
import com.demo.assignment.model.remote.response.RepoResponse
import com.demo.assignment.model.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {
    companion object {
        const val TAG = "APP_TAG_ViewModel"
    }

    //Live data for default Repository list
    private var _itemLiveData: MutableLiveData<List<Item>> = MutableLiveData()
    val itemLiveData: LiveData<List<Item>>
        get() = _itemLiveData

    //Live Data for network error
    private var _isNetworkErrorLiveData: MutableLiveData<Boolean?> = MutableLiveData()
    val isErrorLiveData: LiveData<Boolean?>
        get() = _isNetworkErrorLiveData

    private val responseObserver = Observer<RepoResponse> {
        when (it?.status) {
            "success" -> {
                it.repositories?.let { list ->
                    _itemLiveData.value= list
                    _isNetworkErrorLiveData.value = null
                }
            }
            "networkError" -> {
                _isNetworkErrorLiveData.value = true
            }
            "apiError" -> {
                _isNetworkErrorLiveData.value = false
            }
        }
    }

    init {
        Log.d(TAG, "RepoViewModel Initialized")
        itemRepository.repoResponse.observeForever(responseObserver)
        getRepositories()
    }

    fun getRepositories() {
        itemRepository.getRepositories()
    }

    override fun onCleared() {
        Log.d(TAG, "ViewModel's onCleared ")
        itemRepository.repoResponse.removeObserver(responseObserver)
        super.onCleared()
    }
}