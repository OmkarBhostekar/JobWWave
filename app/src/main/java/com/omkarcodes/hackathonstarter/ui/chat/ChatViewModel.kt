package com.omkarcodes.hackathonstarter.ui.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omkarcodes.hackathonstarter.ui.chat.model.ChatData
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.ConnectException

enum class ChatStatus { LOADING, ERROR, FAILED, EMPTY, DONE }

class ChatViewModel(token: String, userId: String): ViewModel() {
    private val _status = MutableLiveData<ChatStatus>()
    val status: LiveData<ChatStatus>
        get() = _status

    private val _chatList = MutableLiveData<List<ChatData>>()
    val chatList: LiveData<List<ChatData>>
        get() = _chatList

    private val _navigateToSelectedChat = MutableLiveData<ChatData>()
    val navigateToSelectedChat: LiveData<ChatData>
        get() = _navigateToSelectedChat

    private val viewModelJob = Job()

    init {
        // getChatList(token, userId)
    }

    fun displaySavedProfile(chatData: ChatData) {
        _navigateToSelectedChat.value = chatData
    }

    fun onDisplayChatComplete() {
//        _navigateToSelectedChat.value = null
    }

    fun getChatList(token: String, userId: String) {
        viewModelScope.launch {
            try {
                Log.i("ChatViewModel", "Begin...")
                _status.value = ChatStatus.LOADING

                val returnedValue = mutableListOf<ChatData>()
                returnedValue.add(
                    ChatData("2","Omkar","Bhostekar","https://randomuser.me/api/portraits/men/86.jpg",
                    System.currentTimeMillis(),false,"Hey there", true
                )
                )
                returnedValue.add(ChatData("2","Prateek","Vishvakarma","https://randomuser.me/api/portraits/men/86.jpg",
                    System.currentTimeMillis(),false,"Hello", true
                ))
                returnedValue.add(ChatData("2","Sachin","Jangir","https://randomuser.me/api/portraits/men/86.jpg",
                    System.currentTimeMillis(),false,"Okay", true
                ))
                returnedValue.add(ChatData("2","Aditya","Surve","https://randomuser.me/api/portraits/men/86.jpg",
                    System.currentTimeMillis(),false,"No", true
                ))
//                val returnedValue = StrangeeApi.retrofitService.getChat("Bearer ".plus(token), userId)
//                Log.i("ChatViewModel", returnedValue.toString())

                if (returnedValue.isNotEmpty()) {
                    _chatList.value = returnedValue
                    _status.value = ChatStatus.DONE
                } else {
                    _status.value = ChatStatus.EMPTY
                }
            } catch (t: ConnectException) {
                Log.i("ChatViewModel", "Connection_Error ::: $t")
                _status.value = ChatStatus.ERROR
            } catch (t: Throwable) {
                Log.i("ChatViewModel", "Failed ::: $t")
                _status.value = ChatStatus.FAILED
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}