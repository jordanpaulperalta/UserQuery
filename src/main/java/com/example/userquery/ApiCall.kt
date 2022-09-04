package com.example.userquery

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiCall {
    @GET("users")
    @Headers("Accept:application/json",
        "Content-Type:application/json",
        "Authorization: Bearer 4f87d2089168b8a03d1f50be6b4332b2e338b8d7768065914285296620fb3ed2")
    fun getUserByName(@Query("name") name: String, @Query("status") status: String) : Call<UserInfo>
}