package com.example.vkfuture


import kotlinx.coroutines.flow.Flow

interface NetworkConnectionObservable {
    fun observe(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}