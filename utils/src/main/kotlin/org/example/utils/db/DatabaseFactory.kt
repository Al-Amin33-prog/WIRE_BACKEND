package org.example.utils.org.example.utils.db

import io.github.cdimascio.dotenv.dotenv
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    // Reads from your hidden .env file at the root folder
    private val dotenv = dotenv {
        directory = "../" // Backs up one directory since submodules execute out of their own paths
        ignoreIfMissing = true
    }

    fun init() {
        val database = Database.connect(createHikariDataSource())

        // This is a test query block to confirm your connection is alive upon application launch
        transaction(database) {
            // Cleaner execution format
            exec("SELECT 1;")
        }

    }

    private fun createHikariDataSource(): HikariDataSource {
        val config = HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = dotenv["DATABASE_URL"] ?: "jdbc:postgresql://localhost:5432/wire_db"
            username = dotenv["DATABASE_USER"] ?: "postgres"
            password = dotenv["DATABASE_PASSWORD"] ?: "password"

            // Standard Hikari enterprise production connection pooling optimizations
            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }
}
