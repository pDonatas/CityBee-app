package com.example.mainactivity.ui.login

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val id: Int,
    val username: String,
    val email: String,
    val money: Double,
    val banned: Boolean = false,
    val role: Int = 0
)