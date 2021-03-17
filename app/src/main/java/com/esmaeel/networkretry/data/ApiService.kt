package com.esmaeel.networkretry.data

import retrofit2.http.GET

interface ApiService {
    @GET("users?page=1")
    suspend fun getUsers(): UsersResponseModel
}