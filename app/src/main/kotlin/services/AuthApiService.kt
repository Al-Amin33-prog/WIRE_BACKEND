package org.example.app.services

import org.example.app.repository.UserRepository

class AuthApiService(
    private val repository: UserRepository
){
    suspend fun syncUser(
        uid:String,
        email: String
    ){
        repository.createUser(
            uid,
            email
        )
    }
}