package org.example.utils.org.example.utils.repository



import org.example.utils.org.example.utils.dto.MessageDto
import org.example.utils.org.example.utils.entity.MessagesTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant


class MessageRepositoryImpl : MessageRepository {


    override suspend fun sendMessage(
        message: MessageDto
    ) {

        transaction {

            MessagesTable.insert {

                it[id] = message.id
                it[senderId] = message.senderId
                it[receiverId] = message.receiverId
                it[content] = message.content
                it[type] = message.type
                it[createdAt] = Instant.now()
                it[isEdited] = false
                it[isDeleted] = false

            }

        }

    }

    override suspend fun getChatHistory(
        senderId: String,
        receiverId: String
    ): List<MessageDto> {

        return transaction {

            MessagesTable
                .selectAll()
                .where {

                    (
                            (MessagesTable.senderId eq senderId) and
                                    (MessagesTable.receiverId eq receiverId)

                            ) or (

                            (MessagesTable.senderId eq receiverId) and
                                    (MessagesTable.receiverId eq senderId)

                            )

                }
                .orderBy(MessagesTable.createdAt to SortOrder.ASC)
                .map {

                    MessageDto(

                        id = it[MessagesTable.id],
                        senderId = it[MessagesTable.senderId],
                        receiverId = it[MessagesTable.receiverId],
                        content = it[MessagesTable.content],
                        type = it[MessagesTable.type],
                        createdAt = it[MessagesTable.createdAt].toEpochMilli(),

                        edited = it[MessagesTable.isEdited],
                        deleted = it[MessagesTable.isDeleted]

                    )

                }

        }

    }

}
