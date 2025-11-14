package com.example.frunizone.model
import java.io.Serializable

data class CategoryModel(
    var cat_id: String? = null,
    var cat_name: String? = null,
    var cat_pic1: String? = null,
    var cat_pic2: String? = null
) : Serializable