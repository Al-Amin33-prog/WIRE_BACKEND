package org.example.app

import org.example.utils.org.example.utils.dto.SecuritySettingsDto
import org.example.utils.org.example.utils.dto.UserDto
import org.example.utils.org.example.utils.repository.UserRepository

class FakeUserRepository : UserRepository{
    private val users = mutableMapOf<String, UserDto>()
    init {
        users["test_uid"] = UserDto(
            uid = "test_uid",
            email = "test@example.com",
            displayName = "Test User",
            phone = null,
            pinHash = null,
            biometricEnabled = false
        )
    }

    override suspend fun upsertUser(uid: String, email: String, displayName: String?, phone: String?) {
        users[uid] = UserDto(
            uid = uid,
            email = email,
            displayName = displayName,
            phone = phone,
            pinHash = users[uid]?.pinHash,
            biometricEnabled = users[uid]?.biometricEnabled ?: false
        )

    }

    override suspend fun getUser(uid: String): UserDto? {
        return users[uid]
    }

    override suspend fun savePinHash(uid: String, pinHash: String) {
        val user = users[uid] ?: return
        users[uid] = user.copy(
            pinHash = pinHash
        )
    }

    override suspend fun updateBiometric(uid: String, enabled: Boolean) {
        val user = users[uid] ?: return
        users[uid] = user.copy(
            biometricEnabled = enabled
        )
    }

    override suspend fun getSecuritySettings(uid: String): SecuritySettingsDto {
        val user = users[uid] ?: error("User not found")
        return SecuritySettingsDto(
            hasPin = user.pinHash != null,
            biometricEnabled = user.biometricEnabled
        )
    }
}