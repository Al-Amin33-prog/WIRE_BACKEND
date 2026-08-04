package org.example.app.services



import com.resend.Resend
import com.resend.services.emails.model.CreateEmailOptions
import io.github.cdimascio.dotenv.dotenv
import java.io.File

class ResendEmailService : EmailService {

    private val dotenv = dotenv {
        directory = if (File(".env").exists()) "./" else "../"
        ignoreIfMissing = true
    }

    private val resend = Resend(dotenv["RESEND_API_KEY"]!!)

    override suspend fun sendPasswordResetEmail(
        email: String,
        resetLink: String
    ) {

        val params = CreateEmailOptions.builder()
            .from("Wire <onboarding@resend.dev>")
            .to(email)
            .subject("Reset your Wire password")
            .html(
                """
                <h2>Reset your Wire Password</h2>

                <p>Click the button below to reset your password.</p>

                <p>
                    <a href="$resetLink">
                        Reset Password
                    </a>
                </p>

                <p>If you did not request this, ignore this email.</p>
                """.trimIndent()
            )
            .build()

        val response = resend.emails().send(params)

        println("Resend response: $response")
    }
}