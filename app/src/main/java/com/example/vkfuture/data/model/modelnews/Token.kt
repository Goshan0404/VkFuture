package com.example.vkfuture.data.model.modelnews

object Token {
    lateinit var accessToken: String
    lateinit var userId: String

    fun setToken(accessToken: String, userId: String) {
        Token.accessToken = accessToken
        Token.userId = userId
    }
}