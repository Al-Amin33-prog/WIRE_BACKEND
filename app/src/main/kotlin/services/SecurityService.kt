package org.example.app.services

import org.example.utils.org.example.utils.repository.UserRepository

class SecurityApiService(
    private val repository: UserRepository
) {
    suspend fun savePin(
        uid: String,
        pinHash: String
    ){
        repository.savePinHash(
            uid = uid,
            pinHash = pinHash
        )
    }
    suspend fun updateBiometric(
        uid: String,
        enabled: Boolean
    ){
        repository.updateBiometric(
            uid,
            enabled
        )
    }
    suspend fun getSecuritySettings(
        uid: String
    ){
        repository.getSecuritySettings(uid)
    }

}