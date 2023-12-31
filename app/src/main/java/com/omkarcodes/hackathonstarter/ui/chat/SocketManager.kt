package com.omkarcodes.hackathonstarter.ui.chat

import android.util.Log
import com.google.gson.Gson
import com.omkarcodes.hackathonstarter.ui.chat.model.Message
import io.socket.client.IO
import io.socket.client.Socket

data class Status(
    var userId: String,
    var status: String,
    var token: String,
)

data class RoomData(
    val userId: String,
    val strangeeId: String,
    val purpose: String,
    val token: String,
)

data class MessageWithToken(
    val token: String,
    val message: Message,
)

data class MessagePagination(
    val token: String,
    val userId: String,
    val strangeeId: String,
    var lastCreatedAt: String,
)

data class BlockStatus(
    val blockedBy: String,
    val blockedUser: String,
    val status: String,
)

object SocketManager {
    private var mSocket: Socket? = null
    private var UID: String = ""
    private var token: String = ""
    private val userStatus: Status = Status("", "", "")
    val gson: Gson = Gson()

    init {
        setupSocket()
    }

    private fun setupSocket() {
        try {
            mSocket = IO.socket("")
            Log.d("success", mSocket?.id().toString())

        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("fail", "Failed to connect")
        }

        mSocket?.connect()
        //Register all the listener and callbacks here.
        //mSocket.on(Socket.EVENT_CONNECT, onConnect)
        //mSocket.on("sampleDataFromServer", onDataFromServer)
    }

    fun getSocket(): Socket? = mSocket

    fun setUserId(id: String) {
        if (mSocket?.connected() != true) {
            setupSocket()
        }
        UID = id
        userStatus.userId = id
    }

    fun setToken(token: String) {
        this.token = token
        userStatus.token = token
    }

    fun setOnline(online: Boolean) {
        if (online) {
            userStatus.status = "online"
        } else {
            userStatus.status = "offline"
        }

        mSocket?.emit("status", gson.toJson(userStatus))
    }
}