package com.example.frunizone.model

import java.io.Serializable

data class BannerOutputModel(
    var status: Boolean? = null,
    var message: String? = null,
    var banner: ArrayList<BannerModel>? = null
) : Serializable