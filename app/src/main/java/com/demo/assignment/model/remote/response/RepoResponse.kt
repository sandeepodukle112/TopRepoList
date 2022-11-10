package com.demo.assignment.model.remote.response

import com.demo.assignment.model.pojo.Item

data class RepoResponse(
    var status: String = "",
    var statusCode: Int? = null,
    var isNetworkError: Boolean? = null,
    var repositories: List<Item>? = null
)