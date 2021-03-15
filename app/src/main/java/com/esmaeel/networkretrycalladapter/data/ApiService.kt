package com.esmaeel.networkretrycalladapter.data

import retrofit2.http.GET

interface ApiService {
    @GET("users?page=1")
    suspend fun getUsers(): UsersResponseModel
}