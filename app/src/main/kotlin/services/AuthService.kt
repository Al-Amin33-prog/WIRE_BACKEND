package org.example.app.services


import org.example.app.security.AuthUser
import org.example.utils.org.example.utils.repository.UserRepository

class AuthService(
    private val repository: UserRepository
){
    suspend fun syncUser(
        user: AuthUser
    ){
        repository.upsertUser(
            uid = user.uid,
            email = user.email ?: "",
            displayName = user.displayName,
            phone = user.phone

        )
    }
}