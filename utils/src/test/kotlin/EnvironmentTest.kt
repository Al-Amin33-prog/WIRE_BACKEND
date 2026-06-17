package org.example.utils

import io.github.cdimascio.dotenv.dotenv
import java.io.File
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class EnvironmentTest {

    @Test
    fun testDotEnvFileIsReadableAndContainsFirebaseKey() {
        // Look up the execution context directory paths dynamically
        val isRootContext = File(".env").exists()
        val dotenv = dotenv {
            directory = if (isRootContext) "./" else "../"
            ignoreIfMissing = true
        }

        val credentialsPath = dotenv["FIREBASE_CREDENTIALS_PATH"]

        assertNotNull(credentialsPath, "CRITICAL ERROR: FIREBASE_CREDENTIALS_PATH key is missing in your .env file!")

        // DYNAMIC FILE LOOKUP: Checks if file path is valid from root context OR  path context
        val localFile = File(credentialsPath)
        val parentFallbackFile = File("../$credentialsPath") // Checks root folder if executing from inside /utils/

        val fileExists = localFile.exists() || parentFallbackFile.exists()

        assertTrue(fileExists, "SECURITY ERROR: The localized Firebase private account JSON file does not exist at path: $credentialsPath")
        println("SUCCESS: Verified local security environment config paths perfectly!")
    }
}
