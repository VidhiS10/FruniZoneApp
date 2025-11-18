package com.example.frunizone.model

import java.io.Serializable

data class FurnitureModel(
    var cid: String,
    var sub_cat_id: String,
    var sub_cat_name: String,
    var sub_cat_color: String,
    var sub_cat_price: String,
    var sub_cat_discount: String,
    var sub_cat_description: String,
    var sub_cat_dimention: String,
    var sub_cat_weight: String,
    var sub_cat_primary_material: String,
    var sub_cat_warenty: String,
    var sub_cat_product_rating: String,
    var sub_cat_sku: String,
    var sub_cat_specification: String,
    var sub_cat_pic1: String,
    var sub_cat_pic2: String,
    var sub_cat_pic3: String,
    var sub_cat_pic4: String
) : Serializable