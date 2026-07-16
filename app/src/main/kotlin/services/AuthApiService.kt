package org.example.app.services


import org.example.utils.org.example.utils.repository.UserRepository

class AuthApiService(
    private val repository: UserRepository
){
    suspend fun syncUser(
        uid:String,
        email: String
    ){
        repository.getUser(
            uid,

        )
    }
}