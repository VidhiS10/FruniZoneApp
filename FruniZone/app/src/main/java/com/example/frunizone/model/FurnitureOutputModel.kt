package com.example.frunizone.model

data class FurnitureOutputModel(
    var status: Boolean,
    var message: String,
    var subCategory: ArrayList<FurnitureModel>
)