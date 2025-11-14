package com.example.frunizone.model

import java.io.Serializable

data class PersonModel (
    var user_id: String? = null,
    var user_name: String? = null,
    var user_email: String? = null,
    var user_password: String? = null,
    var user_phone: String? = null,
    var user_address: String? = null,
    var user_pic: String? = null
    ): Serializable