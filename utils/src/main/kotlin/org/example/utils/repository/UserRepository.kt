package org.example.utils.org.example.utils.repository

import org.example.utils.org.example.utils.dto.UserDto

interface UserRepository {
    suspend fun upsertUser(
        uid: String,
        email: String,
        displayName:String?,
        phone: String?
    )
    suspend fun getUser(uid:String):UserDto?
}