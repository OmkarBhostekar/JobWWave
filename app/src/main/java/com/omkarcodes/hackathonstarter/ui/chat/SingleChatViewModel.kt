package com.omkarcodes.hackathonstarter.ui.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.omkarcodes.hackathonstarter.ui.chat.model.Message
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.ConnectException

enum class SingleChatStatus { UPLOADING, UPLOAD_ERROR, UPLOAD_FAILED, UPLOAD_DONE }
enum class MessageLoad { LOADING, LOADING_ERROR, LOADING_FAILED, EMPTY, LOADING_DONE }

class SingleChatViewModel(
    private val token: String,
    private val userId: String,
    private val strangeeId: String,
) :
    ViewModel() {
    private val statusRoomData: RoomData = RoomData(userId, strangeeId, "status", token)
    private val chatRoomData: RoomData = RoomData(userId, strangeeId, "chat", token)
    private val messagePagination: MessagePagination =
        MessagePagination(token, userId, strangeeId, "0")
    private val gson = Gson()

    private val _status = MutableLiveData<SingleChatStatus>()
    val status: LiveData<SingleChatStatus>
        get() = _status

    private val _messageLoadStatus = MutableLiveData<MessageLoad>()
    val messageLoadStatus: LiveData<MessageLoad>
        get() = _messageLoadStatus

    private val _isOnline = MutableLiveData<Boolean>()
    val isOnline: LiveData<Boolean>
        get() = _isOnline

    private val _isBlocked = MutableLiveData<Boolean>()
    val isBlocked: LiveData<Boolean>
        get() = _isBlocked

    private val _messageList = MutableLiveData<List<Message>>()
    val messageList: LiveData<List<Message>>
        get() = _messageList

    private val _initialOldMessageLoad = MutableLiveData<Boolean>()
    val initialOldMessageLoad: LiveData<Boolean>
        get() = _initialOldMessageLoad

    private val _fetchedMessageIsNew = MutableLiveData<Boolean>()
    val fetchedMessageIsNew: LiveData<Boolean>
        get() = _fetchedMessageIsNew

    private val _scrollToNewMessage = MutableLiveData<Boolean>(true)
    val scrollToNewMessage: LiveData<Boolean>
        get() = _scrollToNewMessage

    private val _scrollPaginationEnabled = MutableLiveData<Boolean>()
    val scrollPaginationEnabled: LiveData<Boolean>
        get() = _scrollPaginationEnabled

    private val _imageUploadUrl = MutableLiveData<String>()
    val imageUploadUrl: LiveData<String>
        get() = _imageUploadUrl

    private val _lastCreatedAt = MutableLiveData<String>()
    val lastCreatedAt: LiveData<String>
        get() = _lastCreatedAt

    private val viewModelJob = Job()

    init {
        checkBlocked()
    }

    fun checkBlocked() {

        viewModelScope.launch {
//            try {
//                Log.i("SProfileViewModel", "Checking blocked...")
//                _messageLoadStatus.value = MessageLoad.LOADING
//
//                val blocked =
//                    StrangeeApi.retrofitService.getAmIBlocked("Bearer ".plus(token), strangeeId)
//                Log.i("SingleChatViewModel", "Blocked: $blocked")
//
//                if(!blocked) {
                    setupSockets()
//                } else {
//                    _messageLoadStatus.value = null
//                }
//
//                _isBlocked.value = blocked
//            } catch (t: ConnectException) {
//                Log.i("SingleChatViewModel", "BLOCKING_Error ::: $t")
//                _messageLoadStatus.value = MessageLoad.LOADING_ERROR
//            } catch (t: Throwable) {
//                Log.i("SingleChatViewModel", "BLOCKING_Failed ::: $t")
//                _messageLoadStatus.value = MessageLoad.LOADING_FAILED
//            }
        }
    }

    private fun setupSockets() {
        SocketManager.getSocket()?.emit("subscribe", gson.toJson(statusRoomData))
        SocketManager.getSocket()?.emit("subscribe", gson.toJson(chatRoomData))
        postMessageRead()

        SocketManager.getSocket()?.on("statusChange") {
            _isOnline.postValue(it[0].toString() == "online")
        }
        getOlderMessages(true)
        SocketManager.getSocket()?.on("new message") {
            _fetchedMessageIsNew.postValue(true)

            if(_isBlocked.value != true) {
                _messageLoadStatus.postValue(MessageLoad.LOADING_DONE)
            }

            _messageList.postValue((_messageList.value
                ?: listOf<Message>()) + (gson.fromJson(it[0].toString(),
                Array<Message>::class.java)) as Array<Message>)

            postMessageRead()
        }

        SocketManager.getSocket()?.on("blockStatusChange") {
            val blockStatus: BlockStatus = gson.fromJson(it[0].toString(), BlockStatus::class.java)

            if(blockStatus.blockedUser == userId) {
                _isBlocked.postValue(blockStatus.status == "blocked")
            }
        }
    }

    private fun postMessageRead() {
        SocketManager.getSocket()?.emit("message read", gson.toJson(chatRoomData))
    }

    fun onInitialOldMessageLoadComplete() {
        _initialOldMessageLoad.value = false
    }

    fun setScrollToNewMessage(scroll: Boolean) {
        _scrollToNewMessage.value = scroll
    }

    fun sendMessage(message: Message) {
        val messageWithToken = MessageWithToken(token, message)
        SocketManager.getSocket()?.emit("message", gson.toJson(messageWithToken))
    }

    fun getOlderMessages(changeStatus: Boolean = false) {
        _scrollPaginationEnabled.value = false

        viewModelScope.launch {
            try {
                // TODO: fetch old messages
                Log.i("ChatViewModel", "Begin...")
                if(changeStatus) _messageLoadStatus.value = MessageLoad.LOADING

//                val returnedValue = StrangeeApi.retrofitService.getMessage("Bearer ".plus(token), userId, strangeeId, _lastCreatedAt.value ?: "0")
//                Log.i("ChatViewModel", returnedValue.toString())
                val returnedValue = mutableListOf<Message>()
                returnedValue.add(Message("1","2","Hello","text",null,System.currentTimeMillis(),
                "", null))
                returnedValue.add(Message("1","2","Hi","text",null,System.currentTimeMillis(),
                "", null))

                if (returnedValue.isNotEmpty()) {
                    _fetchedMessageIsNew.value = false
                    if (_initialOldMessageLoad.value == null) {
                        _initialOldMessageLoad.value = true
                    }
                    _messageList.value = returnedValue + (_messageList.value ?: listOf<Message>())

                    if(changeStatus) _messageLoadStatus.value = MessageLoad.LOADING_DONE
//                    _lastCreatedAt.value = returnedValue[0].createdAt
                    _lastCreatedAt.value = "just now"
                    _scrollPaginationEnabled.value = true
                } else {
                    if(changeStatus) _messageLoadStatus.value = MessageLoad.EMPTY
                }
            } catch (t: ConnectException) {
                Log.i("ChatViewModel", "Connection_Error ::: $t")
                if(changeStatus) _messageLoadStatus.value = MessageLoad.LOADING_ERROR
                _scrollPaginationEnabled.value = true
            } catch (t: Throwable) {
                Log.i("ChatViewModel", "Failed ::: $t")
                if(changeStatus) _messageLoadStatus.value = MessageLoad.LOADING_FAILED
                _scrollPaginationEnabled.value = true
            }
        }
    }

    fun onImageUrlUsed() {
//        _imageUploadUrl.value = null
    }

    fun uploadImage(imagePath: String) {
        viewModelScope.launch {
            try {
                // TODO: upload image to firebase
//                _status.value = SingleChatStatus.UPLOADING
//
//                val file: File = File(imagePath)
//                val requestBody: RequestBody =
//                    RequestBody.create(MediaType.parse(file.getMimeType("image/png")), file)
//                val part: MultipartBody.Part =
//                    MultipartBody.Part.createFormData("imageByUser", file.name, requestBody)
//
//                val returnedData = StrangeeApi.retrofitService.postImage(
//                    "Bearer ".plus(token),
//                    part
//                )
//
//                Log.i("SingleChatViewModel", returnedData)
//
//                if (returnedData.isNotEmpty()) {
//                    _imageUploadUrl.value = returnedData
//                    _status.value = SingleChatStatus.UPLOAD_DONE
//                } else {
//                    _status.value = SingleChatStatus.UPLOAD_FAILED
//                }
            } catch (t: ConnectException) {
                Log.i("SingleChatViewModel", "CONNECT EXCEPTION ::: $t")
                _status.value = SingleChatStatus.UPLOAD_ERROR
            } catch (t: Throwable) {
                Log.i("SingleChatViewModel", "PIC_UPDATE_Error ::: $t")
                _status.value = SingleChatStatus.UPLOAD_FAILED
            }
        }
    }

    fun clearUploadStatus() {
//        _status.value = null
    }

    override fun onCleared() {
        super.onCleared()
        SocketManager.getSocket()?.emit("unsubscribe", gson.toJson(statusRoomData))
        SocketManager.getSocket()?.emit("unsubscribe", gson.toJson(chatRoomData))
        viewModelJob.cancel()
    }
}