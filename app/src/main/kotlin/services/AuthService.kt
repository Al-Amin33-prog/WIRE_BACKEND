package org.example.app.services


import org.example.app.security.AuthUser
import org.example.utils.org.example.utils.repository.UserRepository

class AuthService(
    private val repository: UserRepository,
    private val emailService: EmailService,
    private val linkGenerator: PasswordResetLinkGenerator
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
    suspend fun forgotPassword(
        email: String
    ){
        val normalizedEmail = email.trim().lowercase()
        println("STEP 1")
        val user = repository.findUserByEmail(normalizedEmail)
        println("User returned from repository: $user")
        if (user == null){
            println("User not found")
            return
        }
        println("STEP 3")
        val resetLink =
            linkGenerator.generateResetLink(normalizedEmail)
        println("STEP 4")
            emailService.sendPasswordResetEmail(
                normalizedEmail,
                resetLink
            )
        println("STEP 5")

    }
}