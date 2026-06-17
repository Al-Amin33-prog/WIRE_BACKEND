package org.example.app.config



import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.github.cdimascio.dotenv.dotenv
import java.io.File


object FirebaseAdmin {
    fun init() {
        val dotenv = dotenv {
            // Evaluates execution directory dynamically so it reads your root .env file flawlessly
            directory = if (File(".env").exists()) "./" else "../"
            ignoreIfMissing = true
        }

        // Inside FirebaseAdmin.kt init block
        val credentialsPath = dotenv["FIREBASE_CREDENTIALS_PATH"]
            ?: throw IllegalStateException("Firebase credentials path missing in .env")

// Checks local root workspace first, then falls back to parent folder parsing
        val serviceAccountFile = if (File(credentialsPath).exists()) {
            File(credentialsPath)
        } else {
            File("../$credentialsPath")
        }

        if (!serviceAccountFile.exists()) {
            throw IllegalStateException("CRITICAL: Firebase JSON Key file not found anywhere at path: $credentialsPath")
        }

        val serviceAccount = java.io.FileInputStream(serviceAccountFile)

        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        FirebaseApp.initializeApp(options)
        println("Firebase Admin SDK successfully initialized!")
    }
}
