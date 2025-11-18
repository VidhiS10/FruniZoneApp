package com.example.frunizone.model
import com.google.gson.annotations.SerializedName

data class FurnitureOutputModel(
    val status: Boolean = false,
    val message: String = "",
    @SerializedName("sub_category")
    val sub_category: ArrayList<FurnitureModel> = arrayListOf()
)
