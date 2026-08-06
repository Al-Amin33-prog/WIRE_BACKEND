package org.example.utils.org.example.utils.repository



import org.example.utils.org.example.utils.dto.ChatDto
import org.example.utils.org.example.utils.entity.ChatsTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ChatRepositoryImpl : ChatRepository {

    override suspend fun getRecentChats(
        userId: String
    ): List<ChatDto> {

        return transaction {

            ChatsTable
                .selectAll()
                .where {
                    (ChatsTable.userA eq userId) or
                            (ChatsTable.userB eq userId)
                }
                .orderBy(
                    ChatsTable.updatedAt,
                    SortOrder.DESC
                )
                .map {

                    ChatDto(
                    uid =
                            if (it[ChatsTable.userA] == userId)
                                it[ChatsTable.userB]
                            else
                                it[ChatsTable.userA],

                        displayName = null,

                        lastMessage =
                            it[ChatsTable.lastMessage],

                        timestamp = it[ChatsTable.updatedAt],

                    )

                }

        }

    }

    override suspend fun updateChatPreview(
        senderId: String,
        receiverId: String,
        lastMessage: String
    ) {

        transaction {

            val chatId =
            listOf(senderId, receiverId)
                .sorted()
                .joinToString("_")

            val exists =
            ChatsTable
                .selectAll()
                .where {
                    ChatsTable.id eq chatId
                }
                .count() > 0

            if (exists) {

                ChatsTable.update({
                    ChatsTable.id eq chatId
                }) {

                    it[ChatsTable.lastMessage] =
                        lastMessage

                    it[updatedAt] =
                        System.currentTimeMillis()

                }

            } else {

            ChatsTable.insert {

                it[id] = chatId

                it[userA] =
                    minOf(senderId, receiverId)

                it[userB] =
                    maxOf(senderId, receiverId)

                it[ChatsTable.lastMessage] =
                    lastMessage

                it[updatedAt] =
                    System.currentTimeMillis()

            }

        }

        }

    }
}



