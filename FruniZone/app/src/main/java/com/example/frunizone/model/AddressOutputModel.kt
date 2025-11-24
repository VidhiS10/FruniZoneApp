package com.example.frunizone.model

data class AddressOutputModel(
    var status: Boolean = false,
    var message: String? = null,
    var addresses: ArrayList<AddressModel>? = null
)
