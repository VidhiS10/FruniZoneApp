package com.example.frunizone.model

data class OrderModel(
    var id: String,
    var uid: String,
    var pid: String,
    var pname: String,
    var ppic: String,
    var amount: String,
    var totalAmount: String,
    var status: String,
    var quantity: String,
    var date: String,
    var time: String,
    var isWishlist: String,
    var state: String
)