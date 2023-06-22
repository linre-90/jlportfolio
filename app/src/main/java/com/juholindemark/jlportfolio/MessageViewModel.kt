package com.juholindemark.jlportfolio

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.juholindemark.jlportfolio.service.Message
import com.juholindemark.jlportfolio.service.MessageService
import com.juholindemark.jlportfolio.service.MessageServiceImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Ui data
data class MessageUiState(val uiMessages: MutableMap<String, Message>, val initialized: Boolean, val refreshRequested:Boolean)

/**
 * View model for contact message data inside app.
 */
class MessageViewModel(private val messageService: MessageService): ViewModel() {

    // Private message state
    private val _messages = MutableStateFlow(MessageUiState(mutableStateMapOf<String, Message>(), false, false))
    // Public message state
    val messages: StateFlow<MessageUiState> = _messages.asStateFlow()

    /** Delete entry from database. */
    fun deleteEntry(id: String){
        _messages.update { currState ->
            currState.copy(
                uiMessages = currState.uiMessages,
                initialized = currState.initialized,
                refreshRequested = true
            )
        }
        messageService.deleteEntry(id){ error ->
            if(error != null){
                Log.d("MESSAGEVIEVMODEL", error.message.toString())
                _messages.update { currState ->
                    currState.copy(
                        uiMessages = currState.uiMessages,
                        initialized = currState.initialized,
                        refreshRequested = false
                    )
                }
            }else{
                val  futureState :MessageUiState = _messages.value
                futureState.uiMessages.remove(id)
                _messages.update { currState ->
                    currState.copy(
                        uiMessages = futureState.uiMessages,
                        initialized = currState.initialized,
                        refreshRequested = false
                    )
                }
            }
        }
    }

    /** Mark contact as read. */
    fun setRead(id: String){
        val msg = _messages.value.uiMessages[id]
        _messages.update { currState ->
            currState.copy(
                uiMessages = currState.uiMessages,
                initialized = currState.initialized,
                refreshRequested = true
            )
        }
        messageService.setRead(id, Message(msg!!)){ error ->
            if(error != null){
                Log.d("MESSAGEVIEVMODEL", error.message.toString())
            }else{
                _messages.update { currState ->
                    Log.d("asd", "asdasdasd")
                    val updatableMessage = currState.uiMessages[id]
                    val  futureState :MessageUiState
                    if(updatableMessage != null){
                        updatableMessage.read = !updatableMessage.read
                        futureState = _messages.value
                        futureState.uiMessages[id] = updatableMessage
                        // update to new ui state
                        currState.copy(
                            uiMessages = futureState.uiMessages,
                            initialized = currState.initialized,
                            refreshRequested = false
                        )

                    }else{
                        // Something happened use old ui state
                        currState.copy(
                            uiMessages = currState.uiMessages,
                            initialized = currState.initialized,
                            refreshRequested = false
                        )
                    }

                }
            }
        }
    }

    /** fetch message */
    fun fetchMessages(){
        // Set ui state to refreshing
        _messages.update {
            currState -> currState.copy(
                uiMessages = currState.uiMessages,
                initialized = currState.initialized,
                refreshRequested = true
            )
        }
        // Fetch data and set refreshing state to false
        messageService.fetchMessages{error, data ->
            if(error != null){
                Log.d("MESSAGEVIEWMODEL", error.message.toString())
            }else if(data != null){
                _messages.update { currState ->
                    currState.copy(
                        uiMessages = data,
                        initialized = true,
                        refreshRequested = false
                    )
                }
            }
        }
    }

    /** Clears ui state, initializes it back to empty. */
    fun clearUiState(){
        _messages.update { currState ->
            currState.copy(
                uiMessages = mutableMapOf<String, Message>(),
                initialized = false
            )
        }
    }

    /**
     * Fucktory to initialize view model.
     * */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                // AccountServiceImpl used through AccountService interface
                val myMessageService = MessageServiceImpl()
                MessageViewModel(
                    messageService = myMessageService
                )
            }
        }
    }
}