package org.example.app.auth



import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseToken

object TokenVerifier {
    /**
     * Authenticates the encrypted JWT string transmitted by the Android Client.
     * Returns the FirebaseToken object containing the real uid if valid, or null if counterfeit/expired.
     */
    fun verifyToken(token: String): FirebaseToken? {
        return try {
            // Strip the standard "Bearer " prefix if your Android client includes it in the header
            val cleanToken = token.removePrefix("Bearer ").trim()

            // Reaches out to secure servers to cryptographically prove ownership
            FirebaseAuth.getInstance().verifyIdToken(cleanToken)
        } catch (e: Exception) {
            println("Security Warning: Token authentication failed -> ${e.message}")
            null
        }
    }
}
