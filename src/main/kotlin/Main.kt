data class Chat(
    val cId: Int,
    val ownerId: Int,
    val title: String,
    // val readChat: Boolean
)

data class Message(
    val mId: Int,
    val ownerId: Int,
    val text: String,
    val readMessage: Boolean
)

data class Chats<A, B>(var first: A, var second: B)

object ChatService {
    private var userChats = mutableListOf<Chats<Chat, Array<Message>>>()
    private var counterMid: Int = 0
    private var counterCid: Int = 0

    fun createEmptyChat(chat: Chat): Int {
        userChats.find { chats -> chat.cId == chats.first.cId } ?: userChats.add(
            Chats(
                chat.copy(cId = counterCid),
                emptyArray()
            )
        )
        counterCid += 1
        return counterCid - 1
    }

    fun createChat(message: Message, ownerIdChat: Int, cId: Int, titleChat: String): Int {
        userChats.find { chats -> cId == chats.first.cId && ownerIdChat == chats.first.ownerId } ?: userChats.add(
            Chats(
                Chat(counterCid, ownerIdChat, titleChat),
                arrayOf(message.copy(mId = counterMid))
            )
        )
        counterMid += 1
        counterCid += 1
        return counterCid - 1
    }

    fun addMessage(message: Message, ownerIdChat: Int, cId: Int): Int {
        val chat = userChats.find { chats -> cId == chats.first.cId && ownerIdChat == chats.first.ownerId }
            ?: throw ChatNotFoundException("Chat not found")
        chat.second += message.copy(mId = counterMid)
        userChats.set(userChats.indexOfFirst { cId == it.first.cId && ownerIdChat == it.first.ownerId }, chat)
        counterMid += 1
        return counterMid - 1
    }

    fun getUnreadReceivedChatsCount(ownerCid: Int): Int {
        val unreadChats = userChats.filter { chats -> chats.second.any { message -> message.readMessage } }
        return unreadChats.count { chats -> chats.first.ownerId == ownerCid }
    }

    fun getChats(ownerCid: Int) {
              val chat1 = userChats.filter { chats -> chats.first.cId == ownerCid }
    }


}


fun main(args: Array<String>) {
    val chat1 = Chat(1, 122, "Chating")
    val chat2 = Chat(2, 123, "Chating2")
    val chat3 = Chat(3, 122, "Chating3")
    val message1 = Message(1, 122, "Hi", true)
    val message2 = Message(2, 123, "Hi-Hi", false)
    val message3 = Message(3, 123, "Hi-Hi", false)
    ChatService.createEmptyChat(chat1)
    ChatService.createEmptyChat(chat2)
    ChatService.createEmptyChat(chat3)

    val v = Chats<Chat, Array<Message>>(first = chat1, second = arrayOf(message1, message2))
    val b = Chats<Chat, Array<Message>>(first = chat2, second = arrayOf(message3))
    var lista = mutableListOf<Chats<Chat, Array<Message>>>()
    lista += v
    lista += b


}