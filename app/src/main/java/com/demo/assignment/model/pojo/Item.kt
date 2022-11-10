package com.demo.assignment.model.pojo

data class Item(
    var id: Int,
    var node_id: String? = null,
    var name: String? = null,
    var description: String? = null,
    var owner: Owner? = null,
    var isSelected: Boolean = false
)