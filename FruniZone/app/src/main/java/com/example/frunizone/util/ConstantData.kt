package com.example.frunizone.util

object ConstantData {
    //Base server address for all API calls
    const val SERVER_ADDRESS = "http://192.168.29.6/"


//Base path to fetch images from server
    // http://192.168.1.39/furniture/api/prod_from_cate_api.php
    const val SERVER_IMAGE_ADDRESS = "http://192.168.29.6/furniture/admin"

    //endpoints for your API calls
    const val REGISTER_URL = "furniture/api/register.php"
    const val LOGIN_URL = "furniture/api/login_api.php"
    const val BANNER_URL = "furniture/api/bannerapi.php"
    const val CATEGORY_URL = "furniture/api/category.php"
    const val FURNITURE_URL = "furniture/api/sub_catapi.php"
    const val FURNITURE_URL1 = "furniture/api/prod_from_cate_api.php"
    const val EDIT_PROFILE_URL = "furniture/api/editprofileapi.php"
    const val COUPON_URL = "furniture/api/Coupen.php"
    const val ORDER_URL = "furniture/api/add_orders_api.php"
    const val COUPON_DISCOUNT_URL = "furniture/api/getDiscountApi.php"
    const val GET_PENDING_ORDER_URL = "furniture/api/getorder_api.php"
    const val REMOVE_ORDER = "furniture/api/removeOrder.php"
    const val UPDATE_ORDER = "furniture/api/updateqtyapi.php"
    const val CONFIRM_ORDER = "furniture/api/confirm_order_api.php"
    const val COMPLETED_ORDER = "furniture/api/order_history_api.php"
    const val CANCLE_ORDER = "furniture/api/cancel_order_api.php"
    const val SEARCH_API = "furniture/api/searchApi.php"
    const val UPDATE_PROFILE_URL = "furniture/api/update_profile.php"



    //keys for SharedPreferences, JSON parsing, or intent extras
    const val KEY_ID = "key_id"
    const val KEY_USERNAME = "key_username"
    const val KEY_EMAIL = "key_email"
    const val KEY_PHONE = "key_phone"
    const val KEY_PIC = "key_pic"
    const val KEY_PWD = "key_pwd"


    //Name of the SharedPreferences file used to store user login info
    const val SP_LOGIN_PREFS = "sp_login_pref"
}