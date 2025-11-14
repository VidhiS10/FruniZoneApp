package com.example.frunizone.model

import java.io.Serializable

data class BannerModel (
    var ban_id: String? = null,
    var ban_img: String? = null,
    var ban_title: String? = null,
    var status: String? = null
    ) : Serializable
