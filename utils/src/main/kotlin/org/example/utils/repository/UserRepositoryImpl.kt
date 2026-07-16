package org.example.utils.org.example.utils.repository



import org.example.utils.org.example.utils.dto.UserDto
import org.example.utils.org.example.utils.entity.UsersTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant

class UserRepositoryImpl : UserRepository {

    override suspend fun upsertUser(
        uid: String,
        email: String,
        displayName: String?,
        phone: String?
    ) {

        transaction {

            val exists = UsersTable
                .selectAll()
                .where { UsersTable.uid eq uid }
                .count() > 0

            if (exists) {

                UsersTable.update({ UsersTable.uid eq uid }) {

                    it[UsersTable.email] = email
                    it[UsersTable.displayName] = displayName
                    it[UsersTable.phone] = phone
                    it[updatedAt] = Instant.now()

                }

            } else {

                UsersTable.insert {

                    it[UsersTable.uid] = uid
                    it[UsersTable.email] = email
                    it[UsersTable.displayName] = displayName
                    it[UsersTable.phone] = phone
                    it[createdAt] = Instant.now()
                    it[updatedAt] = Instant.now()

                }

            }

        }

    }




    override suspend fun getUser(uid: String): UserDto? {

        return transaction {

            UsersTable
                .selectAll()
                .where { UsersTable.uid eq uid }
                .singleOrNull()
                ?.let {

                    UserDto(
                        uid = it[UsersTable.uid],
                        email = it[UsersTable.email],
                        displayName = it[UsersTable.displayName],
                        phone = it[UsersTable.phone]
                    )

                }

        }

    }
}