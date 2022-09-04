package com.example.userquery

data class UserInfo(val data: List<User>)

data class User(
    val id: Int?,
    val name: String,
    val email: String,
    val gender: String,
    val status: String
)