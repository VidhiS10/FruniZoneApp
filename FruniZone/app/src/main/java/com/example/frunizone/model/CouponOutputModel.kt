package com.example.frunizone.model

import java.io.Serializable

data class CouponOutputModel(
    var status: Boolean? = null,
    var message: String? = null,
    var coupen: ArrayList<CouponModel>? = null
) : Serializable