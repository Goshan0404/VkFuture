package com.example.vkfuture.data.model.modelprofiledetails

data class Response(
    val bdate: String,
    val bdate_visibility: Int,
    val city: City,
    val country: Country,
    val first_name: String,
    val home_town: String,
    val id: Int,
    val is_sber_verified: Boolean,
    val is_service_account: Boolean,
    val is_tinkoff_linked: Boolean,
    val is_tinkoff_verified: Boolean,
    val last_name: String,
    val oauth_linked: List<Any>,
    val oauth_verification: List<Any>,
    val phone: String,
    val photo_200: String,
    val relation: Int,
    val screen_name: String,
    val sex: Int,
    val status: String,
    val verification_status: String
)