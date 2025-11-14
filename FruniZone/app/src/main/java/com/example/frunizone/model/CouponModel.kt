package com.example.frunizone.model

import java.io.Serializable

data class CouponModel(
    var coupen_id: String? = null,
    var coupen_title: String? = null,
    var coupen_code: String? = null,
    var coupen_description: String? = null,
    var coupen_img: String? = null,
    var coupen_discount: String? = null
) : Serializable