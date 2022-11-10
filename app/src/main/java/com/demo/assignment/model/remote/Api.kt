package com.demo.assignment.model.remote

import com.demo.assignment.model.pojo.Item
import retrofit2.http.GET

interface Api {

    @GET("repositories")
    suspend fun getRepositories(): List<Item>
}