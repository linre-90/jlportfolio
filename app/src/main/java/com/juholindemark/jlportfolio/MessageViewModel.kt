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

    }

    /** Mark contact as read. */
    fun setRead(boolean: Boolean){

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