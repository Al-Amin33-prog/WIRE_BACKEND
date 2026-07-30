package org.example.app.auth



import com.google.firebase.auth.FirebaseAuth
import org.example.app.security.AuthUser
import org.example.app.security.AuthVerifier

class FirebaseAuthVerifier: AuthVerifier {
    override fun verifyToken(token: String): AuthUser? {
        return try {

            val firebaseToken = FirebaseAuth
                .getInstance()
                .verifyIdToken(
                    token.removePrefix("Bearer").trim()
                )
            AuthUser(
                uid = firebaseToken.uid,
                email = firebaseToken.email,
                displayName = firebaseToken.name,
                phone = firebaseToken.claims["phone"] as? String
            )
        }catch (e: Exception){
            println("Security Warning:" +
                    "Token authentication failed -> ${e.message}"
            )
            null
        }
    }



}
