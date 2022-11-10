package com.demo.assignment.ui

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.demo.assignment.getOrAwaitValue
import com.demo.assignment.model.remote.Api
import com.demo.assignment.model.remote.RemoteDataSource
import com.demo.assignment.model.repository.ItemRepository
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemViewModelTest {

    private lateinit var viewModel: ItemViewModel

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val remoteDataSource = RemoteDataSource()
        val api = remoteDataSource.buildApi(context, Api::class.java)
        val repository = ItemRepository(api)
        viewModel = ItemViewModel(repository)
    }

    @Test
    fun testRepoViewModel() {
        viewModel.getRepositories()
        val result = viewModel.itemLiveData.getOrAwaitValue()
        assertThat(result != null).isTrue()
    }
}