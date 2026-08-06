package org.example.utils.org.example.utils.entity



import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object MessagesTable : Table("messages") {

    val id = varchar("id", 128)

    val senderId = varchar("sender_id", 128)
        .references(UsersTable.uid)

    val receiverId = varchar("receiver_id", 128)
        .references(UsersTable.uid)

    val content = text("content")

    val type = varchar("type", 30)
        .default("TEXT")

    val createdAt = timestamp("created_at")

    val isEdited = bool("is_edited")
        .default(false)

    val isDeleted = bool("is_deleted")
        .default(false)

    override val primaryKey = PrimaryKey(id)
}