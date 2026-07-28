package org.example.utils.org.example.utils.dto

data class UserDto(
    val uid: String,
    val email: String,
    val displayName: String?,
    val phone:String?,
    val pinHash: String?,
    val biometricEnabled: Boolean
)