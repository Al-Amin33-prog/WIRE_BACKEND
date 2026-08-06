package org.example.utils.org.example.utils.entity



import org.jetbrains.exposed.sql.Table

object ChatsTable : Table("chats") {

    val id = varchar("id", 128)

    val userA = varchar("user_a", 128)
    .references(UsersTable.uid)

    val userB = varchar("user_b", 128)
    .references(UsersTable.uid)

    val lastMessage = text("last_message")

    val updatedAt = long("updated_at")

    override val primaryKey = PrimaryKey(id)
}
