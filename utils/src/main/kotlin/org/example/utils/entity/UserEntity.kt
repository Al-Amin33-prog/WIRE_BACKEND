package org.example.utils.org.example.utils.entity



import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object UsersTable : Table("users") {

    val uid = varchar("uid", 128)

    val email = varchar("email", 255)

    val displayName = varchar("display_name", 255)
        .nullable()

    val phone = varchar("phone", 30)
        .nullable()

    val createdAt = timestamp("created_at")

    val updatedAt = timestamp("updated_at")

    override val primaryKey = PrimaryKey(uid)
}