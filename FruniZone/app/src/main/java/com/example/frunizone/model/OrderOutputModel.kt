package com.example.frunizone.model

data class OrderOutputModel(
    var status: Boolean,
    var message: String,
    var order: ArrayList<OrderModel>
)