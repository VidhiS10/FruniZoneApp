package com.example.frunizone.model

data class OrderModel(
    var id: String,
    var uid: String,
    var pid: String,
    var pname: String,
    var ppic: String,
    var amount: String,
    var total_amount: String,
    var status: String,
    var quantity: String,
    var date: String,
    var time: String,
    var is_wishlist: String,
    var state: String
)