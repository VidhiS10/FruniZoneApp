package com.example.frunizone.model

import java.io.Serializable

data class FurnitureModel(
    var cid: String,
    var subCatId: String,
    var subCatName: String,
    var subCatColor: String,
    var subCatPrice: String,
    var subCatDiscount: String,
    var subCatDescription: String,
    var subCatDimention: String,
    var subCatWeight: String,
    var subCatPrimaryMaterial: String,
    var subCatWarenty: String,
    var subCatProductRating: String,
    var subCatSku: String,
    var subCatSpecification: String,
    var subCatPic1: String,
    var subCatPic2: String,
    var subCatPic3: String,
    var subCatPic4: String
) : Serializable