package com.example.frunizone.model

import java.io.Serializable

data class AddressModel(
    var id: String? = null,
    var user_id: String? = null,
    var full_name: String? = null,
    var phone: String? = null,
    var house_no: String? = null,
    var area: String? = null,
    var landmark: String? = null,
    var city: String? = null,
    var state: String? = null,
    var pincode: String? = null,
    var address_type: String? = null,
    var is_default: String? = null,
    var created_at: String? = null
) : Serializable
