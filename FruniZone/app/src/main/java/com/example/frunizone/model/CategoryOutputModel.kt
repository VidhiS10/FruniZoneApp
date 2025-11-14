package com.example.frunizone.model

import java.io.Serializable

data class CategoryOutputModel(
    var status: Boolean? = null,
    var message: String? = null,
    var category: ArrayList<CategoryModel>? = null
) : Serializable